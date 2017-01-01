package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.items.CCubesItems;
import chanceCubes.tileentities.ChanceCubeData;
import chanceCubes.tileentities.ChanceD20Data;
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
        Location d20Loc = location.clone().add(0, 2, 0);
        if (RewardsUtil.placeBlock(CCubesItems.chanceIcosahedron.getType(), d20Loc))
            CCubesBlocks.addChanceD20(new ChanceD20Data(d20Loc));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(-3, 0, -3));
        Location cubeLoc1 = location.clone().add(-3, 1, -3);
        if (RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), cubeLoc1))
            CCubesBlocks.addChanceCube(new ChanceCubeData(cubeLoc1));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(-3, 0, 3));
        Location cubeLoc2 = location.clone().add(-3, 1, 3);
        if (RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), cubeLoc2))
            CCubesBlocks.addChanceCube(new ChanceCubeData(cubeLoc2));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(3, 0, -3));
        Location cubeLoc3 = location.clone().add(3, 1, -3);
        if (RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), cubeLoc3))
            CCubesBlocks.addChanceCube(new ChanceCubeData(cubeLoc3));

        RewardsUtil.placeBlock(Material.QUARTZ_BLOCK, location.clone().add(3, 0, 3));
        Location cubeLoc4 = location.clone().add(3, 1, 3);
        if (RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), cubeLoc4))
            CCubesBlocks.addChanceCube(new ChanceCubeData(cubeLoc4));
    }
}
