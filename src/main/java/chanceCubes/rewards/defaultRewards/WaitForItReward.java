package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class WaitForItReward implements IChanceCubeReward {

    private final Random rand = new Random();

    @Override
    public int getChanceValue() {
        return -30;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Wait_For_It";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        player.sendMessage("Wait for it.......");
        RewardsUtil.scheduleTask(() -> {
            int reward = rand.nextInt(3);
            player.sendMessage("NOW!");
            if (reward == 0)
                location.getWorld().spawnEntity(player.getLocation(), EntityType.PRIMED_TNT);
            else if (reward == 1)
                location.getWorld().spawnEntity(player.getLocation(), EntityType.CREEPER);
            else if (reward == 2)
                player.getLocation().getBlock().setType(Material.BEDROCK);
        }, rand.nextInt(4000) + 1000);
    }

}
