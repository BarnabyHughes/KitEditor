package me.barnaby.kiteditor.commands;

import me.barnaby.kiteditor.KitEditor;
import me.barnaby.kiteditor.gui.AdminKitGui;
import me.barnaby.kiteditor.objects.Kit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class KitAdmin implements CommandExecutor {

    private final KitEditor kitEditor;
    public KitAdmin(KitEditor kitEditor) {
        this.kitEditor = kitEditor;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!s.equalsIgnoreCase("kitadmin")) return true;
        if (commandSender instanceof Player p) {
            if (strings.length != 2) return true;
            if (strings[0].equalsIgnoreCase("edit")) {
                Kit kit = kitEditor.getJsonManager().getKit(strings[1]);
                if (kit == null) {
                    p.sendMessage(ChatColor.RED + "Kit " + strings[1] + " not found!");
                    return true;
                }
                AdminKitGui adminKitGui = new AdminKitGui(kit, kitEditor);
                adminKitGui.open(p);
            }
            if (strings[0].equalsIgnoreCase("create")) {
                if (kitEditor.getJsonManager().getKit(strings[1]) != null) {
                    p.sendMessage(ChatColor.RED + "A kit with this name already exists!");
                    return true;
                }
                Kit kit = new Kit(strings[1], new ArrayList<>(), new ArrayList<>(), kitEditor);
                AdminKitGui adminKitGui = new AdminKitGui(kit, kitEditor);
                adminKitGui.open(p);
            }
        }
        return false;
    }
}
