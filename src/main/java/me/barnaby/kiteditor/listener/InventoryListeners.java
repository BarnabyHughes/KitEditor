package me.barnaby.kiteditor.listener;

import me.barnaby.kiteditor.KitEditor;
import me.barnaby.kiteditor.gui.KitGui;
import me.barnaby.kiteditor.gui.PlayerKitGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryListeners implements Listener {

    private final KitEditor kitEditor;

    public InventoryListeners(KitEditor kitEditor) {
        this.kitEditor = kitEditor;
    }

    @EventHandler
    public void onCloseInv(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            if (!kitEditor.getKitManager().getManagingKits().containsKey(player)) return;
            KitGui kitGui = kitEditor.getKitManager().getManagingKits().get(player);
            kitGui.finish(player);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            if (!kitEditor.getKitManager().getManagingKits().containsKey(player)) return;
            KitGui kitGui = kitEditor.getKitManager().getManagingKits().get(player);
            if (kitGui instanceof PlayerKitGui) {
                if (event.getClickedInventory().getType() == InventoryType.PLAYER
                        || event.getInventory().getType() == InventoryType.PLAYER
                || event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                        || event.getAction() == InventoryAction.COLLECT_TO_CURSOR
                        || event.getClick().isShiftClick()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!kitEditor.getKitManager().getManagingKits().containsKey(player)) return;
        KitGui kitGui = kitEditor.getKitManager().getManagingKits().get(player);
        event.setCancelled(true);
    }
}
