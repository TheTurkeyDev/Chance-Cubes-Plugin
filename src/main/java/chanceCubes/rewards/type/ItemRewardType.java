package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.ItemPart;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ItemRewardType extends BaseRewardType<ItemPart> {

    public ItemRewardType(ItemPart... items) {
        super(items);
    }

    public void spawnStack(ItemPart part, Location location) {
        Item item = (Item) location.getWorld().spawnEntity(location.clone().add(0.5, 0.5, 0.5), EntityType.DROPPED_ITEM);
        item.setItemStack(part.getItemStack());
        part.setDelay(10);
    }

    @Override
    public void trigger(final ItemPart part, Location location, final Player player) {
        if (part.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> spawnStack(part, location), part.getDelay());
        else
            spawnStack(part, location);
    }
}
