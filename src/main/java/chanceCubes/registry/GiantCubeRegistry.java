package chanceCubes.registry;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.rewards.defaultRewards.BasicReward;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.giantRewards.ChunkFlipReward;
import chanceCubes.rewards.giantRewards.ChunkReverserReward;
import chanceCubes.rewards.giantRewards.FloorIsLavaReward;
import chanceCubes.rewards.giantRewards.FluidTowerReward;
import chanceCubes.rewards.giantRewards.OrePillarReward;
import chanceCubes.rewards.giantRewards.OreSphereReward;
import chanceCubes.rewards.giantRewards.PotionsReward;
import chanceCubes.rewards.giantRewards.TNTSlingReward;
import chanceCubes.rewards.giantRewards.ThrowablesReward;
import chanceCubes.rewards.type.SchematicRewardType;
import chanceCubes.util.FileUtil;
import chanceCubes.util.RewardData;
import chanceCubes.util.SchematicUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GiantCubeRegistry implements IRewardRegistry {

    public static GiantCubeRegistry INSTANCE = new GiantCubeRegistry();

    private Map<String, IChanceCubeReward> nameToReward = Maps.newHashMap();
    private List<IChanceCubeReward> sortedRewards = Lists.newArrayList();

    /**
     * loads the default rewards of the Chance Cube
     */
    public static void loadDefaultRewards() {
        if (!CCubesSettings.enableHardCodedRewards)
            return;

        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getName().toLowerCase() + ":Village", 0, new SchematicRewardType(SchematicUtil.loadCustomSchematic(FileUtil.JSON_PARSER.parse(RewardData.VILLAGE_SCHEMATIC), 0, -1, 0, 0.05f, false, false, false))));

        INSTANCE.registerReward(new BioDomeReward());
        INSTANCE.registerReward(new TNTSlingReward());
        INSTANCE.registerReward(new ThrowablesReward());
        INSTANCE.registerReward(new OrePillarReward());
        INSTANCE.registerReward(new ChunkReverserReward());
        INSTANCE.registerReward(new FloorIsLavaReward());
        INSTANCE.registerReward(new ChunkFlipReward());
        INSTANCE.registerReward(new OreSphereReward());
        INSTANCE.registerReward(new PotionsReward());
        INSTANCE.registerReward(new FluidTowerReward());
    }

    public void ClearRewards() {
        this.sortedRewards.clear();
        this.nameToReward.clear();
    }

    @Override
    public IChanceCubeReward getRewardByName(String name) {
        return nameToReward.get(name);
    }

    private void redoSort(@Nullable IChanceCubeReward newReward) {
        if (newReward != null)
            sortedRewards.add(newReward);

        sortedRewards.sort(Comparator.comparingInt(IChanceCubeReward::getChanceValue));
    }

    @Override
    public void registerReward(IChanceCubeReward reward) {
        if (CCubesSettings.giantChanceCubeRewards.getBoolean(reward.getName(), true) && !nameToReward.containsKey(reward.getName())) {
            nameToReward.put(reward.getName(), reward);
            redoSort(reward);
        }
    }

    @Override
    public void triggerRandomReward(Location location, Player player, int chance) {
        if (location == null)
            return;

        if (this.sortedRewards.size() == 0) {
            CCubesCore.instance().getLogger().log(Level.WARNING, "There are no registered rewards with the Giant Chance Cubes and no reward was able to be given");
            return;
        }

        int pick = new Random().nextInt(sortedRewards.size());
        CCubesCore.instance().getLogger().log(Level.INFO, "Triggered the reward with the name of: " + sortedRewards.get(pick).getName());
        sortedRewards.get(pick).trigger(location, player);
    }

    @Override
    public boolean unregisterReward(String name)
    {
        IChanceCubeReward reward = nameToReward.remove(name);
        return reward != null && sortedRewards.remove(reward);
    }
}