package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RottenFoodReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -30;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Rotten_Food";
    }

    @Override
    public void trigger(Location location, Player player) {
        PlayerInventory inv = player.getInventory();
        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (stack != null && stack.getType().isEdible()) {
                stack.setType(Material.ROTTEN_FLESH);
                inv.setItem(i, stack);
            }
        }

        player.sendMessage("Ewwww it's all rotten");
    }
}