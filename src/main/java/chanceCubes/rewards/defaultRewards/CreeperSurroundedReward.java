package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperSurroundedReward implements IChanceCubeReward {

    private Random rand = new Random();

    @Override
    public int getChanceValue() {
        return -85;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Surrounded_Creeper";
    }

    @Override
    public void trigger(Location location, Player player) {
        int px = player.getLocation().getBlockX();
        int pz = player.getLocation().getBlockZ();
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1, true, false));
        boolean skip = false;
        Creeper creeper;
        for (int xx = 0; xx < 2; xx++) {
            for (int zz = -4; zz < 5; zz++) {
                if (!skip) {
                    int xxx = xx == 1 ? px + 4 : px - 4;
                    BlockState blockState = new Location(location.getWorld(), px + xxx, location.getBlockY(), pz + zz).getBlock().getState();
                    BlockState blockState2 = new Location(location.getWorld(), px + xxx, location.getBlockY() + 1, pz + zz).getBlock().getState();
                    BlockState blockState3 = new Location(location.getWorld(), px + xxx, location.getBlockY() + 2, pz + zz).getBlock().getState();
                    if (!blockState.getType().isSolid() && !blockState2.getType().isSolid() && !blockState3.getType().isSolid()) {
                        creeper = (Creeper) location.getWorld().spawnEntity(new Location(location.getWorld(), xxx, location.getBlockY(), pz + zz, xx == 1 ? 90 : -90, 0), EntityType.CREEPER);
                        if (rand.nextInt(10) == 1)
                            creeper.setPowered(true);
                        
                        creeper.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 5));
                    }
                }

                skip = !skip;
            }
        }

        for (int xx = -4; xx < 5; xx++) {
            for (int zz = 0; zz < 2; zz++) {
                if (!skip) {
                    int zzz = zz == 1 ? pz + 4 : pz - 4;
                    if (new Location(location.getWorld(), px + xx, location.getBlockY(), zzz).getBlock().getType() == Material.AIR && new Location(location.getWorld(), px + xx, location.getBlockY() + 1, zzz).getBlock().getType() == Material.AIR && new Location(location.getWorld(), px + xx, location.getBlockY() + 2, zzz).getBlock().getType() == Material.AIR) {
                        creeper = (Creeper) location.getWorld().spawnEntity(new Location(location.getWorld(), px + xx, location.getBlockY(), zzz, zz == 1 ? 180 : 0, 0), EntityType.CREEPER);
                        if (rand.nextInt(10) == 1)
                            creeper.setPowered(true);

                        creeper.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 5));
                    }
                }

                skip = !skip;
            }
        }
    }
}
