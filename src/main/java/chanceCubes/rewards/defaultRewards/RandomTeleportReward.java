package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RandomTeleportReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -15;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Random_Teleport";
    }

    @Override
    public void trigger(Location location, Player player) {
        int xChange = ((world.rand.nextInt(50) + 20) + pos.getX()) - 35;
        int zChange = ((world.rand.nextInt(50) + 20) + pos.getZ()) - 35;

        int yChange = -1;

        for (int yy = 0; yy <= world.getActualHeight(); yy++) {
            if (world.isAirBlock(new BlockPos(xChange, yy, zChange)) && world.isAirBlock(new BlockPos(xChange, yy + 1, zChange))) {
                yChange = yy;
                break;
            }
        }
        if (yChange == -1)
            return;

        player.setPositionAndUpdate(xChange, yChange, zChange);
    }
}