package plugin.blockbreaker.command;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class MMRecord extends SuperCommand {

  @Override
  public boolean PlayerDoneCommand(Player player, Command command, String[] args) {
    return false;
  }
}
