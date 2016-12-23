package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryChestReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -70;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Inventory_Chest";
    }

    @Override
    public void trigger(Location location, final Player player) {
        final List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : player.getInventory())
            if (stack != null)
                stacks.add(stack);

        //This does not clear armor contents
        player.getInventory().clear();
        player.sendMessage("At least i didn't delete your items...");
        RewardsUtil.placeBlock(Material.CHEST, location);
        RewardsUtil.placeBlock(Material.CHEST, location.clone().add(1, 0, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(0, -1, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(1, -1, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(-1, 0, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(2, 0, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(0, 0, 1));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(1, 0, 1));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(0, 0, -1));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(1, 0, -1));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(0, -1, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(1, -1, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(0, 1, 0));
        RewardsUtil.placeBlock(Material.OBSIDIAN, location.clone().add(1, 1, 0));

        DoubleChest chest = (DoubleChest) location.getBlock();
        for (int i = 0; i < stacks.size(); i++) {
            if (i > chest.getInventory().getSize() * 2)
                return;

            chest.getInventory().addItem(stacks.toArray(new ItemStack[0]));
        }
    }
}
