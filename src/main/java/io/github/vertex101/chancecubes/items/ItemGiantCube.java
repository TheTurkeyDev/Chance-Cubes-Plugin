package io.github.vertex101.chancecubes.items;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;
import java.util.List;

public class ItemGiantCube {
    public static List<Text> giantLore = Arrays.asList(Text.of(TextColors.GOLD, "A block to give a random event"));
    public static List<Enchantment> giantEnchant = Arrays.asList(Enchantment.of(EnchantmentTypes.SILK_TOUCH, 1));
    public static ItemStack giantcube = ItemStack.builder()
            .itemType(ItemTypes.EMERALD_BLOCK)
            .quantity(1)
            .add(Keys.DISPLAY_NAME, Text.of(TextColors.LIGHT_PURPLE, "Chance Cube"))
            .add(Keys.ITEM_LORE, giantLore)
            .add(Keys.ITEM_ENCHANTMENTS, giantEnchant)
            .add(Keys.HIDE_ENCHANTMENTS, true)
            .build();
}
