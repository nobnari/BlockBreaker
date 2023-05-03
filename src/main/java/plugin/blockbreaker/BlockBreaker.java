package plugin.blockbreaker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.blockbreaker.command.MMRecord;
import plugin.blockbreaker.command.MMRetire;
import plugin.blockbreaker.command.MMStart;
import plugin.blockbreaker.data.Meta;

public final class BlockBreaker extends JavaPlugin {

  @Override
  public void onEnable() {
    saveDefaultConfig();

    Meta meta = new Meta();
    Finisher fini = new Finisher(meta);
    Initializer init = new Initializer(meta);

    Bukkit.getPluginManager().registerEvents(new EventListener(meta), this);

    getCommand("mmstart").setExecutor(new MMStart(this, meta, init));
    getCommand("mmretire").setExecutor(new MMRetire(meta, fini));
    getCommand("mmrecord").setExecutor(new MMRecord());

  }


}
