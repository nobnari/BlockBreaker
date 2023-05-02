package plugin.blockbreaker.data;

import org.bukkit.block.Block;

public class BlockStatus {

  boolean onBlock;

  public BlockStatus(Block block) {
    this.onBlock = isOnBlock(block);
  }

  /**
   * ブロックが空中にあるかどうかを返すメソッド
   */
  public boolean isOnBlock(Block block) {
    return !block.getLocation().add(0, -1, 0).getBlock().getType().isAir();
  }
}
