package rip.plugins.arenaplugin;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class Arena {
    private UUID id;
    private Location spectatorLocation;
    private Location blueSpawn;
    private Location redSpawn;
    private Location corner1;
    private Location corner2;
    private boolean isLoaded = false;

    public Arena(UUID id, Location spectatorLocation, Location blueSpawn, Location redSpawn, Location corner1, Location corner2) {
        this.id = id;
        this.spectatorLocation = spectatorLocation;
        this.blueSpawn = blueSpawn;
        this.redSpawn = redSpawn;
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public UUID getId() {
        return id;
    }

    public Location getSpectatorLocation() {
        Location currentLocation = this.spectatorLocation.clone(); // Clone to avoid modifying the original
        World world = currentLocation.getWorld();
        int x = currentLocation.getBlockX();
        int y = world.getMaxHeight();
        int z = currentLocation.getBlockZ();
        while (isBlockAboveAir(world, x, y, z))
        {
            y--;
            currentLocation.setY(y);
        }
        if(y < 0)
        {
            y = 10;
            currentLocation.setY(y);
        }
        return currentLocation;

    }

    private boolean isBlockAboveAir(World world, final int x, final int y, final int z)
    {
        if (y > world.getMaxHeight())
        {
            return true;
        }
        return world.getBlockAt(x, y, z).getType().isCollidable();
    }
    public Location getBlueSpawn() {
        return blueSpawn;
    }

    public Location getRedSpawn() {
        return redSpawn;
    }

    public Location getCorner1() {
        return corner1;
    }

    public Location getCorner2() {
        return corner2;
    }

    public boolean isLoaded() {
        return isLoaded;
    }
    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
}
