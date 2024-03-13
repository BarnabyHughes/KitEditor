package me.barnaby.kiteditor.objects;

import lombok.Getter;
import me.barnaby.kiteditor.KitEditor;
import me.barnaby.kiteditor.gui.KitGui;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class KitManager {

    @Getter
    private final Map<Player, KitGui> managingKits = new HashMap<>();
    private KitEditor kitEditor;
    public KitManager(KitEditor kitEditor) {
        this.kitEditor = kitEditor;
    }

}
