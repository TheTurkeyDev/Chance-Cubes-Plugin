package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.Block;
import org.bukkit.Location;

public interface IBioDomeBiome {

    Block getFloorBlock();

    void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay);

    void spawnEntities(Location location);
}