package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ThrownInAirReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -35;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Thrown_In_Air";
    }

    @Override
    public void trigger(Location location, final Player player) {
        Location playerLoc = player.getLocation();
        int px = playerLoc.getBlockX();
        int py = playerLoc.getBlockY() + 1;
        int pz = playerLoc.getBlockZ();

        for (int y = 0; y < 40; y++)
            for (int x = -1; x < 2; x++)
                for (int z = -1; z < 2; z++)
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(px + x, py + y, pz + z));

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> player.setVelocity(new Vector(0, 20, 0)));
    }
}
