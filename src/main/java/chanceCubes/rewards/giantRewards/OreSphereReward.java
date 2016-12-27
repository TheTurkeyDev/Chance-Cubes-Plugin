package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OreSphereReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Ore_Sphere";
    }

    @Override
    public void trigger(Location location, Player player) {
        List<OffsetBlock> blocks = new ArrayList<>();

        Material ore = RewardsUtil.getRandomOre();
        int delay = 0;
        for (int i = 0; i < 5; i++) {
            for (int yy = -5; yy < 6; yy++) {
                for (int zz = -5; zz < 6; zz++) {
                    for (int xx = -5; xx < 6; xx++) {
                        Location loc = new Location(location.getWorld(), xx, yy, zz);
                        double dist = Math.abs(loc.distance(new Location(loc.getWorld(), 0, 0, 0)));
                        if (dist <= i && dist > i - 1) {
                            blocks.add(new OffsetBlock(xx, yy, zz, ore, false, delay));
                            delay++;
                        }
                    }
                }
            }
            delay += 10;
        }

        for (OffsetBlock b : blocks)
            b.spawnInWorld(location);
    }
}
