package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NukeReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -75;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Nuke";
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.sendMessageToNearPlayers(location, 32, "May death rain upon them");
        World world = location.getWorld();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        world.spawnEntity(new Location(world, x - 6, y + 65, z - 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 2, y + 65, z - 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 2, y + 65, z - 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 6, y + 65, z - 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 6, y + 65, z - 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 2, y + 65, z - 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 2, y + 65, z - 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 6, y + 65, z - 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 6, y + 65, z + 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 2, y + 65, z + 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 2, y + 65, z + 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 6, y + 65, z + 2), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 6, y + 65, z + 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x - 2, y + 65, z + 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 2, y + 65, z + 6), EntityType.PRIMED_TNT);
        world.spawnEntity(new Location(world, x + 6, y + 65, z + 6), EntityType.PRIMED_TNT);
    }

}
