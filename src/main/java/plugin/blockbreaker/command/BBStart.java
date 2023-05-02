package plugin.blockbreaker.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import plugin.blockbreaker.BlockBreaker;
import plugin.blockbreaker.Initializer;
import plugin.blockbreaker.data.Course;
import plugin.blockbreaker.data.GameArea;
import plugin.blockbreaker.data.Meta;
import plugin.blockbreaker.data.ReserveData;

public class BBStart extends SuperCommand {

  BlockBreaker main;
  private final Meta meta;
  private final Initializer init;

  public BBStart(BlockBreaker main, Meta meta, Initializer init) {
    this.main = main;
    this.meta = meta;
    this.init = init;
  }


  @Override
  public boolean PlayerDoneCommand(Player player, Command command, String[] args) {
    if (!meta.getStatus().get(player.getName())) {
      Course course = new Course(main, args);
      meta.getReserveData().put(player.getName(), new ReserveData(player, course));
      meta.getStatus().put(player.getName(), true);
      player.setGameMode(GameMode.CREATIVE);

      GameArea ga = meta.getReserveData().get(player.getName()).getGa();
      init.glassAirSetter(ga);
      init.monolithSetter(course, ga);

      player.sendMessage("ゲームスタート！");
    } else {
      player.sendMessage("ゲームはすでにはじまっている!!!");
    }
    return false;
  }
}
