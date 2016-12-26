package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChargedCreeperReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Charged_Creeper";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        RewardsUtil.placeBlock(Material.AIR, location.clone().add(0, 1, 0));
        Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
        creeper.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1, 99, true, false));
        creeper.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 10, 99, true, false));
        RewardsUtil.scheduleTask(() -> location.getWorld().strikeLightning(location), 2);
    }
}
