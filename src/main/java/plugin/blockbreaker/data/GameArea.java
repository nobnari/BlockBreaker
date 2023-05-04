package plugin.blockbreaker.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@Getter
@Setter
public class GameArea {

  Location ls;
  Location lg;
  int X = 7;
  int Y = 11;
  int Z = 5;
  int ZC = 2;

  /**
   * 以下コンストラクタ
   *
   * @param player 　プレイヤー
   */
  public GameArea(Player player) {
    World w = player.getWorld();
    Location l = player.getLocation();
    Vector vec = l.getDirection().multiply(5);
    Location l0 = new Location(w, l.getX() + vec.getX(), l.getY(), l.getZ() + vec.getZ());
    this.ls = (new Location(w, (int) l0.getX() - 2, (int) l0.getY(), (int) l0.getZ() - 1));
    this.lg = (new Location(w, (int) ls.getX() + X - 1, (int) ls.getY() + Y - 1,
        (int) ls.getZ() + Z - 1));
  }

  /**
   * このモノリススペースエリアにブロックが含まれるか検出するメソッド
   *
   * @param block 検出するブロックの座標
   */
  public boolean checkMonolithSpace(Block block) {
    Location l = block.getLocation();
    return ls.getX() + 1 <= l.getX() && l.getX() <= lg.getX() - 1
        && ls.getY() <= l.getY() && l.getY() <= lg.getY() - 1
        && l.getZ() == ls.getZ() + ZC;

  }

}


