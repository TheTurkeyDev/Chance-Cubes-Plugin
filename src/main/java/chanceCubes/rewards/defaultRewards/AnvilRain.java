package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AnvilRain implements IChanceCubeReward {

    private Random rand = new Random();

    @Override
    public int getChanceValue() {
        return -45;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":AnvilRain";
    }

    @Override
    public void trigger(Location location, Player player) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        int x1 = x + (rand.nextInt(9) - 4);
        int z1 = z + (rand.nextInt(9) - 4);

        int x2 = x + (rand.nextInt(9) - 4);
        int z2 = z + (rand.nextInt(9) - 4);

        int x3 = x + (rand.nextInt(9) - 4);
        int z3 = z + (rand.nextInt(9) - 4);

        int x4 = x + (rand.nextInt(9) - 4);
        int z4 = z + (rand.nextInt(9) - 4);

        int x5 = x + (rand.nextInt(9) - 4);
        int z5 = z + (rand.nextInt(9) - 4);

        int yy = 0;
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), x, y + yy, z));
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), x1, y + yy, z1));
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), x2, y + yy, z2));
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), x3, y + yy, z3));
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), x4, y + yy, z4));
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), x5, y + yy, z5));
        for (yy = 0; yy < 25; yy++)
            RewardsUtil.placeBlock(Material.AIR, new Location(location.getWorld(), player.getLocation().getBlockX(), y + yy, player.getLocation().getBlockZ()));

        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), x, y + 25, z));
        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), x1, y + 25, z1));
        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), x2, y + 25, z2));
        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), x3, y + 25, z3));
        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), x4, y + 25, z4));
        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), x5, y + 25, z5));
        RewardsUtil.placeBlock(Material.ANVIL, new Location(location.getWorld(), player.getLocation().getBlockX(), y + 25, player.getLocation().getBlockZ()));

        for (int xx = 0; xx < 2; xx++) {
            int xxx = xx == 1 ? x + 5 : x - 5;
            for (int zz = -5; zz < 6; zz++) {
                for (int yyy = 0; yyy < 3; yyy++) {
                    RewardsUtil.placeBlock(Material.COBBLESTONE, new Location(location.getWorld(), xxx, yyy + y, zz + z));
                }
            }
        }

        for (int xx = -5; xx < 6; xx++) {
            for (int zz = 0; zz < 2; zz++) {
                int zzz = zz == 1 ? z + 5 : z - 5;
                for (int yyy = 0; yyy < 3; yyy++) {
                    RewardsUtil.placeBlock(Material.COBBLESTONE, new Location(location.getWorld(), xx + x, yyy + y, zzz));
                }
            }
        }
    }
}