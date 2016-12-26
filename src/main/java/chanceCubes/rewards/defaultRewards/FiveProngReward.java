package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.items.CCubesItems;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class FiveProngReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -10;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":5_Prongs";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int xx = location.getBlockX() - 3; xx <= location.getBlockX() + 3; xx++)
            for (int zz = location.getBlockZ() - 3; zz <= location.getBlockZ() + 3; zz++)
                for (int yy = location.getBlockY(); yy <= location.getBlockY() + 4; yy++)
                    RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), xx, yy, zz));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location);
        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(0, 1, 0));
        RewardsUtil.placeBlock(CCubesItems.chanceIcosahedron.getType(), location.clone().add(0, 2, 0));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(-3, 0, -3));
        RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), location.clone().add(-3, 1, -3));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(-3, 0, 3));
        RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), location.clone().add(-3, 1, 3));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(3, 0, -3));
        RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), location.clone().add(3, 1, -3));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(3, 0, 3));
        RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), location.clone().add(3, 1, 3));
    }
}
