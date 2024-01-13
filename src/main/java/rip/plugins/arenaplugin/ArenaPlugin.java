package rip.plugins.arenaplugin;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import rip.plugins.arenaplugin.commands.GenerateArenaCommand;
import rip.plugins.arenaplugin.commands.TestCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ArenaPlugin extends JavaPlugin {

    private static ArenaPlugin arenaPlugin;

    private static ArrayList<ArenaTemplate> arenaTemplates = new ArrayList<>();

    @Override
    public void onEnable() {
        arenaPlugin = this;
        this.saveDefaultConfig();

//        this.getCommand("test").setExecutor(new TestCommand());
//        this.getCommand("generatearena").setExecutor(new GenerateArenaCommand());
//        this.getCommand("generatearena").setTabCompleter(new GenerateArenaCommand());


        World world = this.getServer().getWorld(this.getConfig().getString("world"));

        if(world == null){
            this.getLogger().severe("Arena world not found!");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        File base = this.getDataFolder();

//        create a folder called schematics in the data folder
        File schematics = new File(base, "schematics");
        if (!schematics.exists()) {
            schematics.mkdir();
        }

        File[] files = schematics.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".schem")) {
                ClipboardFormat format = ClipboardFormats.findByFile(file);
                try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {

                    Clipboard clipboard = reader.read();

                    ArenaTemplate arenaTemplate = new ArenaTemplate(clipboard, world, file.getName().replace(".schem",""));
                    arenaTemplates.add(arenaTemplate);


                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public static ArenaPlugin getInstance() {
        return arenaPlugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public ArrayList<ArenaTemplate> getArenaTemplates(){
        return arenaTemplates;
    }

    public List<String> getArenaTemplateNames(){
        ArrayList<String> output = new ArrayList<>();
        for(ArenaTemplate arenaTemplate : arenaTemplates){
            output.add(arenaTemplate.getName());
        }
        return output;
    }
}
