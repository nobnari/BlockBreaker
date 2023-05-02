package plugin.blockbreaker;

import java.util.List;
import java.util.SplittableRandom;
import org.bukkit.Location;
import org.bukkit.Material;
import plugin.blockbreaker.data.Course;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;

public class Initializer {

  Meta meta;

  public Initializer(Meta meta) {
    this.meta = meta;
  }

  /**
   * ゲームエリア全体に空気を設置し、天井部分にガラスブロックを設置するメソッド
   *
   * @param ga ゲームエリア
   */
  public void glassAirSetter(GameArea ga) {
    Location ls = ga.getLs();
    int x = ga.getX();
    int y = ga.getY();
    int z = ga.getZ();
    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y - 1; j++) {
        for (int k = 0; k < z; k++) {
          Location l = new Location(ls.getWorld(), ls.getX() + i, ls.getY() + j, ls.getZ() + k);
          l.getBlock().setType(Material.AIR);
        }
      }
    }
    for (int i = 0; i < x; i++) {
      for (int k = 0; k < z; k++) {
        Location l = new Location(ls.getWorld(), ls.getX() + i, ls.getY() + y - 1, ls.getZ() + k);
        l.getBlock().setType(Material.GLASS);
      }
    }
  }

  /**
   * ゲームエリアの中心にモノリスブロックを設置するメソッド
   *
   * @param course 難易度
   * @param ga     ゲームエリア
   */
  public void monolithSetter(Course course, GameArea ga) {
    Location ls = ga.getLs();
    int x = ga.getX();
    int y = ga.getY();
    int z = ga.getZ();
    int zc = z / 2 + 1;
    for (int i = 1; i < x - 1; i++) {
      for (int j = 0; j < y - 1; j++) {
        Location l = new Location(ls.getWorld(), ls.getX() + i, ls.getY() + j,
            ls.getZ() + zc);
        randomBlockSetter(course, l);
      }
    }
  }

  /**
   * 難易度別にランダムにブロック選択し１箇所に設置するメソッド
   *
   * @param course 難易度
   * @param l      　設置する場所
   */
  private void randomBlockSetter(Course course, Location l) {
    List<String> blockList = course.getBlockList();
    int random = new SplittableRandom().nextInt(blockList.size());
    Material material = Material.valueOf(blockList.get(random));
    l.getBlock().setType(material);
  }


}

