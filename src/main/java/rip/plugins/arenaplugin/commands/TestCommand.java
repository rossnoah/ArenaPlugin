package rip.plugins.arenaplugin.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import rip.plugins.arenaplugin.ArenaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }

        Player player = (Player) commandSender;


        if(strings.length == 0){
            player.sendMessage("Please specify a file!");
            return true;
        }
        String fileName = strings[0]+".schem";

        File base = ArenaPlugin.getInstance().getDataFolder();

        File file = new File(base, fileName);

        if(!file.exists()){
            player.sendMessage("File does not exist!");
            return true;
        }
    



        Clipboard clipboard;

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();

            World playerWorld = player.getWorld();
            Location playerLocation = player.getLocation();

            com.sk89q.worldedit.world.World world = new com.sk89q.worldedit.bukkit.BukkitWorld(playerWorld);
            BlockVector3 origin = BlockVector3.at(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ());

            EditSession editSession = clipboard.paste(world, origin);

            editSession.commit();


        } catch (FileNotFoundException e) {
            player.sendMessage("File not found!");
            return true;
        } catch (IOException e) {
            player.sendMessage("Error reading file!");
            return true;
        }




return true;
    }
}
