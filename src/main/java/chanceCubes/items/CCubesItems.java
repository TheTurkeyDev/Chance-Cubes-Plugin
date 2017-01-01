package chanceCubes.items;

import chanceCubes.config.CCubesSettings;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CCubesItems {

    public static ItemStack chancePendantT1;
    public static ItemStack chancePendantT2;
    public static ItemStack chancePendantT3;
    public static ItemStack creativePendant;
    public static ItemStack rewardSelectorPendant;
    public static ItemStack scanner;
    public static ItemStack silkPendant;
    public static ItemStack chanceCube;
    public static ItemStack chanceIcosahedron;
    public static ItemStack giantChanceCube;

    public static void loadItems() {
        chancePendantT1 = chancePendant(1, 10);
        chancePendantT2 = chancePendant(2, 25);
        chancePendantT3 = chancePendant(3, 50);

        silkPendant = silkTouchPendant();
        creativePendant = creativePendant();
        rewardSelectorPendant = rewardSelectorPendant();

        scanner = scanner();

        chanceCube = chanceCube();
        chanceIcosahedron = chanceIcosahedron();
        giantChanceCube = giantChanceCube();
    }

    public static ItemStack getMultipleOfItem(ItemStack itemStack, int amount) {
        ItemStack clone = itemStack.clone();
        clone.setAmount(amount);
        return clone;
    }

    private static ItemStack chancePendant(int tier, int chanceBonus) {
        ItemStack itemStack = new ItemStack(Material.ITEM_FRAME) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Chance Pendant Tier " + tier);
        meta.setLore(Arrays.asList("Increases the chance of Chance Cubes by:",
                "      +" + chanceBonus + " when the block is broken",
                "Only needs to be in the players inventory to work",
                "Note, this is NOT a percentage increase.",
                "Uses: " + CCubesSettings.pendantUses + "/" + CCubesSettings.pendantUses));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack silkTouchPendant() {
        ItemStack itemStack = new ItemStack(Material.ITEM_FRAME) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Silk Touch Pendant");
        meta.setLore(Arrays.asList("Use this pendant to retrieve Chance Cubes",
                "Player must hold this in hand to get the cube!"));
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack creativePendant() {
        ItemStack itemStack = new ItemStack(Material.ITEM_FRAME) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Creative Pendant");
        meta.setLore(Arrays.asList("Right click to change the chance",
                "of the inserted cubes."));
        meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack rewardSelectorPendant() {
        ItemStack itemStack = new ItemStack(Material.ITEM_FRAME) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Reward Selector Pendant");
        meta.setLore(Arrays.asList("Shift right click to change the reward.",
                "Right click a Chance Cube to summon the reward."));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack scanner() {
        ItemStack itemStack = new ItemStack(Material.ITEM_FRAME) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Scanner");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack chanceCube() {
        ItemStack itemStack = new ItemStack(Material.LAPIS_BLOCK) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Chance Cube");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack chanceIcosahedron() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_BLOCK) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Chance Icosahedron");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack giantChanceCube() {
        ItemStack itemStack = new ItemStack(Material.EMERALD_BLOCK) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Giant Chance Cube");
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    //TODO when creating block in world, use something that can be dug by hand but still requires silk touch
    //TODO when creating block, make it only breakable by the "Silk Touch Pendant"
    public static boolean isGenericChanceCube(ItemStack itemStack) {
        return itemStack.isSimilar(chanceCube) || itemStack.isSimilar(chanceIcosahedron) || itemStack.isSimilar(giantChanceCube);
    }
}
