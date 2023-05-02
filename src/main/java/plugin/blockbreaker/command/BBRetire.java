package plugin.blockbreaker.command;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import plugin.blockbreaker.Finisher;
import plugin.blockbreaker.data.Meta;

public class BBRetire extends SuperCommand {

  private final Meta meta;
  Finisher fini;

  public BBRetire(Meta meta, Finisher fini) {
    this.meta = meta;
    this.fini = fini;
  }


  @Override
  public boolean PlayerDoneCommand(Player player, Command command, String[] args) {
    if (meta.getStatus().get(player.getName())) {

      fini.blockReset(player);
      fini.gameModeReset(player);

      meta.getStatus().put(player.getName(), false);
      player.sendMessage("ゲームをリタイアしました");


    } else {
      player.sendMessage("ゲームはまだはじまっていない…");
    }

    return false;
  }


}
