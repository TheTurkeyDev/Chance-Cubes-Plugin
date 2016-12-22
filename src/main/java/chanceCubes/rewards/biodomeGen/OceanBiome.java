package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.Blocks;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class OceanBiome implements IBioDomeBiome {

    private Random rand = new Random();

    @Override
    public Block getFloorBlock() {
        return Blocks.CLAY;
    }

    @Override
    public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay) {
        if (y == 0 || dist >= 0)
            return;
        blocks.add(new OffsetBlock(x, y, z, Blocks.WATER, false, delay / BioDomeReward.delayShorten));
    }

    @Override
    public void spawnEntities(Location location) {
        for (int i = 0; i < rand.nextInt(10) + 5; i++) {
            Entity entity = location.getWorld().spawnEntity(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), EntityType.SQUID);
            entity.setCustomName("Mango");
            entity.setCustomNameVisible(true);
        }
    }
}