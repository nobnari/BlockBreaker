package plugin.blockbreaker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.blockbreaker.command.BBRecord;
import plugin.blockbreaker.command.BBRetire;
import plugin.blockbreaker.command.BBStart;
import plugin.blockbreaker.data.MetaMap;

public final class BlockBreaker extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        MetaMap meta = new MetaMap();
        Finisher fini =new Finisher(meta);
        Initializer init =new Initializer();

        Bukkit.getPluginManager().registerEvents(new EventListener(meta),this);

        getCommand("bbstart").setExecutor(new BBStart(meta,init));
        getCommand("bbretire").setExecutor(new BBRetire(meta,fini));
        getCommand("bbrecord").setExecutor(new BBRecord());

    }


}
