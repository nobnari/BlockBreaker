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
  int X = 6;
  int Y = 7;
  int Z = 5;

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
    this.ls = (new Location(w, l0.getX() - 2, l0.getY(), l0.getZ() - 1));
    this.lg = (new Location(w, ls.getX() + X - 1, ls.getY() + Y - 1, ls.getZ() + Z - 1));
  }


  /**
   * このエリアにブロックが含まれるか検出するメソッド
   *
   * @param block 検出するブロックの座標
   */
  public boolean containsBlock(Block block) {
    Location l = block.getLocation();
    return ls.getX() <= l.getX() && l.getX() <= lg.getX()
        && ls.getY() <= l.getY() && l.getY() <= lg.getY()
        && ls.getZ() <= l.getZ() && l.getZ() <= lg.getZ();
  }

}


