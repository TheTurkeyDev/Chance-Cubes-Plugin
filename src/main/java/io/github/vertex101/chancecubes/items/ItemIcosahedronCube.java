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

public class ItemIcosahedronCube {
    public static List<Text> icosLore = Arrays.asList(Text.of(TextColors.GOLD, "A block to give a random event"));
    public static List<Enchantment> icosEnchant = Arrays.asList(Enchantment.of(EnchantmentTypes.SILK_TOUCH, 1));
    public static ItemStack icoscube = ItemStack.builder()
            .itemType(ItemTypes.DIAMOND_BLOCK)
            .quantity(1)
            .add(Keys.DISPLAY_NAME, Text.of(TextColors.LIGHT_PURPLE, "Chance Cube"))
            .add(Keys.ITEM_LORE, icosLore)
            .add(Keys.ITEM_ENCHANTMENTS, icosEnchant)
            .add(Keys.HIDE_ENCHANTMENTS, true)
            .build();
}
