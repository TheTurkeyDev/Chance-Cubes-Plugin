package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.CCubesDamageSource;
import chanceCubes.util.MazeGenerator;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MazeReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -25;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Maze";
    }

    @Override
    public void trigger(final Location location, final Player player) {
        player.sendMessage("Generating maze..... May be some lag...");
        final MazeGenerator gen = new MazeGenerator();
        gen.generate(location, 20, 20);
        Location playerLoc = player.getLocation();
        final int px = playerLoc.getBlockX();
        final int py = playerLoc.getBlockY();
        final int pz = playerLoc.getBlockZ();
        player.teleport(location.clone().subtract(8.5, 0, 8.5));

        RewardsUtil.scheduleTask(() -> update(0, gen, new Location(location.getWorld(), px, py, pz), player), 20);
        player.sendMessage("Beat the maze and find the sign!");
        player.sendMessage("You have 45 seconds!");
    }

    public void update(final int iteration, final MazeGenerator gen, final Location playerLoc, final Player player) {
        if (iteration == 46) {
            gen.endMaze();
            return;
        }

        if (iteration == 45) {
            player.teleport(playerLoc);
            ((CraftPlayer) player).getHandle().damageEntity(CCubesDamageSource.mazefail, Float.MAX_VALUE);
        }
        else if (gen.endBlockWorldCords.getBlock().getType() != Material.SIGN_POST) {
            player.sendMessage("Hey! You won!");
            gen.endMaze();
            player.teleport(playerLoc);
            return;
        }
        else if (iteration == 15) {
            player.sendMessage("30 seconds left!!");
        }
        else if (iteration == 40) {
            player.sendMessage("5...");
        }
        else if (iteration == 41) {
            player.sendMessage("4...");
        }
        else if (iteration == 42) {
            player.sendMessage("3...");
        }
        else if (iteration == 43) {
            player.sendMessage("2...");
        }
        else if (iteration == 44) {
            player.sendMessage("1!");
        }

        RewardsUtil.scheduleTask(() -> update(0, gen, playerLoc, player), 20);
    }
}