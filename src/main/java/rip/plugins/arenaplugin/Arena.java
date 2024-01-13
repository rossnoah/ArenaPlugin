package rip.plugins.arenaplugin;

import org.bukkit.Location;

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
        while (currentLocation.getY() > 0) {
            if (currentLocation.getBlock().getType().isEmpty()) {
                return currentLocation; // Return this location if the block is solid
            }
            currentLocation.setY(currentLocation.getY() - 1); // Decrease Y to check the block below
        }
        currentLocation.setY(10); // Set Y to 10 if no solid block was found
        return currentLocation;

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
