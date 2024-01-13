package rip.plugins.arenaplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rip.plugins.arenaplugin.Arena;
import rip.plugins.arenaplugin.ArenaPlugin;
import rip.plugins.arenaplugin.ArenaTemplate;

import java.util.ArrayList;
import java.util.List;

public class GenerateArenaCommand implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("You must be a player to use this command!");
            return true;
        }

        if(strings.length == 0){
            commandSender.sendMessage("Please specify an arena template!");
            return true;
        }

        Player player = (Player) commandSender;

        String templateName = strings[0];

        ArrayList<ArenaTemplate> arenaTemplates = ArenaPlugin.getInstance().getArenaTemplates();

        ArenaTemplate template = null;
        for(ArenaTemplate arenaTemplate : arenaTemplates){
            if(arenaTemplate.getName().equalsIgnoreCase(templateName)){
                template = arenaTemplate;
                break;
            }
        }

        if(template == null){
            commandSender.sendMessage("Arena template not found!");
            return true;
        }

        Arena arena = template.generate();

        Bukkit.getScheduler().runTaskTimer(ArenaPlugin.getInstance(), () -> {
            if(arena.isLoaded()){
                Bukkit.getScheduler().cancelTasks(ArenaPlugin.getInstance());

//                find the top block at the spectator location
                int spectatorY = arena.getSpectatorLocation().getBlockY();

                for(int i = spectatorY; i < 320; i++){
                    if(arena.getSpectatorLocation().getWorld().getBlockAt(arena.getSpectatorLocation().getBlockX(), i, arena.getSpectatorLocation().getBlockZ()).getType().isAir()){
                        spectatorY = i;
                        break;
                    }
                }
                player.sendMessage("Teleporting you to the arena!");

                Location spectatorLocation = new Location(arena.getSpectatorLocation().getWorld(), arena.getSpectatorLocation().getBlockX(), spectatorY, arena.getSpectatorLocation().getBlockZ());
                player.teleport(spectatorLocation);



            }else{
                player.sendMessage("Arena is still loading!");
            }
        }, 0, 20);



        commandSender.sendMessage("Arena generated with id: "+arena.getId());


//        print out all the locations to the player
        player.sendMessage("Spectator Location: "+arena.getSpectatorLocation());
        player.sendMessage("Blue Spawn: "+arena.getBlueSpawn());
        player.sendMessage("Red Spawn: "+arena.getRedSpawn());
        player.sendMessage("Corner 1: "+arena.getCorner1());
        player.sendMessage("Corner 2: "+arena.getCorner2());


        return true;


    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1){
            return ArenaPlugin.getInstance().getArenaTemplateNames();
        }

        return null;
    }


}
