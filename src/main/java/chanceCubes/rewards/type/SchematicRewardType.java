package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.CustomSchematic;
import chanceCubes.util.RewardsUtil;
import java.util.LinkedList;
import java.util.Queue;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SchematicRewardType implements IRewardType {

    private CustomSchematic schematic;

    private Queue<OffsetBlock> stack = new LinkedList<OffsetBlock>();

    public SchematicRewardType(CustomSchematic schematic) {
        this.schematic = schematic;
    }

    public void spawnInBlock(final Queue<OffsetBlock> stack, final CustomSchematic schem, Location location) {
        RewardsUtil.scheduleTask(() -> {
            float lessThan1 = 0;
            while (lessThan1 < 1 && !stack.isEmpty()) {
                OffsetBlock osb = stack.remove();
                osb.spawnInWorld(location);
                lessThan1 += schem.getDelay();
                if (stack.size() == 0)
                    lessThan1 = 1;
            }

            if (stack.size() != 0)
                spawnInBlock(stack, schem, location);
        }, schem.getDelay()< 1 ? 1 : (int) schem.getDelay());
    }

    @Override
    public void trigger(Location location, Player player) {
        for (OffsetBlock osb : schematic.getBlocks())
            stack.add(osb);

        spawnInBlock(stack, schematic, location);
    }

}
