package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FluidTowerReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 15;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Fluid_Tower";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int i = 0; i < 25; i++)
            RewardsUtil.placeBlock(RewardsUtil.getRandomFluid(), location.clone().add(0, i, 0));
    }
}
