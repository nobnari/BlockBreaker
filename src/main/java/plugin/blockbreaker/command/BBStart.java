package plugin.blockbreaker.command;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import plugin.blockbreaker.Initializer;
import plugin.blockbreaker.data.MetaMap;
import plugin.blockbreaker.data.Course;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.PlayerData;

public class BBStart extends SuperCommand {

    private final MetaMap meta;
    private final Initializer init;

    public BBStart(MetaMap meta, Initializer init) {
        this.meta = meta;
        this.init = init;
    }


    @Override
    public boolean PlayerDoneCommand(Player player, Command command, String[] args) {

        if (!meta.getStatus().get(player.getName())) {
            Course course = new Course(args);
            meta.getPlayerData().put(player.getName(), new PlayerData(player, course));
            meta.getStatus().put(player.getName(), true);

            GameArea ga = meta.getPlayerData().get(player.getName()).getGa();

            init.createBlock(ga);
        }else{
            player.sendMessage("ゲームはすでにはじまっている!!!");
        }
        return false;
    }
}
