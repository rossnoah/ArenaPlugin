package rip.plugins.arenaplugin;

import com.fastasyncworldedit.bukkit.FaweBukkit;
import com.fastasyncworldedit.bukkit.adapter.SimpleBukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public class ArenaTemplate {


    private static int xOffset = -10;
    private static int zOffset = -10;

    private Clipboard clipboard;
    private World world;
    private String name;

    public ArenaTemplate(Clipboard clipboard, World world, String name) {
        this.clipboard = clipboard;
        this.world = world;
        this.name = name;
    }

    public Arena generate() {

        BlockVector3 origin = BlockVector3.at(1000 * xOffset, 0, 1000 * zOffset);
        com.sk89q.worldedit.world.World WEworld = new com.sk89q.worldedit.bukkit.BukkitWorld(this.world);
        BlockVector3 dimensions = clipboard.getDimensions();
        BlockVector3 halfDimensions = BlockVector3.at(dimensions.getX() / 2, 0, dimensions.getZ() / 2);
        BlockVector3 offset = clipboard.getMinimumPoint().subtract(clipboard.getOrigin());
        BlockVector3 pasteLocation = origin.subtract(offset);
        origin = origin.add(halfDimensions);

        Location corner1 = new Location(world, origin.getX(), origin.getY(), origin.getZ());
        Location corner2 = new Location(world, origin.getX() + clipboard.getDimensions().getX(), origin.getY() + clipboard.getDimensions().getY(), origin.getZ() + clipboard.getDimensions().getZ());
        Location spectatorLocation = new Location(world, origin.getX(), origin.getY() + 10, origin.getZ());

        int thirdX = clipboard.getDimensions().getX() / 3;

        Location blueSpawn = new Location(world, origin.getX() - thirdX, origin.getY() + 1, origin.getZ());
        Location redSpawn = new Location(world, origin.getX() + thirdX, origin.getY() + 1, origin.getZ());


        Arena output = new Arena(UUID.randomUUID(), spectatorLocation, blueSpawn, redSpawn, corner1, corner2);


        Bukkit.getScheduler().runTaskAsynchronously(ArenaPlugin.getInstance(), () -> {
            clipboard.paste(WEworld, pasteLocation, false, false, false, null);
            output.setIsLoaded(true);
        });





        increaseOffset();

        return output;


    }

    public String getName() {
        return name;
    }

    private void increaseOffset() {
        if (xOffset < 10) {
            xOffset++;
        } else if (xOffset == 10) {
            xOffset = -10;
            zOffset++;
        }

    }


}
