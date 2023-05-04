package plugin.blockbreaker;

import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;

public class Finisher {

  Meta meta;

  public Finisher(Meta meta) {
    this.meta = meta;
  }

  /**
   * ゲーム終了時の処理
   *
   * @param player プレイヤー
   */
  public void Finisher(Player player) {
    blockReset(player);
    gameModeReset(player);
//meta.getStatus().put(player.getName(), false);
  }

  /**
   * ゲーム終了後ブロックを元に戻す
   *
   * @param player 　プレイヤー
   */
  public void blockReset(Player player) {
    GameArea ga = meta.getReserveData().get(player.getName()).getGa();
    List<Material> lastBlocks = meta.getReserveData().get(player.getName()).getMemorizeBlocks();
    Location ls = ga.getLs();
    int x = ga.getX();
    int y = ga.getY();
    int z = ga.getZ();
    int m = 0;
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        for (int k = 0; k < z; k++) {
          Location l = new Location(ls.getWorld(), ls.getX() + i, ls.getY() + j, ls.getZ() + k);
          l.getBlock().setType(lastBlocks.get(m));
          m++;
        }
      }
    }
  }

  /**
   * ゲームモードを元に戻す
   *
   * @param player 　プレイヤー
   */
  public void gameModeReset(Player player) {
    GameMode gameMode = meta.getReserveData().get(player.getName()).getGameMode();
    player.setGameMode(gameMode);
  }
}
