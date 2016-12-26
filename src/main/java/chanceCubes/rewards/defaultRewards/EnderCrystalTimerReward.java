package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class EnderCrystalTimerReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -90;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Ender_Crystal_Timer";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int i = 30; i > 0; i--)
            RewardsUtil.placeBlock(Material.AIR, new MaterialData(Material.AIR), location.clone().add(0, i, 0));

        location.getWorld().spawnEntity(location.clone().add(0.5, 0, 0.5), EntityType.ENDER_CRYSTAL);
        location.getWorld().spawnArrow(location.clone().add(0.5, 29, 0.5), new Vector(0, -0.25f, 0), 0, 0);
    }
}