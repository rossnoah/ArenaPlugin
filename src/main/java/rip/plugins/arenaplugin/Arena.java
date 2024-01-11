package rip.plugins.arenaplugin;


import com.sk89q.worldedit.extent.clipboard.Clipboard;
import org.bukkit.Location;

public class Arena {
    private String name;
    private Clipboard clipboard;
    private Location location;
    private Location spectatorLocation;
    private Location blueSpawn;
    private Location redSpawn;
    private int maxPlayers;

    public Arena(String name, Clipboard clipboard, Location location, Location spectatorLocation, Location blueSpawn, Location redSpawn, int maxPlayers) {
        this.name = name;
        this.clipboard = clipboard;
        this.location = location;
        this.spectatorLocation = spectatorLocation;
        this.blueSpawn = blueSpawn;
        this.redSpawn = redSpawn;
        this.maxPlayers = maxPlayers;
    }








}
