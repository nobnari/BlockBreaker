package plugin.blockbreaker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import plugin.blockbreaker.data.MetaMap;

public class EventListener implements Listener {
    private final MetaMap data;

    public EventListener(MetaMap data){
        this.data = data;
    }

    /**
     * プレイヤーマインクラフト自体に参加した時にステータスに名前とFALSEを追加。
     * @param e　プレイヤーjoin
     */
    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent e){
        data.getStatus().put(e.getPlayer().getName(),Boolean.FALSE);
    }
}
