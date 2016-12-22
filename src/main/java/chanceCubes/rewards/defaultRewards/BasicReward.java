package chanceCubes.rewards.defaultRewards;

import chanceCubes.rewards.type.IRewardType;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BasicReward implements IChanceCubeReward {

    private int chance;
    private String name;
    private IRewardType[] rewards;

    public BasicReward(String name, int chance, IRewardType... rewards) {
        this.name = name;
        this.chance = chance;
        this.rewards = rewards;
    }

    @Override
    public int getChanceValue() {
        return this.chance;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void trigger(Location location, Player player) {
        if (rewards != null)
            for (IRewardType reward : rewards)
                reward.trigger(location, player);
    }
}