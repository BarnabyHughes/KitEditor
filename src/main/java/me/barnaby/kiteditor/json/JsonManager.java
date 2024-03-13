package me.barnaby.kiteditor.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.barnaby.kiteditor.KitEditor;
import me.barnaby.kiteditor.objects.Kit;
import me.barnaby.kiteditor.objects.SerializableKit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonManager {

    private final KitEditor plugin;
    private final Gson gson;
    private final File adminKitLayouts;
    private final File playerKitLayouts;

    public JsonManager(KitEditor plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.adminKitLayouts = new File(plugin.getDataFolder(), "adminKitLayouts");
        this.playerKitLayouts = new File(plugin.getDataFolder(), "playerKitLayouts");

        // Create the data folder if it doesn't exist
        if (!adminKitLayouts.exists()) adminKitLayouts.mkdirs();
        if (!playerKitLayouts.exists()) playerKitLayouts.mkdirs();
    }

    public Kit getKit(String kitName) {

        File file = new File(adminKitLayouts, kitName + ".json");

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                SerializableKit kit = gson.fromJson(reader, SerializableKit.class);
                if (kit != null) return new Kit(kit.getName(), kit.getItemStacks(), kit.getArmor(), true, plugin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // Default value if data not found
    }

    public void saveKit(Kit kit) {
        File file = new File(adminKitLayouts, kit.getKitName() + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(new SerializableKit(kit.getKitName(),
                    kit.getItemStacksSerialized(kit.getItemStacks()),
                    kit.getItemStacksSerialized(kit.getArmor())), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveKitLayout(Player player, Kit kit) {
        File file = new File(playerKitLayouts, player.getName() + "_" + kit.getKitName() + ".json");

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(new SerializableKit(kit.getKitName(),
                    kit.getItemStacksSerialized(kit.getItemStacks()),
                    kit.getItemStacksSerialized(kit.getArmor())), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Kit getKitLayout(Player player, String kitName) {

        File file = new File(playerKitLayouts, player.getName() + "_" + kitName + ".json");

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                SerializableKit kit = gson.fromJson(reader, SerializableKit.class);
                if (kit != null) return new Kit(kit.getName(), kit.getItemStacks(), kit.getArmor(), true, plugin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // Default value if data not found
    }

}

