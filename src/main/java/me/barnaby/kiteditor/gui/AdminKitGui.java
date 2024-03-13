package me.barnaby.kiteditor.gui;

import me.barnaby.kiteditor.KitEditor;
import me.barnaby.kiteditor.objects.Kit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminKitGui extends KitGui{
    private final Kit kit;
    private final Inventory inventory;
    private final KitEditor kitEditor;
    public AdminKitGui(Kit kit, KitEditor kitEditor) {
        this.kitEditor = kitEditor;
        this.kit = kit;
        inventory = Bukkit.createInventory(null, 54, "Editing Kit " + kit.getKitName());

        for (int i = 0; i<36; i++) {
            if (kit.getItemStacks().size() <= i) break;
            ItemStack item = kit.getItemStacks().get(i);
            inventory.setItem(i, item);
        }

        for (int i = 36; i<53; i++) {
            inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }


        List<ItemStack> armor = kit.getArmor();
        if (armor.size() < 4) {
            inventory.setItem(50, addMeta(new ItemStack(Material.LEATHER_HELMET)));
            inventory.setItem(51, addMeta(new ItemStack(Material.LEATHER_CHESTPLATE)));
            inventory.setItem(52, addMeta(new ItemStack(Material.LEATHER_LEGGINGS)));
            inventory.setItem(53, addMeta(new ItemStack(Material.LEATHER_BOOTS)));
        }
        else {
            for (int i = 0; i < 4; i++) {
                ItemStack armorPiece = armor.get(i);
                if (armorPiece == null) continue;
                inventory.setItem(50 + i, armorPiece);
            }
        }
    }


    public ItemStack addMeta(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Add " + itemStack.getType().name().toLowerCase() + " here");
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Swap me out!"));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void open(Player player) {
        player.openInventory(inventory);
        kitEditor.getKitManager().getManagingKits().put(player, this);
    }

    public void finish(Player player) {
        kit.setArmor(Arrays.asList(inventory.getItem(50), inventory.getItem(51), inventory.getItem(52), inventory.getItem(53)));
        kit.setItemStacks(Arrays.asList(inventory.getContents()));
        kitEditor.getJsonManager().saveKit(kit);
        kitEditor.getKitManager().getManagingKits().remove(player);
        player.sendMessage(ChatColor.GREEN + "Kit saved!");
    }
}
