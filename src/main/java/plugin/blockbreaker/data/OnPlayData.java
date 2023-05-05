package plugin.blockbreaker.data;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Setter
@Getter
public class OnPlayData {

  Material lastTouchMaterial = Material.CAKE;
  int chainCount;
  List<Block> touchedBlocks = new ArrayList<>();
  int score;
  boolean gameOver = false;

}

