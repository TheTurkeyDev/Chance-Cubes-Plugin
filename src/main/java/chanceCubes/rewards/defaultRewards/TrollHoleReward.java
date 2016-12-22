package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;

public class TrollHoleReward implements IChanceCubeReward {

    public void fillHole(Location location, Player player, Map<Material, BlockState> storedBlocks) {
        storedBlocks.forEach((material, blockState) -> {
            blockState.setType(material);
            blockState.update(true);
        });

        player.teleport(location.add(0, 1, 0));
    }

    @Override
    public int getChanceValue() {
        return -20;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":TrollHole";
    }

    @Override
    public void trigger(final Location location, final Player player) {

        final Map<Material, BlockState> storedBlocks = new HashMap<>();
        Location playerLoc = player.getLocation();
        final int px = playerLoc.getBlockX();
        final int py = playerLoc.getBlockY() - 1;
        final int pz = playerLoc.getBlockZ();

        for (int y = 0; y < 75; y++) {
            for (int x = -2; x < 3; x++) {
                for (int z = -2; z < 3; z++) {
                    BlockState blockState = new Location(player.getWorld(), px + x, py - y, pz + x).getBlock().getState();
                    storedBlocks.put(blockState.getType(), blockState);
                    blockState.setType(Material.AIR);
                    blockState.update(true);
                }
            }
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> fillHole(new Location(player.getWorld(), px, py, pz), player, storedBlocks), 35);
    }
}