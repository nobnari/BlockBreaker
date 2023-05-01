package plugin.blockbreaker.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
@Getter
@Setter
public class GameArea {
    Location ls;
    int X=6;
    int Y=7;
    int Z=3;

    public GameArea(Player player) {
        World w = player.getWorld();
        Location l = player.getLocation();
        Vector vec = l.getDirection().multiply(5);
        Location l0 = new Location(w, l.getX() + vec.getX(), l.getY(), l.getZ() + vec.getZ());
        this.ls = (new Location(w, l0.getX() - 2, l0.getY(), l0.getZ() - 1));
    }


}


