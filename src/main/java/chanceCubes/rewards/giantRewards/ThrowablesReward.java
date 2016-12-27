package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import net.minecraft.server.v1_10_R1.EntityLargeFireball;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftLargeFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class ThrowablesReward implements IChanceCubeReward {

    private Random random = new Random();

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Throwables";
    }

    public void throwThing(final int count, final Location location) {
        World world = location.getWorld();
        int entChoice = random.nextInt(5);
        if (entChoice == 0)
            world.spawnArrow(location, new Vector(-1 + (Math.random() * 2), -1 + (Math.random() * 2), -1 + (Math.random() * 2)), 0.6f, 12f);
        else if (entChoice == 1) {
            EntityLargeFireball nmsFireball = setEntityMotion((CraftLargeFireball) world.spawnEntity(location, EntityType.FIREBALL)).getHandle();
            nmsFireball.dirX = 0.1f * (-1 + (Math.random() * 2));
            nmsFireball.dirY = 0.1f * (-1 + (Math.random() * 2));
            nmsFireball.dirZ = 0.1f * (-1 + (Math.random() * 2));
        }
        else if (entChoice == 2)
            setEntityMotion(world.spawnEntity(location, EntityType.EGG));
        else if (entChoice == 3)
            setEntityMotion(world.spawnEntity(location, EntityType.WITHER_SKULL));
        else {
            TNTPrimed tnt = setEntityMotion((TNTPrimed) world.spawnEntity(location, EntityType.PRIMED_TNT));
            tnt.setFuseTicks(20);
        }

        if (count < 50)
            RewardsUtil.scheduleTask(() -> throwThing(count + 1, location), 5);
    }

    private <E extends Entity> E setEntityMotion(E entity) {
        entity.setVelocity(new Vector(-1 + (Math.random() * 2), -1 + (Math.random() * 2), -1 + (Math.random() * 2)));
        return entity;
    }

    @Override
    public void trigger(Location location, Player player) {
        throwThing(0, location);
    }

}
