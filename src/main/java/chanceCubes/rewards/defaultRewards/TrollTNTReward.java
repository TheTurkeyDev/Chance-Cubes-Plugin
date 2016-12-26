package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.material.MaterialData;

public class TrollTNTReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -5;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":TrollTNT";
    }

    private void timeUp(Entity ent, Player player) {
        player.sendMessage("BOOM");
        ent.remove();
    }

    @Override
    public void trigger(Location location, final Player player) {
        for (int x = -1; x < 2; x++)
            for (int z = -1; z < 2; z++)
                RewardsUtil.placeBlock(Material.WEB, new MaterialData(Material.WEB), location.clone().add(x, 0, z));

        TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location.clone().add(1, 1, 0), EntityType.PRIMED_TNT);
        player.playSound(player.getLocation(), Sound.ENTITY_TNT_PRIMED, 1F, 1F);
        if (new Random().nextInt(5) != 1)
            Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> timeUp(tnt, player), 80);
    }
}
