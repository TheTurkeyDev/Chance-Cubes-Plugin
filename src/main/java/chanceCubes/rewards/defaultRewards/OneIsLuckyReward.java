package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.items.CCubesItems;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.FixedMetadataValue;

public class OneIsLuckyReward implements IChanceCubeReward {

    private Random random = new Random();

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":One_Is_Lucky";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        RewardsUtil.sendMessageToNearPlayers(location, 32, "A Lucky Block Salute");
        boolean leftLucky = random.nextBoolean();
        if (RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), new MaterialData(CCubesItems.chanceCube.getType()), location.clone().add(-1, 0, 0))) {
            BlockState chanceCube = location.clone().add(-1, 0, 0).getBlock().getState();
            chanceCube.setMetadata("ChanceCubes-Chance", new FixedMetadataValue(CCubesCore.instance(), leftLucky ? 100 : -100));
            chanceCube.update(true);
        }

        if (RewardsUtil.placeBlock(Material.SIGN_POST, new org.bukkit.material.Sign(), location)) {
            Sign sign = (Sign) location.getBlock().getState();
            sign.setLine(0, "One is lucky");
            sign.setLine(1, "One is not");
            sign.setLine(3, "#OGLuckyBlocks");
            sign.update(true);
        }

        if (RewardsUtil.placeBlock(CCubesItems.chanceCube.getType(), new MaterialData(CCubesItems.chanceCube.getType()), location.clone().add(-1, 0, 0))) {
            BlockState chanceCube = location.clone().add(-1, 0, 0).getBlock().getState();
            chanceCube.setMetadata("ChanceCubes-Chance", new FixedMetadataValue(CCubesCore.instance(), !leftLucky ? 100 : -100));
            chanceCube.update(true);
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> update(0, location), 10);
    }

    public void update(final int iteration, final Location location) {
        BlockState left = location.clone().subtract(1, 0, 0).getBlock().getState();
        BlockState center = location.clone().getBlock().getState();
        BlockState right = location.clone().add(1, 0, 0).getBlock().getState();
        boolean flag = false;

        if (left.getType() == Material.AIR || right.getType() == Material.AIR)
            flag = true;

        if (iteration == 600 || flag) {
            left.setType(Material.AIR);
            left.update(true);
            center.setType(Material.AIR);
            center.update(true);
            left.setType(Material.AIR);
            left.update(true);
            return;
        }

        RewardsUtil.scheduleTask(() -> update(iteration + 1, location), 10);
    }
}
