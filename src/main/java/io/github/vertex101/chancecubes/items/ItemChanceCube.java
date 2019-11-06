package io.github.vertex101.chancecubes.items;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.*;

public class ItemChanceCube {
    public static List<Text> cubeLore = Arrays.asList(Text.of(TextColors.GOLD, "A block to give a random event"));
    public static ItemStack chancecube = ItemStack.builder()
            .itemType(ItemTypes.LAPIS_BLOCK)
            .quantity(1)
            .add(Keys.DISPLAY_NAME, Text.of(TextColors.LIGHT_PURPLE, "Chance Cube"))
            .add(Keys.ITEM_LORE, cubeLore)
            .build();
}
