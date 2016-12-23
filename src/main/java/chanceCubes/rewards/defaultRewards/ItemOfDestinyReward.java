package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.RomanNumerals;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemOfDestinyReward implements IChanceCubeReward {

    private Random rand = new Random();

    private void changeEnchantAmount(final Item item, final Player player) {
        RewardsUtil.scheduleTask(() -> {
            int i = rand.nextInt(9);
            int amount = i < 5 ? 1 : i < 8 ? 2 : 3;
            player.sendMessage(amount + " random enchants will be added!");
            player.sendMessage("Selecting random enchant to give to the item");
            changeEnchants(item, amount, 0, player);
        }, 50);
    }

    private void changeEnchants(final Item item, final int enchants, final int iteration, final Player player) {
        RewardsUtil.scheduleTask(() -> {
            if (iteration < enchants) {
                Enchantment ench = randomEnchantment();
                int level = ench.getStartLevel() + rand.nextInt(ench.getMaxLevel());
                item.getItemStack().addEnchantment(ench, level);
                player.sendMessage(ench.getName() + " " + RomanNumerals.toString(level) + " Has been added to the item!");
                changeEnchants(item, enchants, iteration + 1, player);
            }
            else {
                player.sendMessage("Your item of destiny is complete! Enjoy!");
                item.setPickupDelay(0);
            }
        }, 50);
    }

    private void changeItem(final Item item, final int iteration, final Player player) {
        RewardsUtil.scheduleTask(() -> {
            if (iteration + 1 > 17) {
                player.sendMessage("Random item selected");
                player.sendMessage("Selecting number of enchants to give item");
                changeEnchantAmount(item, player);
            }
            else {
                item.setItemStack(new ItemStack(RewardsUtil.getRandomItem(), 1));
                changeItem(item, iteration + 1, player);
            }
        }, 5);
    }

    @Override
    public int getChanceValue() {
        return 40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Item_Of_Destiny";
    }

    public Enchantment randomEnchantment() {
        Enchantment ench = null;
        while (ench == null)
            ench = Enchantment.values()[rand.nextInt(Enchantment.values().length)];

        return ench;
    }

    @Override
    public void trigger(Location location, final Player player) {
        final Item item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
        item.setPickupDelay(100000);
        player.sendMessage("Selecting random item");
        RewardsUtil.scheduleTask(() -> changeItem(item, 0, player), 5);
    }

}
