package me.barnaby.kiteditor;

import lombok.Getter;
import me.barnaby.kiteditor.commands.KitAdmin;
import me.barnaby.kiteditor.commands.KitCommand;
import me.barnaby.kiteditor.config.ConfigManager;
import me.barnaby.kiteditor.json.JsonManager;
import me.barnaby.kiteditor.listener.InventoryListeners;
import me.barnaby.kiteditor.listener.RespawnListeners;
import me.barnaby.kiteditor.objects.Kit;
import me.barnaby.kiteditor.objects.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public class KitEditor extends JavaPlugin {

    JsonManager jsonManager = new JsonManager(this);

    KitManager kitManager = new KitManager(this);

    ConfigManager configManager = new ConfigManager(this);

    private final Map<Player, Kit> respawnWithKits = new HashMap<>();

    private final Map<Player, Kit> selectedKits = new HashMap<>();

    @Override
    public void onEnable() {
        configManager.init();

        registerCommands();
        registerListeners();
    }



    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(this), this);
        pm.registerEvents(new RespawnListeners(this), this);

        //pm.registerEvents();
    }

    private void registerCommands() {
        getCommand("kitadmin").setExecutor(new KitAdmin(this));
        getCommand("kit").setExecutor(new KitCommand(this));

    }
}