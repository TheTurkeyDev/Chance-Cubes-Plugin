package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RandomTeleportReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -15;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Random_Teleport";
    }

    @Override
    public void trigger(Location location, Player player) {
        int xChange = ((new Random().nextInt(50) + 20) + location.getBlockX()) - 35;
        int zChange = ((new Random().nextInt(50) + 20) + location.getBlockZ()) - 35;

        int yChange = -1;
        World world = location.getWorld();
        for (int yy = 0; yy <= world.getMaxHeight(); yy++) {
            if (new Location(world, xChange, yy, zChange).getBlock().getType() == Material.AIR && new Location(world, xChange, yy + 1, zChange).getBlock().getType() == Material.AIR) {
                yChange = yy;
                break;
            }
        }

        if (yChange == -1)
            return;

        player.teleport(new Location(location.getWorld(), xChange, yChange, zChange));
    }
}