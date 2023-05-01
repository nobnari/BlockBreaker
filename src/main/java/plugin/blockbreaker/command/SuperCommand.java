package plugin.blockbreaker.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * センダーがコマンド実行した時、プレイヤーかどうかを判別する基底クラス
 */
public abstract class SuperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            return PlayerDoneCommand(player,command,args);
        }else{
            return false;
        }
    }
    /**
     * プレイヤー入力時、実行
     * @param player　プレイヤー
     * @return 処理の実行有無判定
     */
    public abstract boolean PlayerDoneCommand(Player player ,Command command, String[] args) ;

}