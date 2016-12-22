package chanceCubes.rewards.rewardparts;

import org.bukkit.inventory.ItemStack;

public class ItemPart {

    public static String[] elements = new String[]{"experienceAmount:I", "delay:I", "numberOfOrbs:I"};
    private int delay = 0;
    private ItemStack stack;

    public ItemPart(ItemStack stack) {
        this.stack = stack;
    }

    public ItemPart(ItemStack stack, int delay) {
        this.stack = stack;
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public ItemPart setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public ItemStack getItemStack() {
        return stack;
    }
}
