package chanceCubes.rewards.giantRewards;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.biodomeGen.BasicTreesBiome;
import chanceCubes.rewards.biodomeGen.DesertBiome;
import chanceCubes.rewards.biodomeGen.EndBiome;
import chanceCubes.rewards.biodomeGen.IBioDomeBiome;
import chanceCubes.rewards.biodomeGen.NetherBiome;
import chanceCubes.rewards.biodomeGen.OceanBiome;
import chanceCubes.rewards.biodomeGen.SnowGlobeBiome;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.Scheduler;
import chanceCubes.util.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BioDomeReward implements IChanceCubeReward {

    public static final int delayShorten = 10;
    private IBioDomeBiome[] biomes = new IBioDomeBiome[]{new BasicTreesBiome(), new DesertBiome(),
            new EndBiome(), new OceanBiome(), new SnowGlobeBiome(), new NetherBiome()};
    private Random rand = new Random();

    public void genDome(final Location location, final IBioDomeBiome spawnedBiome) {
        genDomePart(0, -25, location, spawnedBiome);
    }

    public void genDomePart(final int yinc, final int xinc, final Location location, final IBioDomeBiome spawnedBiome) {
        List<OffsetBlock> blocks = new ArrayList<>();
        int delay = 0;
        for (int z = -25; z <= 25; z++) {
            Location loc = new Location(location.getWorld(), xinc, yinc, z);
            float dist = (float) (Math.abs(loc.distance(new Location(location.getWorld(), 0, 0, 0)) - 25));
            if (dist < 1) {
                if (dist >= 0) {
                    blocks.add(new OffsetBlock(xinc, yinc, z, Material.GLASS, false, (delay / delayShorten)));
                    delay++;
                }
                else if (yinc == 0) {
                    blocks.add(new OffsetBlock(xinc, yinc, z, spawnedBiome.getFloorBlock(), false, (delay / delayShorten)));
                    delay++;
                }

                spawnedBiome.getRandomGenBlock(dist, rand, xinc, yinc, z, blocks, delay);
            }
        }

        final int nextXinc = xinc + 1 > 25 ? (-25) : xinc + 1;
        int Yinctemp = yinc;
        if (nextXinc == -25) {
            Yinctemp = Yinctemp + 1 > 25 ? -1 : Yinctemp + 1;
        }

        if (Yinctemp == -1) {
            Scheduler.scheduleTask(new Task("Entity_Delays", delay) {
                @Override
                public void callback() {
                    spawnedBiome.spawnEntities(location);
                }
            });
            return;
        }

        final int nextYinc = Yinctemp;

        for (OffsetBlock b : blocks)
            b.spawnInWorld(location);

        RewardsUtil.scheduleTask(() -> genDomePart(nextYinc, nextXinc, location, spawnedBiome), delay / delayShorten);
        Task task = new Task("BioDome Reward", (delay / delayShorten)) {

            @Override
            public void callback() {
                genDomePart(nextYinc, nextXinc, location, spawnedBiome);
            }

        };

        Scheduler.scheduleTask(task);
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":BioDome";
    }

    @Override
    public void trigger(final Location location, Player player) {
        // player.addChatMessage(new ChatComponentText("Hey! I can be a Pandora's Box to!"));

        final IBioDomeBiome spawnedBiome = biomes[rand.nextInt(biomes.length)];
        genDome(location, spawnedBiome);
    }

}
