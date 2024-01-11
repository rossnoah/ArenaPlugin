package rip.plugins.arenaplugin;

import org.bukkit.plugin.java.JavaPlugin;
import rip.plugins.arenaplugin.commands.TestCommand;

public final class ArenaPlugin extends JavaPlugin {

    private static ArenaPlugin arenaPlugin;

    @Override
    public void onEnable() {
        arenaPlugin=this;
        this.saveDefaultConfig();

        this.getCommand("test").setExecutor(new TestCommand());

    }

    public static ArenaPlugin getInstance(){
        return arenaPlugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
