package rip.plugins.arenaplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RejoinListener implements Listener{

    @EventHandler
    public void onRejoin(PlayerJoinEvent event){
        World arenaWorld = Bukkit.getWorld(ArenaPlugin.getInstance().getConfig().getString("world"));
        Location spectatorLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
        if(event.getPlayer().getWorld()==arenaWorld){
            event.getPlayer().teleport(spectatorLocation);
        }
    }
}
