package plugin.blockbreaker.data;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Setter
@Getter
public class ReserveData {

  Player player;
  Course selectedCourse;
  GameMode gameMode;
  GameArea ga;
  List<Material> lastBlocks;

  public ReserveData(Player player, Course course) {
    this.player = player;
    this.selectedCourse = course;
    this.gameMode = player.getGameMode();
    this.ga = new GameArea(player);
    this.lastBlocks = LastBlocks(ga);
  }

  /**
   * lsからlgまでの範囲のブロックをリストに取得するメソッド
   */
  public List<Material> LastBlocks(GameArea ga) {
    List<Material> lastBlocks = new ArrayList<>();
    Location ls = ga.getLs();
    int x = ga.getX();
    int y = ga.getY();
    int z = ga.getZ();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        for (int k = 0; k < z; k++) {
          Location l = new Location(ls.getWorld(), ls.getX() + i, ls.getY() + j, ls.getZ() + k);
          Material block = l.getBlock().getType();
          lastBlocks.add(block);
        }
      }
    }
    return lastBlocks;
  }

}

