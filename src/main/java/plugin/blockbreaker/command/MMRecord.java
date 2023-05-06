package plugin.blockbreaker.command;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import plugin.blockbreaker.DAO.Connector;

public class MMRecord extends SuperCommand {

  public static final String EASY = "easy";
  public static final String NORMAL = "normal";
  public static final String HARD = "hard";
  public static final String LOG = "log";
  public static final String LEAVES = "leaves";
  public static final String RECENT = "recent";
  Connector con = new Connector();

  /**
   * 引数ごとにランキングを分ける 引数なしの時は全ての中からランキングを表示
   *
   * @param player 　プレイヤー
   * @param args   　引数
   * @return 処理の実行有無判定
   */
  @Override
  public boolean PlayerDoneCommand(Player player, Command command, String[] args) {
    if (args.length == 1) {
      if (EASY.equals(args[0]) || NORMAL.equals(args[0]) || HARD.equals(args[0])
          || LOG.equals(args[0]) || LEAVES.equals(args[0])) {
        con.displayScores(player, args[0]);
      } else if (RECENT.equals(args[0])) {
        con.displayRecent(player);
      }
    } else {
      con.displayScores(player);
    }
    return false;
  }
}
