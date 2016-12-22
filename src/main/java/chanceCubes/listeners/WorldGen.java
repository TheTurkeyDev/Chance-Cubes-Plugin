package chanceCubes.listeners;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.items.CCubesItems;
import java.util.Random;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.WorldGenMinable;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.metadata.FixedMetadataValue;

public class WorldGen implements Listener {

    //TODO left off here. looking into block population
    @EventHandler
    public void onGenerate(WorldInitEvent event) {
        World world = event.getWorld();
        if (CCubesSettings.isBlockedWorld(world.getName()))
            return;

        if (CCubesSettings.surfaceGeneration)
            world.getPopulators().add(new SurfaceGenerator());

        if (CCubesSettings.oreGeneration)
            world.getPopulators().add(new OreGenerator());
    }

    private static class OreGenerator extends BlockPopulator {

        @Override
        public void populate(World world, Random random, Chunk source) {
            for (int k = 0; k < CCubesSettings.oreGenAmount; k++) {
                int firstBlockXCoord = source.getX() + random.nextInt(16);
                int firstBlockYCoord = random.nextInt(100);
                int firstBlockZCoord = source.getZ() + random.nextInt(16);
                //TODO would prefer to avoid NMS but can't find a way to do it in a simple way
                (new WorldGenMinable(Blocks.GOLD_BLOCK.getBlockData(), 3)).generate(((CraftWorld) world).getHandle(), random, new BlockPosition(firstBlockXCoord, firstBlockYCoord, firstBlockZCoord));
            }
        }
    }

    private static class SurfaceGenerator extends BlockPopulator {

        @Override
        public void populate(World world, Random random, Chunk source) {
            if (random.nextInt(100) < CCubesSettings.surfaceGenAmount) {
                int x = source.getX() + random.nextInt(16);
                int z = source.getZ() + random.nextInt(16);
                int y = world.getHighestBlockYAt(x, z);

                Location location = new Location(world, x, y - 1, z);
                if (world.getBlockAt(location).getType() == Material.BEDROCK) {
                    for (int yLoop = 0; yLoop < y; yLoop++) {
                        Location loc = new Location(world, x, yLoop, z);
                        if (world.getBlockAt(loc).getType().isSolid() && world.getBlockAt(location.clone().add(0, 1, 0)).getType() == Material.AIR) {
                            y = yLoop;
                            break;
                        }
                    }
                }

                BlockState state = world.getBlockAt(new Location(world, x, y, z)).getState();
                state.setType(CCubesItems.chanceCube.getType());
                //TODO need this to possibly return another volume
                state.setMetadata("ChanceCubes", new FixedMetadataValue(CCubesCore.instance(), "Test"));
                state.update(true);
            }
        }
    }
}