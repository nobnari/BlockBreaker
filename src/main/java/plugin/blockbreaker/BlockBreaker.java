package plugin.blockbreaker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.blockbreaker.command.BBRecord;
import plugin.blockbreaker.command.BBRetire;
import plugin.blockbreaker.command.BBStart;
import plugin.blockbreaker.data.Meta;

public final class BlockBreaker extends JavaPlugin {

  @Override
  public void onEnable() {
    saveDefaultConfig();

    Meta meta = new Meta();
    Finisher fini = new Finisher(meta);
    Initializer init = new Initializer(meta);

    Bukkit.getPluginManager().registerEvents(new EventListener(meta), this);

    getCommand("bbstart").setExecutor(new BBStart(this, meta, init));
    getCommand("bbretire").setExecutor(new BBRetire(meta, fini));
    getCommand("bbrecord").setExecutor(new BBRecord());

  }


}
