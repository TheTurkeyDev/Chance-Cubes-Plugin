package chanceCubes.rewards.biodomeGen;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class RandomBioDome implements IBioDomeBiome {

    private ItemStack nmsBlockToBukkitStack(Block block) {
        return CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_10_R1.ItemStack(block));
    }

    public List<OffsetBlock> addBlockStack(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        ItemStack itemStack = nmsBlockToBukkitStack(RewardsUtil.getRandomBlock());
        for (int xx = -1; xx < 2; xx++) {
            for (int zz = -1; zz < 2; zz++) {
                for (int yy = 0; yy < 2; yy++) {
                    if ((yy == 1 && (xx == 0 || zz == 0)) || (yy == 0)) {
                        blocks.add(new OffsetBlock(x + xx, y + 1 + yy, z + zz, itemStack.getType(), itemStack.getData(), false, delay));
                        delay++;
                    }
                }
            }
        }

        blocks.add(new OffsetBlock(x, y + 3, z, itemStack.getType(), itemStack.getData(), false, delay));
        return blocks;
    }

    public List<OffsetBlock> addOreStack(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        for (int xx = -1; xx < 2; xx++) {
            for (int zz = -1; zz < 2; zz++) {
                for (int yy = 0; yy < 2; yy++) {
                    if ((yy == 1 && (xx == 0 || zz == 0)) || (yy == 0)) {
                        ItemStack itemStack = new ItemStack(RewardsUtil.getRandomOre());
                        OffsetBlock osb = new OffsetBlock(x + xx, y + 1 + yy, z + zz, itemStack.getType(), itemStack.getData(), false, delay);
                        blocks.add(osb);
                        delay++;
                    }
                }
            }
        }

        ItemStack itemStack = new ItemStack(RewardsUtil.getRandomOre());
        OffsetBlock osb = new OffsetBlock(x, y + 3, z, itemStack.getType(), itemStack.getData(), false, delay);
        blocks.add(osb);
        return blocks;
    }

    public List<OffsetBlock> addPillar(int x, int y, int z, int delay) {
        List<OffsetBlock> blocks = new ArrayList<>();

        ItemStack itemStack = nmsBlockToBukkitStack(RewardsUtil.getRandomBlock());
        for (int yy = 0; yy < 5; yy++) {
            blocks.add(new OffsetBlock(x, y + yy, z, itemStack.getType(), itemStack.getData(), false, delay));
            delay++;
        }

        return blocks;
    }

    @Override
    public Material getFloorBlock() {
        return Material.OBSIDIAN;
    }

    @Override
    public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay) {
        if (y == 0) {
            if (dist < -3 && rand.nextInt(100) == 0) {
                switch (rand.nextInt(3)) {
                    case 0:
                        blocks.addAll(addBlockStack(x, y, z, (delay / BioDomeReward.delayShorten)));
                        break;
                    case 1:
                        blocks.addAll(addPillar(x, y, z, (delay / BioDomeReward.delayShorten)));
                        break;
                    case 2:
                        blocks.addAll(addOreStack(x, y, z, (delay / BioDomeReward.delayShorten)));
                        break;
                }
            }
        }
    }

    @Override
    public void spawnEntities(Location location) {

    }
}
