package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WaitForItReward implements IChanceCubeReward {

    private final Random rand = new Random();

    @Override
    public int getChanceValue() {
        return -30;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Wait_For_It";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        player.addChatMessage(new TextComponentString("Wait for it......."));

        Task task = new Task("Wait For It", rand.nextInt(4000) + 1000) {
            @Override
            public void callback() {
                triggerRealReward();
            }

            private void triggerRealReward() {
                int reward = rand.nextInt(3);
                player.addChatMessage(new TextComponentString("NOW!"));

                if (reward == 0) {
                    world.spawnEntityInWorld(new EntityTNTPrimed(world, player.posX, player.posY, player.posZ, null));
                }
                else if (reward == 1) {
                    Entity ent = new EntityCreeper(world);
                    ent.setLocationAndAngles(player.posX, player.posY, player.posZ, 0, 0);
                    world.spawnEntityInWorld(ent);
                }
                else if (reward == 2) {
                    world.setBlockState(new BlockPos(player.posX, player.posY, player.posZ), Blocks.BEDROCK.getDefaultState());
                }
            }
        };

        Scheduler.scheduleTask(task);
    }

}
