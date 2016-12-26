package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockRewardType extends BaseRewardType<OffsetBlock> {

    public BlockRewardType(OffsetBlock... blocks) {
        super(blocks);
    }

    @Override
    protected void trigger(OffsetBlock block, Location location, Player player) {
        if (block == null)
            return;

        if (block.isRelativeToPlayer() && !RewardsUtil.isBlockUnbreakable(location))
            block.spawnInWorld(location);
        else if (!RewardsUtil.isBlockUnbreakable(location.clone().add(0, 3, 0)))
            block.spawnInWorld(location);
    }
}
