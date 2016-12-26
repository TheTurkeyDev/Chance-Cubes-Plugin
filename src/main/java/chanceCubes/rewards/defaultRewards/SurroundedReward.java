package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SurroundedReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -45;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Surrounded";
    }

    @Override
    public void trigger(Location location, Player player) {
        int px = player.getLocation().getBlockX();
        int pz = player.getLocation().getBlockZ();
        for (int xx = 0; xx < 2; xx++) {
            for (int zz = -4; zz < 5; zz++) {
                Material material = new Location(location.getWorld(), px + xx, location.getBlockY(), pz + zz).getBlock().getType();
                Material material2 = new Location(location.getWorld(), px + xx, location.getBlockY(), pz + zz).getBlock().getType();
                Material material3 = new Location(location.getWorld(), px + xx, location.getBlockY(), pz + zz).getBlock().getType();
                if (!material.isSolid() && !material2.isSolid() && !material3.isSolid()) {
                    Location tempLoc = location.clone().add(xx == 1 ? 4 : -4, 0, zz);
                    tempLoc.setPitch(xx == 1 ? 90 : -90);
                    location.getWorld().spawnEntity(tempLoc, EntityType.ENDERMAN);
                }
            }
        }

        for (int xx = -4; xx < 5; xx++) {
            for (int zz = 0; zz < 2; zz++) {
                if (location.clone().add(xx, 0, zz == 1 ? 4 : -4).getBlock().getType() == Material.AIR && location.clone().add(xx, 1, zz == 1 ? 4 : -4).getBlock().getType() == Material.AIR && location.clone().add(xx, 2, zz == 1 ? 4 : -4).getBlock().getType() == Material.AIR) {
                    Location tempLoc = location.clone().add(xx, 0, zz == 1 ? 4 : -4);
                    tempLoc.setPitch(zz == 1 ? 180 : 0);
                    location.getWorld().spawnEntity(tempLoc, EntityType.ENDERMAN);
                }
            }
        }
    }

}
