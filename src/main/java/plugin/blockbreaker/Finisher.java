package plugin.blockbreaker;

import java.util.List;
import java.util.SplittableRandom;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import plugin.blockbreaker.DAO.Connector;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;

public class Finisher {

  BlockBreaker main;
  Meta meta;

  Connector con = new Connector();

  public Finisher(BlockBreaker main, Meta meta) {
    this.main = main;
    this.meta = meta;
  }

  public void clearCloser(Player player) {
    int score = meta.getOnPlayData().get(player.getName()).getScore();
    projectFireworks(player, score);
    closer(player);
    player.sendMessage("ゲーム終了!");
    player.sendTitle("§6" + score + "点",
        "§6" + meta.getReserveData().get(player.getName()).getSelectedCourse().getValue(),
        10, 80, 40);
    con.insertMMScore(player, score,
        meta.getReserveData().get(player.getName()).getSelectedCourse().getValue());
  }

  /**
   * ゲーム終了時の処理
   *
   * @param player プレイヤー
   */
  public void closer(Player player) {
    blockReset(player);
    gameModeReset(player);
    player.resetPlayerTime();
    player.resetPlayerWeather();
  }

  /**
   * クリア時にスコアに応じて花火を発射するメソッド
   *
   * @param player プレイヤー
   * @param score  　合計スコア
   */
  private void projectFireworks(Player player, int score) {
    if (score >= 1000) {
      int count = score / 1000;
      for (int i = 0; i < count; i++) {
        List<Color> colorList = List.of(Color.YELLOW, Color.BLUE, Color.PURPLE, Color.RED,
            Color.WHITE);
        int r = new SplittableRandom().nextInt(colorList.size());
        Color color = colorList.get(r);
        Bukkit.getScheduler().scheduleSyncDelayedTask(main,
            () -> {
              Firework fw = player.getWorld().spawn(player.getLocation(), Firework.class);
              FireworkMeta fwm = fw.getFireworkMeta();
              fwm.addEffect(FireworkEffect.builder().flicker(true).with(Type.BALL_LARGE)
                  .withColor(color).build());
              fwm.setPower(1);
              fw.setFireworkMeta(fwm);
            }, (count - 1) * 2);
      }
    }
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
