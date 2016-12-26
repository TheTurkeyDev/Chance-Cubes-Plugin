package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryBombReward implements IChanceCubeReward {

    private Random rand = new Random();

    @Override
    public int getChanceValue() {
        return -65;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Inventory_Bomb";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (ItemStack stack : player.getInventory()) {
            if (stack == null)
                continue;

            Item item = location.getWorld().dropItemNaturally(location, stack);
            item.setPickupDelay(40);
        }

        for (ItemStack stack : player.getInventory().getArmorContents()) {
            if (stack == null)
                continue;

            Item item = location.getWorld().dropItemNaturally(location, stack);
            item.setPickupDelay(40);
        }

        while (player.getInventory().firstEmpty() > -1)
            player.getInventory().addItem(new ItemStack(Material.DEAD_BUSH, 64));

        ItemStack[] armor = new ItemStack[4];
        for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
            ItemStack stack = new ItemStack(Material.DEAD_BUSH, 64);
            ItemMeta meta = stack.getItemMeta();
            if (i == 0) {
                meta.setDisplayName("ButtonBoy");
                stack.setAmount(13);
            }
            else if (i == 1) {
                meta.setDisplayName("TheBlackSwordsman");
                stack.setAmount(13);
            }

            stack.setItemMeta(meta);
            armor[i] = stack;
        }

        player.getInventory().setArmorContents(armor);
        player.sendMessage("Inventory Bomb!!!!");
    }
}
