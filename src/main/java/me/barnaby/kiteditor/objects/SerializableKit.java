package me.barnaby.kiteditor.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
public class SerializableKit {

    @Getter
    private final String name;

    @Getter
    private List<String> itemStacks;

    @Getter
    private List<String> armor;

}
