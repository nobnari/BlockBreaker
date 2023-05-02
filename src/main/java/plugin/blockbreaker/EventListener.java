package plugin.blockbreaker;

import java.util.List;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
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
      //以下ブロックがゲームエリア内にある時の処理
      if (meta.getReserveData().get(player.getName()).getGa().containsBlock(block)) {
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
          //もしリスト登録済のブロックをクリックしたら…変化無し
        } else if (touchedBlocks.contains(block)) {
          e.setCancelled(true);
        } else {
          onPlayData.setChainCount(1);
          onPlayData.setLastTouchMaterial(block.getType());
          touchedBlocks.clear();
          touchedBlocks.add(block);
          e.setCancelled(true);
        }
      }
      e.setCancelled(true);
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
}
