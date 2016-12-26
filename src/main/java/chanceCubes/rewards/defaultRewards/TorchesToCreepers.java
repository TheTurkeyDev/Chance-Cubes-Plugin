package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class TorchesToCreepers implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Torches_To_Creepers";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int yy = -32; yy <= 32; yy++) {
            for (int xx = -32; xx <= 32; xx++) {
                for (int zz = -32; zz <= 32; zz++) {
                    Block b = location.clone().add(xx, yy, zz).getBlock();
                    List<Material> blocks = Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.CHEST,
                            Material.TRAPPED_CHEST, Material.DROPPER, Material.DISPENSER, Material.JUKEBOX,
                            Material.NOTE_BLOCK, Material.MOB_SPAWNER, Material.SIGN_POST, Material.WALL_SIGN,
                            Material.ENDER_CHEST, Material.BREWING_STAND, Material.HOPPER);
                    if (b.getLightLevel() > 0 && !blocks.contains(b.getType())) {
                        RewardsUtil.placeBlock(Material.AIR, location.clone().add(xx, yy, zz));
                        location.getWorld().spawnEntity(location.clone().add(xx + 0.5, yy, zz + 0.5), EntityType.CREEPER);
                    }
                }
            }
        }

        player.sendMessage("Those lights seem a little weird.... O.o");
    }
}
