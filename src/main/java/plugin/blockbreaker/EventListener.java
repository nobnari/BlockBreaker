package plugin.blockbreaker;

import java.util.List;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;
import plugin.blockbreaker.data.OnPlayData;

public class EventListener implements Listener {

  private final Meta meta;
  private final Finisher fini;
  int i = 1;

  public EventListener(Meta meta, Finisher finisher) {
    this.meta = meta;
    this.fini = finisher;
  }

  /**
   * ゲームエリアにあるブロックは右クリックしても破壊をキャンセルする
   *
   * @param e 　プレイヤーがブロックを右クリックした時のイベント
   */
  @EventHandler
  private void onPlayerInteractEvent(PlayerInteractEvent e) {
    Player player = e.getPlayer();
    Block block = e.getClickedBlock();
    OnPlayData onPlayData = meta.getOnPlayData().get(player.getName());
    List<Block> touchedBlocks = onPlayData.getTouchedBlocks();
    //以下プレイヤーのステータスがTRUEの時の処理
    if (meta.getStatus().get(player.getName()) && Objects.nonNull(block)) {
      GameArea ga = meta.getReserveData().get(player.getName()).getGa();
      Location ls = ga.getLs();
      //以下ブロックがモノリススペース内にある時の処理
      if (meta.getReserveData().get(player.getName()).getGa().checkMonolithSpace(block)) {
        //もしリスト未登録の同じ種類のブロックをクリックしたら…
        if (onPlayData.getLastTouchMaterial() == block.getType()) {
          if (!touchedBlocks.contains(block)) {
            onPlayData.setChainCount(onPlayData.getChainCount() + 1);
            listRegister(block, touchedBlocks, ls);
            //もしチェインカウントが４以上なら…
            if (onPlayData.getChainCount() >= 4) {
              //ブロックとリストを全消去
              touchedBlocks.forEach(b -> b.setType(Material.AIR));
              touchedBlocks.clear();
              //スコア生成とメッセージ送信
              int score = onPlayData.getScore() + scoreCalculator(onPlayData.getChainCount());
              onPlayData.setScore(score);
              messenger1000(player, score);
            }
            e.setCancelled(true);
            //もしリスト登録済のブロックをクリックしたら…
          } else {
            e.setCancelled(true);
          } //もし違うブロックをクリックしたら…
        } else {
          if (onPlayData.getChainCount() >= 4) {
            player.sendMessage(onPlayData.getChainCount() + " Chain!");
          }
          onPlayData.setChainCount(1);
          onPlayData.setLastTouchMaterial(block.getType());
          touchedBlocks.clear();
          listRegister(block, touchedBlocks, ls);
          e.setCancelled(true);
        }
      }
      e.setCancelled(true);
    }
  }

  /**
   * リストに登録する。 宙空のブロックは地上に落として上で登録する
   *
   * @param block         　登録するブロック
   * @param touchedBlocks 　リスト
   * @param ls            　ゲームエリアのロケーションの最小値
   */
  private void listRegister(Block block, List<Block> touchedBlocks, Location ls) {
    if (isOnAir(block.getLocation())) {
      touchedBlockGrounder(ls, block, touchedBlocks);
    } else {
      touchedBlocks.add(block);
    }
  }

  /**
   * タッチしたブロックを空中から着地させ　テーブルに保管するメソッド
   *
   * @param ls ゲームエリアのロケーションの最小値
   * @param b  着地させるブロック
   */
  private void touchedBlockGrounder(Location ls, Block b, List<Block> touchedBlocks) {
    Location l = b.getLocation();
    Location l2 = l.clone();
    Location l3 = l.clone();
    int i = 0;
    while (!b.getType().isAir() && l2.getY() > ls.getY() && isOnAir(l2)) {
      l2.clone().add(0, -1, 0).getBlock().setType(b.getType());
      l2.add(0, -1, 0);
      i++;
    }
    touchedBlocks.add(l2.getBlock());
    while (i > 0) {
      l3.getBlock().setType(Material.AIR);
      l3.add(0, -1, 0);
      i--;
    }
  }

  /**
   * ブロックが空中にあるかどうかを返すメソッド
   *
   * @param l ブロックのロケーション
   */
  public boolean isOnAir(Location l) {
    return l.clone().add(0, -1, 0).getBlock().getType().isAir();
  }

  /**
   * スコア計算機
   *
   * @param i 　チェインカウント
   * @return n 獲得スコア
   */
  public int scoreCalculator(int i) {
    int n = 0;
    if (i == 4) {
      n = 100;
    } else if (i > 4) {
      n = i * 10;
    }
    return n;
  }

  /**
   * 1000点ごとにメッセージを送るメソッド
   *
   * @param player プレイヤー
   * @param score  　合計スコア
   */
  private void messenger1000(Player player, int score) {
    if (score >= 1000 * i) {
      player.sendMessage(1000 * i + "点突破!!!");
      i++;
    } else if (score < 1000) {
      i = 1;
    }
  }

  /**
   * プレイヤーがマインクラフト自体に参加した時にステータスに名前とFALSE、 及びオンプレイデータを作る
   *
   * @param e 　プレイヤーjoin
   */
  @EventHandler
  private void onPlayerJoinEvent(PlayerJoinEvent e) {
    meta.getStatus().put(e.getPlayer().getName(), Boolean.FALSE);
    meta.getOnPlayData().put(e.getPlayer().getName(), new OnPlayData());
  }

  /**
   * プレイヤーがマインクラフト自体から退出した時にステータスにFALSE、ブロック戻し、 ゲームモード、時間を元に戻す
   *
   * @param e プレイヤーquit
   */
  @EventHandler
  private void onPlayerQuitEvent(PlayerQuitEvent e) {
    fini.closer(e.getPlayer());
  }
}

