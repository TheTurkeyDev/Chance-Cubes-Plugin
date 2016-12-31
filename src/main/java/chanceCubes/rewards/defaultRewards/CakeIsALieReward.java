package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.achievement.CCubesAchievements;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.Cake;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CakeIsALieReward implements IChanceCubeReward {

    private Random random = new Random();

    @Override
    public int getChanceValue() {
        return 20;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Cake";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        RewardsUtil.sendMessageToNearPlayers(location, 32, "But is it a lie?");
        RewardsUtil.placeBlock(Material.CAKE_BLOCK, new Cake(), location);
        if (random.nextInt(3) == 1)
            RewardsUtil.scheduleTask(() -> update(0, location, player), 20);
    }

    public void update(final int iteration, final Location location, final Player player) {
        Block block = location.getBlock();
        if (block.getType() != Material.CAKE_BLOCK)
            return;

        if (((Cake) block.getState().getData()).getSlicesEaten() > 0) {
            BlockState bs = block.getState();
            bs.setType(Material.AIR);
            bs.update(true);
            RewardsUtil.sendMessageToNearPlayers(location, 32, "It's a lie!!!");
            Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
            if (random.nextInt(10) == 1)
                creeper.setPowered(true);

            creeper.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 2));
            creeper.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60, 999));
            CCubesAchievements.award(player, CCubesAchievements.itsALie);
            return;
        }

        if (iteration == 300) {
            BlockState bs = block.getState();
            bs.setType(Material.AIR);
            bs.update(true);
            return;
        }

        RewardsUtil.scheduleTask(() -> update(iteration + 1, location, player), 20);
    }
}