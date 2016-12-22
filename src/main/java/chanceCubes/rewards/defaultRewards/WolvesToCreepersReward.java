package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class WolvesToCreepersReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -20;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Wolves_To_Creepers";
    }

    @Override
    public void trigger(final Location location, Player player) {
        final List<Entity> wolves = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int yy = 0; yy < 4; yy++)
                for (int xx = -1; xx < 2; xx++)
                    for (int zz = -1; zz < 2; zz++)
                        RewardsUtil.placeBlock(Material.AIR, location.clone().add(xx, yy, zz));

            Wolf wolf = (Wolf) location.getWorld().spawnEntity(location, EntityType.WOLF);
            wolf.setTamed(true);
            wolf.setOwner(player);
            wolf.setCustomName("Kehaan");
            wolf.setCustomNameVisible(true);
            wolves.add(wolf);
        }

        RewardsUtil.sendMessageToNearPlayers(location, 32, "Do they look weird to you?");

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> {
            for (Entity wolf : wolves) {
                wolf.remove();
                Creeper creeper = (Creeper) location.getWorld().spawnEntity(wolf.getLocation(), EntityType.CREEPER);
                creeper.setCustomName("Jacky");
                creeper.setCustomNameVisible(true);
            }
        }, 200);
    }
}
