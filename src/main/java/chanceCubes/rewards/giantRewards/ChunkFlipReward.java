package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class ChunkFlipReward implements IChanceCubeReward {

    public ChunkFlipReward() {

    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Chunk_Flip";
    }

    public void moveLayer(final World world, final int x, int y, final int z) {
        if (y >= world.getMaxHeight() / 2)
            return;

        for (int zz = 0; zz < 16; zz++) {
            for (int xx = 0; xx < 16; xx++) {
                Location pos1 = new Location(world, x + xx, y, z + zz);
                Location pos2 = new Location(world, x + xx, world.getMaxHeight() - y, z + zz);
                BlockState b = pos1.getBlock().getState();
                BlockState b2 = pos2.getBlock().getState();
                if (b.getBlock().getType() != Material.GRAVEL && !CCubesBlocks.isChanceCube(b.getBlock())) {
                    Material material2 = b.getType();
                    MaterialData materialData1 = b.getData();
                    Material material1 = b2.getType();
                    MaterialData materialData2 = b2.getData();
                    b.setType(material2);
                    b.setData(materialData2);
                    b.update(true);
                    b2.setType(material1);
                    b2.setData(materialData1);
                    b2.update(true);
                }
            }
        }

        RewardsUtil.scheduleTask(() -> moveLayer(world, x, y + 1, z), 10);
    }

    @Override
    public void trigger(Location location, Player player) {
        int zBase = location.getBlockZ() - (location.getBlockZ() % 16);
        int xBase = location.getBlockX() - (location.getBlockX() % 16);

        moveLayer(location.getWorld(), xBase, 0, zBase);
        player.playSound(location, Sound.BLOCK_ANVIL_PLACE, 1f, 1f);
        player.sendMessage("Inception!!!!");
    }

}
