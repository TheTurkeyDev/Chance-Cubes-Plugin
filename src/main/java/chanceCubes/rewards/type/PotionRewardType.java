package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.PotionPart;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class PotionRewardType extends BaseRewardType<PotionPart> {

    public PotionRewardType(PotionPart... effects) {
        super(effects);
    }

    @Override
    public void trigger(final PotionPart part, Location location, final Player player) {
        if (part.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> triggerPotion(part, player), part.getDelay());
        else
            triggerPotion(part, player);
    }

    public void triggerPotion(PotionPart part, Player player) {
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.addCustomEffect(part.getEffect(), true);
        Location playerLoc = player.getLocation();
        playerLoc.getWorld().spawnEntity(playerLoc.clone().add(0, 2, 0), EntityType.SPLASH_POTION);
    }
}
