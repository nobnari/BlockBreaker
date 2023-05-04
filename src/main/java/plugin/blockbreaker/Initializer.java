package plugin.blockbreaker;

import java.util.List;
import java.util.SplittableRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import plugin.blockbreaker.data.Course;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;

public class Initializer {

  BlockBreaker main;
  Meta meta;
  Finisher fini;


  public Initializer(BlockBreaker main, Meta meta, Finisher fini) {
    this.main = main;
    this.meta = meta;
    this.fini = fini;
  }


  /**
   * 一定間隔でモノリス最上段(X値ランダム)に コースに依存したランダムな種類のブロックを生成し, さらに10ティック後にそのブロックを着地させる。
   * プレイヤーステータスがfalseになったら終了。
   *
   * @param player プレイヤー
   * @param course 難易度
   */
  public void timerStart(Player player, Course course) {
    GameArea ga = meta.getReserveData().get(player.getName()).getGa();
    Bukkit.getScheduler().runTaskTimer(main,
        run -> {
          if (!meta.getStatus().get(player.getName())) {
            run.cancel();
            fini.Finisher(player);
            return;
          }
          randomBlockGenerator(player, course);
          Bukkit.getScheduler().scheduleSyncDelayedTask(main,
              () -> {
                allBlockGrounder(ga, ga.getLs());
              }, 10);

        }, 0, 20);
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
   * ブロックが空中にあるかどうかを返すメソッド
   *
   * @param l ブロックのロケーション
   */
  public boolean isOnAir(Location l) {
    return l.clone().add(0, -1, 0).getBlock().getType().isAir();
  }

  /**
   * モノリス最上段(X値ランダム)に コースに依存したランダムな種類のブロックを生成
   *
   * @param player プレイヤー
   * @param course 難易度
   */
  private void randomBlockGenerator(Player player, Course course) {
    Location l = randomLocationGen(player);
    Material m = randomMaterialGen(course);
    l.getBlock().setType(m);
  }

  /**
   * モノリス最上段にX値がランダムのロケーションを返す。
   *
   * @param player 　コマンド実行プレイヤー
   * @return Location
   */
  private Location randomLocationGen(Player player) {
    GameArea ga = meta.getReserveData().get(player.getName()).getGa();
    Location ls = ga.getLs();
    int x = ga.getX();
    int y = ga.getY();
    int zc = ga.getZC();
    int randomX = new SplittableRandom().nextInt(x - 2) + 1;
    return new Location(ls.getWorld(), ls.getX() + randomX, ls.getY() + y - 2, ls.getZ() + zc);
  }

  /**
   * config.yml 内の リストからランダムでマテリアルを一種選んで返す。
   *
   * @param course 　難易度
   * @return material
   */
  private Material randomMaterialGen(Course course) {
    List<String> blockList = course.getBlockList();
    int i = new SplittableRandom().nextInt(blockList.size());
    return Material.getMaterial(blockList.get(i));
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
      for (int j = 0; j < y - 2; j++) {
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
    int y = ga.getY() - 4;
    int zc = ga.getZC();
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

