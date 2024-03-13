package me.barnaby.kiteditor.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.barnaby.kiteditor.KitEditor;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListeners implements Listener {

    private final KitEditor kitEditor;
    public RespawnListeners(KitEditor editor) {
        this.kitEditor = editor;
    }

    @EventHandler
    public void onDie(PlayerDeathEvent event) {
        Location loc = BukkitAdapter.adapt(event.getEntity().getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        set.forEach(region -> {
            System.out.println(region);
            if (kitEditor.getConfigManager().getConfig().getStringList("autokit-respawn-regions")
                    .contains(region.getId())) {
                kitEditor.getRespawnWithKits().put(event.getEntity(), kitEditor.getSelectedKits().get(event.getEntity()));
            }
        });
    }

    @EventHandler
    public void respawnEvent(PlayerRespawnEvent event) {
        if (kitEditor.getRespawnWithKits().containsKey(event.getPlayer())) {
            kitEditor.getRespawnWithKits().get(event.getPlayer()).apply(event.getPlayer());
            event.getPlayer().sendMessage(ChatColor.GREEN + "Automatically re-applied kit!");
            kitEditor.getRespawnWithKits().remove(event.getPlayer());
        }
    }
}
