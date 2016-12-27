package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OrePillarReward implements IChanceCubeReward {

    private Random rand = new Random();

    public OrePillarReward() {

    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Ore_Pillars";
    }

    @Override
    public void trigger(Location location, Player player) {
        List<OffsetBlock> blocks = new ArrayList<>();
        int delay = 0;
        for (int i = 0; i < rand.nextInt(4) + 2; i++) {
            int xx = rand.nextInt(30) - 15;
            int zz = rand.nextInt(30) - 15;
            for (int yy = 1; yy < 255; yy++) {
                Material ore = RewardsUtil.getRandomOre();
                OffsetBlock osb = new OffsetBlock(xx, yy - location.getBlockY(), zz, ore, false, delay / 3);
                blocks.add(osb);
                delay++;
            }
        }

        for (OffsetBlock b : blocks)
            b.spawnInWorld(location);
    }

}
