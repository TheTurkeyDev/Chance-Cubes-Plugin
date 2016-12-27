package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChunkReverserReward implements IChanceCubeReward {

    private List<Entry<Material, Material>> swappedMap = new ArrayList<>();

    public ChunkReverserReward() {
        swappedMap.add(new SimpleEntry<>(Material.STONE, Material.DIRT));
        swappedMap.add(new SimpleEntry<>(Material.DIRT, Material.COBBLESTONE));
        swappedMap.add(new SimpleEntry<>(Material.GRASS, Material.STONE));
        swappedMap.add(new SimpleEntry<>(Material.GRAVEL, Material.SAND));
        swappedMap.add(new SimpleEntry<>(Material.SAND, Material.GRAVEL));
        swappedMap.add(new SimpleEntry<>(Material.IRON_ORE, Material.GOLD_ORE));
        swappedMap.add(new SimpleEntry<>(Material.COAL_ORE, Material.DIAMOND_ORE));
        swappedMap.add(new SimpleEntry<>(Material.DIAMOND_ORE, Material.COAL_ORE));
        swappedMap.add(new SimpleEntry<>(Material.GOLD_ORE, Material.IRON_ORE));
        swappedMap.add(new SimpleEntry<>(Material.LAVA, Material.WATER));
        swappedMap.add(new SimpleEntry<>(Material.WATER, Material.LAVA));
        swappedMap.add(new SimpleEntry<>(Material.LOG, Material.LEAVES));
        swappedMap.add(new SimpleEntry<>(Material.LOG_2, Material.LEAVES_2));
        swappedMap.add(new SimpleEntry<>(Material.LEAVES, Material.LOG));
        swappedMap.add(new SimpleEntry<>(Material.LEAVES_2, Material.LOG_2));
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Chuck_Reverse";
    }

    @Override
    public void trigger(Location location, Player player) {
        player.sendMessage("Initiating Block Inverter");
        List<OffsetBlock> blocks = new ArrayList<>();
        int delay = 0;
        for (int yy = 256; yy > 0; yy--) {
            int xx = 0, zz = 0, dx = 0, dy = -1;
            int t = 16;
            int maxI = t * t;

            for (int i = 0; i < maxI; i++) {
                if ((-16 / 2 <= xx) && (xx <= 16 / 2) && (-16 / 2 <= zz) && (zz <= 16 / 2)) {
                    Material blockAt = new Location(location.getWorld(), location.getX() + xx, yy, location.getZ() + zz).getBlock().getType();
                    Material toSwapTo = null;
                    for (Entry<Material, Material> blockSwap : swappedMap)
                        if (blockSwap.getKey().equals(blockAt))
                            toSwapTo = blockSwap.getValue();

                    if (toSwapTo != null) {
                        blocks.add(new OffsetBlock(xx, yy - location.getBlockY(), zz, toSwapTo, false, delay / 5));
                        delay++;
                    }
                }

                if ((xx == zz) || ((xx < 0) && (xx == -zz)) || ((xx > 0) && (xx == 1 - zz))) {
                    t = dx;
                    dx = -dy;
                    dy = t;
                }

                xx += dx;
                zz += dy;
            }
        }

        player.sendMessage("Inverting " + blocks.size() + " Blocks... May take a minute...");
        for (OffsetBlock b : blocks)
            b.spawnInWorld(location);
    }

}
