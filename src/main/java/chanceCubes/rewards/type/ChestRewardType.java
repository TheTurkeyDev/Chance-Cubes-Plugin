package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.ChestChanceItem;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class ChestRewardType extends BaseRewardType<ChestChanceItem> {

    private Chest chest;
    private int delay = 0;

    public ChestRewardType(ChestChanceItem... items) {
        super(items);
    }

    public ChestRewardType setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    @Override
    protected void trigger(ChestChanceItem item, Location location, Player player) {
        boolean addToChest = new Random().nextInt(100) < item.getChance();
        if (addToChest) {
            int slot = new Random().nextInt(chest.getInventory().getSize());
            chest.getInventory().setItem(slot, item.getRandomItemStack());
        }
    }

    @Override
    public void trigger(Location location, final Player player) {
        if (delay != 0) {
            RewardsUtil.scheduleTask(() -> spawn(location, player), delay);
        }
        else {
            spawn(location, player);
        }
    }

    private void spawn(Location location, Player player) {
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        chest = (Chest) block;

        for (ChestChanceItem item : rewards)
            trigger(item, location, player);
    }
}
