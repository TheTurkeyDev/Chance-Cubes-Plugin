package chanceCubes.rewards.type;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IRewardType {

    /**
     * What occurs when the this Reward type is triggered
     *
     * @param location
     * @param player
     */
    void trigger(Location location, Player player);
}
