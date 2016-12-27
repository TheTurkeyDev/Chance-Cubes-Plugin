package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.v1_10_R1.MinecraftKey;
import net.minecraft.server.v1_10_R1.MobEffectList;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.potion.CraftPotionUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.util.Vector;

public class PotionsReward implements IChanceCubeReward {

    private ThrownPotion potion;
    private Random rand = new Random();

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Raining_Potions";
    }

    private ItemStack getRandomPotionItem() {
        ItemStack itemStack = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        Set<MinecraftKey> set = MobEffectList.REGISTRY.keySet();
        PotionData potionData = CraftPotionUtil.toBukkit(set.stream().collect(Collectors.toList()).get(rand.nextInt(set.size())).toString());
        meta.setBasePotionData(potionData);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ThrownPotion spawnThrownPotion(Location location, Vector velocity) {
        ThrownPotion potion = (ThrownPotion) location.getWorld().spawnEntity(location.clone().add(0.5, 0, 0.5), EntityType.SPLASH_POTION);
        potion.setItem(getRandomPotionItem());
        potion.setVelocity(velocity);
        return potion;
    }

    private void throwPotion(final int iteration, final Location location) {
        for (double yy = -0.2; yy <= 1; yy += 0.1)
            spawnThrownPotion(location, new Vector(Math.cos(iteration * (Math.PI / 30)), yy, Math.sin(iteration * (Math.PI / 30))));

        if (iteration < 200)
            RewardsUtil.scheduleTask(() -> throwPotion(iteration + 1, location), 2);
    }

    private void throwPotionCircle(final int iteration, final Location location) {
        for (double rad = -Math.PI; rad <= Math.PI; rad += (Math.PI / 20))
            spawnThrownPotion(location, new Vector(Math.cos(rad) * (0.1 + (0.05 * iteration)), 1, Math.sin(rad) * (0.1 + (0.05 * iteration))));

        if (iteration < 5)
            RewardsUtil.scheduleTask(() -> throwPotionCircle(iteration + 1, location), 20);
    }

    @Override
    public void trigger(final Location location, final Player player) {
        player.sendMessage("It's called art! Look it up!");
        throwPotionCircle(0, location);
        RewardsUtil.scheduleTask(() -> throwPotion(0, location), 140);
    }
}
