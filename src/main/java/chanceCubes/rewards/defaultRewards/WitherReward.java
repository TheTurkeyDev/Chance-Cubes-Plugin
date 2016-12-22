package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;

public class WitherReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -100;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Wither";
    }

    private boolean removeEnts(Entity ent) {
        if (new Random().nextInt(10) != 1) {
            ent.remove();
            return true;
        }

        return false;
    }

    @Override
    public void trigger(Location location, Player player) {
        Location witherLoc = location.clone().add(0.5, 1, 1.5);
        witherLoc.setPitch(90F);
        Wither wither = (Wither) location.getWorld().spawnEntity(witherLoc, EntityType.WITHER);
        if (new Random().nextBoolean())
            wither.setCustomName("Kiwi");
        else
            wither.setCustomName("Kehaan");

        wither.setCustomNameVisible(true);

        RewardsUtil.sendMessageToNearPlayers(location, 32, "\"You've got to ask yourself one question: 'Do I feel lucky?' Well, do ya, punk?\"");

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> {
            removeEnts(wither);
            //TODO need to work on custom Achievements
            /*if (!removeEnts(wither))
                player.awardAchievement(CCubesAchievements.wither);*/
        }, 180);
    }

}
