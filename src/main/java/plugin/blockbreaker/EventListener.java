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
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;
import plugin.blockbreaker.data.OnPlayData;

public class EventListener implements Listener {

  private final Meta meta;

  public EventListener(Meta meta) {
    this.meta = meta;
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
        if (!touchedBlocks.contains(block)
            && onPlayData.getLastTouchMaterial() == block.getType()) {
          onPlayData.setChainCount(onPlayData.getChainCount() + 1);
          touchedBlocks.add(block);
          //もしチェインカウントが４以上なら…
          if (onPlayData.getChainCount() >= 4) {
            //タッチしたブロックを全て消去
            touchedBlocks.forEach(b -> b.setType(Material.AIR));
          }
          e.setCancelled(true);
          //もしリスト登録済のブロックをクリックしたら…
        } else if (touchedBlocks.contains(block)) {
          e.setCancelled(true);
        } else if (!isOnAir(block.getLocation())) {
          onPlayData.setChainCount(1);
          onPlayData.setLastTouchMaterial(block.getType());

          touchedBlocks.clear();
          touchedBlocks.add(block);
          e.setCancelled(true);

          allBlockGrounder(ga, ls);
        } else if (isOnAir(block.getLocation())) {
          onPlayData.setChainCount(1);
          onPlayData.setLastTouchMaterial(block.getType());

          touchedBlockGrounder(ls, block, touchedBlocks);
          e.setCancelled(true);

          allBlockGrounder(ga, ls);
        }
      }
      e.setCancelled(true);
    }
  }

  /**
   * モノリス内の全ての宙空ブロックを着地させる
   *
   * @param ga ゲームエリア
   * @param ls モノリスエリアの左上のロケーション
   */
  private void allBlockGrounder(GameArea ga, Location ls) {
    int x = ga.getX();
    int y = ga.getY();
    int zc = ga.getZC();
    for (int i = 1; i < x - 1; i++) {
      for (int j = 0; j < y - 1; j++) {
        Location l = new Location(ls.getWorld(),
            ls.getX() + i,
            ls.getY() + j,
            ls.getZ() + zc);
        Block b = l.getBlock();

        blockGrounder(ls, b);
      }
    }
  }

  /**
   * ブロックを空中から着地させるメソッド
   *
   * @param ls ゲームエリアのロケーションの最小値
   * @param b  着地させるブロック
   */
  private void blockGrounder(Location ls, Block b) {
    Location l = b.getLocation();
    Location l2 = l.clone();
    Location l3 = l.clone();
    int i = 0;
    while (!b.getType().isAir() && l2.getY() > ls.getY() && isOnAir(l2)) {
      l2.clone().add(0, -1, 0).getBlock().setType(b.getType());
      l2.add(0, -1, 0);
      i++;
    }
    while (i > 0) {
      l3.getBlock().setType(Material.AIR);
      l3.add(0, -1, 0);
      i--;
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
    touchedBlocks.clear();
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
   * プレイヤーがマインクラフト自体に参加した時にステータスに名前とFALSE、 及びオンプレイデータを作る
   *
   * @param e 　プレイヤーjoin
   */
  @EventHandler
  private void onPlayerJoinEvent(PlayerJoinEvent e) {
    meta.getStatus().put(e.getPlayer().getName(), Boolean.FALSE);
    meta.getOnPlayData().put(e.getPlayer().getName(), new OnPlayData());
  }
}

