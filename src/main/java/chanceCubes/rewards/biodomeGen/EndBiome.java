package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.Blocks;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class EndBiome implements IBioDomeBiome {

    private Random rand = new Random();

    public List<OffsetBlock> addTower(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        for (int yy = 0; yy < 10; yy++) {
            for (int xx = -1; xx < 2; xx++) {
                for (int zz = -1; zz < 2; zz++) {
                    blocks.add(new OffsetBlock(x + xx, y + yy, z + zz, Blocks.OBSIDIAN, false, delay));
                    delay++;
                }
            }
        }
        blocks.add(new OffsetBlock(x, y + 10, z, Blocks.BEDROCK, false, delay));
        return blocks;
    }

    @Override
    public Block getFloorBlock() {
        return Blocks.END_STONE;
    }

    @Override
    public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay) {
        if (y != 0)
            return;
        if (dist < -5 && rand.nextInt(200) == 0) {
            List<OffsetBlock> treeblocks = this.addTower(x, y, z, (delay / BioDomeReward.delayShorten));
            blocks.addAll(treeblocks);
        }
    }

    @Override
    public void spawnEntities(Location location) {
        for (int i = 0; i < rand.nextInt(10) + 5; i++) {
            location.getWorld().spawnEntity(location.clone().add(rand.nextInt(31) - 15, 1, rand.nextInt(31) - 15), EntityType.ENDERMAN);
        }
    }
}