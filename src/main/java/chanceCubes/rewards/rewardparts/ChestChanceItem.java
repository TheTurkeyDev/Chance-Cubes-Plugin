package chanceCubes.rewards.rewardparts;

import chanceCubes.items.CCubesItems;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ChestChanceItem {

    public static String[] elements = new String[]{"item:I", "chance:I", "meta:I", "amountMin:I", "amountMax:I"};

    private static Random rand = new Random();
    private int amountMax;
    private int amountMin;
    private int chance;
    private String item;
    private int meta;
    private String mod;

    public ChestChanceItem(String item, int meta, int chance, int amountMin, int amountMax) {
        this.mod = item.substring(0, item.indexOf(":"));
        this.item = item.substring(item.indexOf(":") + 1);
        this.meta = meta;
        this.chance = chance;
        this.amountMin = amountMin;
        this.amountMax = amountMax;
    }

    public int getChance() {
        return this.chance;
    }

    private ItemStack getItemStack(int amount, int meta) {
        ItemStack stack = RewardsUtil.getItemStack(mod, item, amount, meta);
        if (stack == null) {
            stack = CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_10_R1.ItemStack(RewardsUtil.getBlock(mod, item), amount, meta));
            if (stack.getType() == null)
                stack = CCubesItems.chanceCube.clone();
        }

        return stack;
    }

    public ItemStack getRandomItemStack() {
        return getItemStack(rand.nextInt(amountMax - amountMin) + amountMin, meta);
    }
}
