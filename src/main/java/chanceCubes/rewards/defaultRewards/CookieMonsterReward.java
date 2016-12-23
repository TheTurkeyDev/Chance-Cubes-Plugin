package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class CookieMonsterReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -5;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Cookie_Monster";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        RewardsUtil.sendMessageToNearPlayers(location, 32, "Here have some cookies!");
        Item item = (Item) location.getWorld().spawnEntity(location, EntityType.DROPPED_ITEM);
        item.setItemStack(new ItemStack(Material.COOKIE, 8));
        RewardsUtil.scheduleTask(() -> {
            Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
            zombie.setCustomName("Cookie Monster");
            zombie.setCustomNameVisible(true);
            zombie.setBaby(true);
            RewardsUtil.sendMessageToNearPlayers(location, 32, "[Cookie Monster] Hey! Those are mine!");
        }, 30);
    }
}