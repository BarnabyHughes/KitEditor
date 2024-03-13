package me.barnaby.kiteditor.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.barnaby.kiteditor.KitEditor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Getter
public class Kit implements Serializable {
    private final String kitName;
    @Setter
    private List<ItemStack> itemStacks;
    @Setter
    private List<ItemStack> armor;
    private final KitEditor kitEditor;

    public Kit(String kitName, List<ItemStack> items, List<ItemStack> armor, KitEditor kitEditor) {
        this.itemStacks = items;
        this.kitName = kitName;
        this.armor = armor;
        this.kitEditor = kitEditor;
    }

    public Kit(String kitName, List<String> serializedItems, List<String> serializedArmor, boolean ignore, KitEditor kitEditor /*stupid ide*/) {
        this.kitName = kitName;
        this.kitEditor = kitEditor;
        itemStacks = new ArrayList<>();
        armor = new ArrayList<>();

        serializedItems.forEach(item -> {
            itemStacks.add(setItemStackDeserialized(item));
        });
        serializedArmor.forEach(item -> {
            armor.add(setItemStackDeserialized(item));
        });
    }

    public void apply(Player p) {
        for (int i = 0; i < 36; i++) {
            ItemStack item = this.getItemStacks().get(i);
            if (item == null) continue;
            p.getInventory().setItem(i, item);
            p.getInventory().setHelmet(this.getArmor().get(0));
            p.getInventory().setChestplate(this.getArmor().get(1));
            p.getInventory().setLeggings(this.getArmor().get(2));
            p.getInventory().setBoots(this.getArmor().get(3));
        }
        p.sendMessage(ChatColor.GREEN + "Kit Applied!");
        kitEditor.getSelectedKits().put(p, this);
    }

    public List<String> getItemStacksSerialized(List<ItemStack> itemStacks) {
        List<String> encodedObjects = new ArrayList<>();

        itemStacks.forEach(itemStack -> {
            String encodedObject;
            try {
                ByteArrayOutputStream io = new ByteArrayOutputStream();
                BukkitObjectOutputStream os = new BukkitObjectOutputStream(io);
                os.writeObject(itemStack);
                os.flush();
                byte[] serializedObject = io.toByteArray();
                encodedObject = new String(Base64.getEncoder().encode(serializedObject));
                encodedObjects.add(encodedObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return encodedObjects;
    }

    public ItemStack setItemStackDeserialized(String itemstack) {


        byte[] serializedObject = Base64.getDecoder().decode(itemstack);

        try {
            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream is = new BukkitObjectInputStream(in);
            return (ItemStack) is.readObject();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }




    /*public List<Map<String, Object>> getItemStacksSerialized(List<ItemStack> itemStacks) {
        List<Map<String, Object>> returnItems = new ArrayList<>();

        itemStacks.forEach(itemStack -> {
            if (itemStack != null) //returnItems.add(new ItemStack(Material.AIR).serialize());
                returnItems.add(itemStack.serialize());
        });
        return returnItems;
    }

     */



}
