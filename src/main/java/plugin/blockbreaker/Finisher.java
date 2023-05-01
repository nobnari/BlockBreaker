package plugin.blockbreaker;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.MetaMap;

import java.util.List;

public class Finisher {
    MetaMap meta;

    public Finisher(MetaMap meta) {
        this.meta = meta;
    }

    /**
     * ゲーム終了後ブロックを元に戻す
     * @param player　プレイヤー
     */
    public void blockReset(Player player) {
        GameArea ga = meta.getPlayerData().get(player.getName()).getGa();
        List<Material> lastBlocks= meta.getPlayerData().get(player.getName()).getLastBlocks();
        Location ls = ga.getLs();
        int x = ga.getX();
        int y = ga.getY();
        int z = ga.getZ();
        int m=0;
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
}
