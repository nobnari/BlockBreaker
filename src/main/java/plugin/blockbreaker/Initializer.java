package plugin.blockbreaker;

import org.bukkit.Location;
import org.bukkit.Material;
import plugin.blockbreaker.data.GameArea;

public class Initializer {



    /**
     * ゲームエリアにガラスブロックを設置するメソッド
     */
    public void createBlock(GameArea ga) {
        Location ls = ga.getLs();
        int x = ga.getX();
        int y = ga.getY();
        int z = ga.getZ();
        int zc=z/2+1;

        for (int i = 1; i < x-1; i++) {
            for (int j = 0; j < y-1; j++) {
                    Location l = new Location(ls.getWorld(), ls.getX() + i, ls.getY() + j, ls.getZ()+zc);
                    l.getBlock().setType(Material.GLASS);
                }
            }
        }

    }

