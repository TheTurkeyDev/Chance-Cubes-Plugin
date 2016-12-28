package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.items.CCubesItems;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearInventoryReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -100;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Clear_Inventory";
    }

    @Override
    public void trigger(Location location, final Player player) {
        boolean cubes = false;
        final ItemStack[] inv = player.getInventory().getContents().clone();
        for (int slotNum = 0; slotNum < player.getInventory().getSize(); slotNum++) {
            ItemStack itemStack = player.getInventory().getItem(slotNum);
            if (itemStack != null && CCubesItems.isGenericChanceCube(itemStack))
                cubes = true;
            else
                player.getInventory().setItem(slotNum, null);
        }

        player.playSound(location, Sound.ENTITY_PLAYER_BURP, 1F, 1F);
        player.sendMessage("I hope you didn't have anything of value with you :)");
        if (cubes)
            player.sendMessage("Don't worry, I left the cubes for you!");

        if (new Random().nextInt(5) == 1)
            RewardsUtil.scheduleTask(() -> {
                player.getInventory().setContents(inv);
                player.sendMessage("AHHHHHH JK!! You should have seen your face!");
            }, 200);
    }

}
