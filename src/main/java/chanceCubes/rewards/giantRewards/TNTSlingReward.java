package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class TNTSlingReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":TNT_Throw";
    }

    public void throwTNT(final int count, final Location location) {
        TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location.clone().add(0, 1, 0), EntityType.PRIMED_TNT);
        tnt.setFuseTicks(60);
        tnt.setVelocity(new Vector(-1 + (Math.random() * 2), Math.random(), -1 + (Math.random() * 2)));

        if (count < 25)
            RewardsUtil.scheduleTask(() -> throwTNT(count + 1, location), 10);
        else {
            for (double xx = 1; xx > -1; xx -= 0.25) {
                for (double zz = 1; zz > -1; zz -= 0.25) {
                    tnt = (TNTPrimed) location.getWorld().spawnEntity(location.clone().add(0, 1, 0), EntityType.PRIMED_TNT);
                    tnt.setFuseTicks(60);
                    tnt.setVelocity(new Vector(xx, Math.random(), zz));
                }
            }
        }
    }

    @Override
    public void trigger(Location location, Player player) {
        throwTNT(0, location);
    }
}
