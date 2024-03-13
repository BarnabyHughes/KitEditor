package me.barnaby.kiteditor.commands;

import me.barnaby.kiteditor.KitEditor;
import me.barnaby.kiteditor.objects.Kit;
import me.barnaby.kiteditor.gui.PlayerKitGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCommand implements CommandExecutor {

    private final KitEditor kitEditor;
    public KitCommand(KitEditor kitEditor) {
        this.kitEditor = kitEditor;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!s.equalsIgnoreCase("kit")) return true;
        if (commandSender instanceof Player p) {
            if (strings.length == 1) {
                Kit kit = kitEditor.getJsonManager().getKit(strings[0]);
                if (kit == null) {
                    p.sendMessage(ChatColor.RED + "Kit " + strings[0] + " not found!");
                    return true;
                }
                Kit kitLayout = kitEditor.getJsonManager().getKitLayout(p, strings[0]);
                if (kitLayout != null) kit = kitLayout;
                p.getInventory().clear();
                p.getInventory().setBoots(new ItemStack(Material.AIR));
                p.getInventory().setLeggings(new ItemStack(Material.AIR));
                p.getInventory().setChestplate(new ItemStack(Material.AIR));
                p.getInventory().setHelmet(new ItemStack(Material.AIR));

                kit.apply(p);

            }

            if (strings.length != 2) return true;
            if (strings[0].equalsIgnoreCase("edit")) {
                Kit kit = kitEditor.getJsonManager().getKit(strings[1]);
                if (kit == null) {
                    p.sendMessage(ChatColor.RED + "Kit " + strings[1] + " not found!");
                    return true;
                }
                Kit kitLayout = kitEditor.getJsonManager().getKitLayout(p, strings[1]);
                PlayerKitGui playerKitGui;
                if (kitLayout == null) playerKitGui = new PlayerKitGui(kit, kitEditor, p);
                else playerKitGui = new PlayerKitGui(kitLayout, kitEditor,p);

                playerKitGui.open(p);
            }
        }

        return false;
    }
}
