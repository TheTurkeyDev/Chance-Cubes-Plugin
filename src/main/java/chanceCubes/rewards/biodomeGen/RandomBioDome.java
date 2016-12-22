package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.Blocks;
import org.bukkit.Location;

public class RandomBioDome implements IBioDomeBiome {

    public List<OffsetBlock> addBlockStack(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        Block b = RewardsUtil.getRandomBlock();
        for (int xx = -1; xx < 2; xx++) {
            for (int zz = -1; zz < 2; zz++) {
                for (int yy = 0; yy < 2; yy++) {
                    if ((yy == 1 && (xx == 0 || zz == 0)) || (yy == 0)) {
                        blocks.add(new OffsetBlock(x + xx, y + 1 + yy, z + zz, b, false, delay));
                        delay++;
                    }
                }
            }
        }

        blocks.add(new OffsetBlock(x, y + 3, z, b, false, delay));

        return blocks;
    }

    public List<OffsetBlock> addOreStack(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        for (int xx = -1; xx < 2; xx++) {
            for (int zz = -1; zz < 2; zz++) {
                for (int yy = 0; yy < 2; yy++) {
                    if ((yy == 1 && (xx == 0 || zz == 0)) || (yy == 0)) {
                        SimpleEntry<Block, Integer> ore = RewardsUtil.getRandomOre();
                        OffsetBlock osb = new OffsetBlock(x + xx, y + 1 + yy, z + zz, ore.getKey(), false, delay);
                        osb.setBlockState(ore.getKey().fromLegacyData(ore.getValue()));
                        blocks.add(osb);
                        delay++;
                    }
                }
            }
        }

        SimpleEntry<Block, Integer> ore = RewardsUtil.getRandomOre();
        OffsetBlock osb = new OffsetBlock(x, y + 3, z, ore.getKey(), false, delay);
        osb.setBlockState(ore.getKey().fromLegacyData(ore.getValue()));
        blocks.add(osb);

        return blocks;
    }

    public List<OffsetBlock> addPillar(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        Block b = RewardsUtil.getRandomBlock();
        for (int yy = 0; yy < 5; yy++) {
            blocks.add(new OffsetBlock(x, y + yy, z, b, false, delay));
            delay++;
        }

        return blocks;
    }

    @Override
    public Block getFloorBlock() {
        return Blocks.OBSIDIAN;
    }

    @Override
    public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay) {
        if (y == 0) {
            if (dist < -3 && rand.nextInt(100) == 0) {
                switch (rand.nextInt(3)) {
                    case 0:
                        blocks.addAll(this.addBlockStack(x, y, z, (delay / BioDomeReward.delayShorten)));
                        break;
                    case 1:
                        blocks.addAll(this.addPillar(x, y, z, (delay / BioDomeReward.delayShorten)));
                        break;
                    case 2:
                        blocks.addAll(this.addOreStack(x, y, z, (delay / BioDomeReward.delayShorten)));
                        break;
                }
            }
        }
    }

    @Override
    public void spawnEntities(Location location) {

    }

}
