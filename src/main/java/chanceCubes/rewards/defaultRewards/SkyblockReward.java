package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Chest;
import org.bukkit.material.MaterialData;

public class SkyblockReward implements IChanceCubeReward {

    ItemStack[] chestStuff = {
            new ItemStack(Material.STRING, 12), new ItemStack(Material.LAVA_BUCKET), new ItemStack(Material.BONE), new ItemStack(Material.SUGAR_CANE),
            new ItemStack(Material.RED_MUSHROOM), new ItemStack(Material.ICE, 2), new ItemStack(Material.PUMPKIN_SEEDS), new ItemStack(Material.SAPLING),
            new ItemStack(Material.BROWN_MUSHROOM), new ItemStack(Material.MELON), new ItemStack(Material.CACTUS), new ItemStack(Material.LOG, 6)
    };

    @Override
    public int getChanceValue() {
        return 10;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":SkyBlock";
    }

    @Override
    public void trigger(Location location, Player player) {
        World world = location.getWorld();
        int skyblockHeight =  world.getMaxHeight() - 16;
        //TODO need to check if this is needed or not
        /*if (world.provider.getHasNoSky())
            skyblockHeight = pos.getY();*/

        Material m = Material.DIRT;
        Location skyblockPos = new Location(location.getWorld(), location.getX(), skyblockHeight, location.getZ());
        for (int i = 0; i < 3; i++) {
            if (i == 2)
                m = Material.GRASS;

            for (int c = 0; c < 3; c++) {
                int xOffset = c == 0 ? -1 : 2;
                int zOffset = c == 2 ? 2 : -1;
                for (int xx = 0; xx < 3; xx++)
                    for (int zz = 0; zz < 3; zz++)
                        skyblockPos.clone().add(xOffset + xx, i, zOffset + zz).getBlock().setType(m);
            }
        }

        RewardsUtil.placeBlock(Material.BEDROCK, new MaterialData(Material.BEDROCK), skyblockPos.clone().add(0, 1, 0));
        world.generateTree(location.clone().add(4, 3, 4), TreeType.TREE);
        Location chestLoc = location.clone().add(-1, 3, 0);
        RewardsUtil.placeBlock(Material.CHEST, new Chest(BlockFace.WEST), chestLoc);
        org.bukkit.block.Chest chest = (org.bukkit.block.Chest) chestLoc.getBlock().getState();
        for (int i = 0; i < chestStuff.length; i++) {
            int slot = ((i < 4 ? 0 : i < 8 ? 1 : 2) * 9) + i % 4;
            chest.getInventory().setItem(slot, chestStuff[i].clone());
        }

        player.teleport(new Location(location.getWorld(), location.getX(), skyblockHeight + 3, location.getZ()));
    }
}
