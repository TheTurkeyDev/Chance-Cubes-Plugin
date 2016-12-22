package chanceCubes.registry;

import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IRewardRegistry {

    @Nullable
    IChanceCubeReward getRewardByName(String name);

    /**
     * Registers the given reward as a possible outcome
     *
     * @param reward to register
     */
    void registerReward(IChanceCubeReward reward);

    /**
     * Triggers a random reward in the given world at the given location
     *
     * @param location      The location of the reward
     * @param player     The player receiving the reward
     */
    void triggerRandomReward(Location location, Player player, int chance);

    /**
     * Unregisters a reward with the given name
     *
     * @param name of the reward to remove
     * @return true is a reward was successfully removed, false if a reward was not removed
     */
    boolean unregisterReward(String name);
}
