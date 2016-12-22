package chanceCubes.rewards.defaultRewards;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IChanceCubeReward {

    /**
     * @return How "lucky" this block is (can be negative). 0 would indicate an "average" reward.
     */
    int getChanceValue();

    /**
     * @return Unique name for reward (suggested to pre-pend MODID).
     */
    String getName();

    /**
     * What occurs when the block is "opened"
     *
     * @param location
     * @param player Player who triggered the block
     */
    void trigger(Location location, Player player);
}