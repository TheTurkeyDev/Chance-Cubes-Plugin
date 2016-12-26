package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.material.Wool;
import org.bukkit.util.Vector;

public class DoubleRainbow implements IChanceCubeReward {

    DyeColor[] colors = {DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW, DyeColor.GREEN, DyeColor.BLUE, DyeColor.PURPLE};

    @Override
    public int getChanceValue() {
        return 15;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Double_Rainbow";
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.sendMessageToNearPlayers(location, 32, "Double Rainbow!");
        OffsetBlock b;
        for (int x = -7; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                float dist = (float) (Math.abs(location.toVector().distance(new Vector(location.getX() + x, location.getY() + y, location.getZ()))));
                if (dist > 1 && dist <= 8) {
                    int distIndex = (int) (dist - 2);
                    b = new OffsetBlock(x, y, 0, Material.WOOL, new Wool(colors[distIndex]), false);
                    b.setDelay((x + 7) * 10);
                    b.spawnInWorld(location);
                }
            }
        }

        for (int x = -17; x < 18; x++) {
            for (int y = 0; y < 18; y++) {
                float dist = (float) (Math.abs(location.toVector().distance(new Vector(location.getX() + x, location.getY() + y, location.getZ()))));
                if (dist >= 12 && dist <= 18) {
                    int distIndex = (int) (dist - 12);
                    b = new OffsetBlock(x, y, 0, Material.WOOL, new Wool(colors[distIndex]), false);
                    b.setDelay((x + 12) * 5);
                    b.spawnInWorld(location);
                }
            }
        }
    }

}
