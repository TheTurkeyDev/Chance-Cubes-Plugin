package chanceCubes.rewards.biodomeGen;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SnowGlobeBiome implements IBioDomeBiome {

    private Random rand = new Random();

    @Override
    public Material getFloorBlock() {
        return Material.SNOW;
    }

    @Override
    public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay) {
        if (y != 0)
            return;
        if (dist < 0 && rand.nextInt(5) == 0) {
            OffsetBlock osb = new OffsetBlock(x, y + 1, z, Material.SNOW, false, (delay / BioDomeReward.delayShorten));
            blocks.add(osb);
        }
    }

    @Override
    public void spawnEntities(final Location location) {
        for (int i = 0; i < rand.nextInt(10) + 5; i++)
            location.getWorld().spawnEntity(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), EntityType.SNOWMAN);

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> {
            for (int i = 0; i < 100; i++) {
                int ri = rand.nextInt(2);
                if (ri == 0)
                    location.getWorld().spawnEntity(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), EntityType.SNOWMAN);
                else
                    location.getWorld().spawnEntity(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), EntityType.POLAR_BEAR);
            }
        }, 20);
    }
}
