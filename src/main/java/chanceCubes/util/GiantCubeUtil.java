package chanceCubes.util;

import chanceCubes.CCubesCore;
import chanceCubes.items.CCubesItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.metadata.FixedMetadataValue;

public class GiantCubeUtil {

    /**
     * Check that structure is properly formed
     *
     * @param location
     * @param build  if the giant cube should be built if the structure is valid
     * @return if there is a valid 3x3x3 configuration
     */
    public static boolean checkMultiBlockForm(Location location, boolean build) {
        Location bottomLeft = findBottomCorner(location);
        int cx = bottomLeft.getBlockX();
        int cy = bottomLeft.getBlockY();
        int cz = bottomLeft.getBlockZ();
        int i = 0;
        // Scan a 3x3x3 area, starting with the bottom left corner
        for (int x = cx; x < cx + 3; x++)
            for (int y = cy; y < cy + 3; y++)
                for (int z = cz; z < cz + 3; z++)
                    if (CCubesItems.isChanceCube(new Location(location.getWorld(), x, y, z).getBlock()))
                        i++;
        // check if there are 27 blocks present (3*3*3) and if a giant cube should be built
        if (build) {
            if (i > 26) {
                setupStructure(new Location(location.getWorld(), cx, cy, cz), true);
                return true;
            }
            return false;
        }
        else {
            return i > 26;
        }
    }

    public static Location findBottomCorner(Location location) {
        int cx = location.getBlockX();
        int cy = location.getBlockY();
        int cz = location.getBlockZ();
        Location tempLoc = location.clone().subtract(0, 1, 0);
        while (CCubesItems.isChanceCube(tempLoc.getBlock())) {
            tempLoc.subtract(0, 1, 0);
            cy--;
        }

        tempLoc.subtract(1, 0, 0);
        while (CCubesItems.isChanceCube(tempLoc.getBlock())) {
            tempLoc.subtract(1, 0, 0);
            cx--;
        }

        tempLoc.subtract(0, 0, 1);
        while (CCubesItems.isChanceCube(tempLoc.getBlock())) {
            tempLoc.subtract(0, 0, 1);
            cz--;
        }

        return new Location(location.getWorld(), cx, cy, cz);
    }

    /**
     * Reset all the parts of the structure
     */
    public static void removeStructure(Location location) {
        for (int x = location.getBlockX() - 1; x < location.getBlockX() + 2; x++)
            for(int y = location.getBlockY() - 1; y < location.getBlockY() + 2; y++)
                for(int z = location.getBlockZ() - 1; z < location.getBlockZ() + 2; z++)
                    new Location(location.getWorld(), x, y, z).getBlock().setType(Material.AIR);
    }

    /**
     * Reset all the parts of the structure
     */
    public static void resetStructure(Location location) {
        for (int x = location.getBlockX() - 1; x < location.getBlockX() + 2; x++)
            for (int y = location.getBlockY() - 1; y < location.getBlockY() + 2; y++)
                for (int z = location.getBlockZ() - 1; z < location.getBlockZ() + 2; z++)
                    new Location(location.getWorld(), x, y, z).getBlock().setType(Material.AIR);
    }

    /**
     * Setup all the blocks in the structure
     */
    public static void setupStructure(Location location, boolean areCoordsCorrect) {
        int cx = location.getBlockX();
        int cy = location.getBlockY();
        int cz = location.getBlockZ();

        if (!areCoordsCorrect) {
            Location bottomLeft = findBottomCorner(location);
            cx = bottomLeft.getBlockX();
            cy = bottomLeft.getBlockY();
            cz = bottomLeft.getBlockZ();
        }

        for (int x = cx; x < cx + 3; x++) {
            for (int z = cz; z < cz + 3; z++) {
                for (int y = cy; y < cy + 3; y++) {
                    Location loc = new Location(location.getWorld(), x, y, z);
                    RewardsUtil.placeBlock(CCubesItems.giantChanceCube.getType(), loc);
                    loc.getBlock().setMetadata("ChanceCubes", new FixedMetadataValue(CCubesCore.instance(), "\\_o<"));
                }
            }
        }

        //TODO no custom sounds available yet
        //world.playSound(null, location.getBlockX(), location.getBlockY(), location.getBlockZ(), CCubesSounds.GIANT_CUBE_SPAWN.getSoundEvent(), CCubesSounds.GIANT_CUBE_SPAWN.getSoundCategory(), 1.0F, 1.0F);
    }
}