package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Jukebox;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class JukeboxReward implements IChanceCubeReward {

    private Material[] discs = new Material[]{Material.RECORD_3, Material.RECORD_4, Material.RECORD_5, Material.RECORD_6, Material.RECORD_7, Material.RECORD_8, Material.RECORD_9, Material.RECORD_10, Material.RECORD_11, Material.RECORD_12, Material.GOLD_RECORD, Material.GREEN_RECORD};
    private Random random = new Random();

    @Override
    public int getChanceValue() {
        return 5;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Juke_Box";
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.placeBlock(Material.JUKEBOX, new MaterialData(Material.JUKEBOX), location);
        Jukebox jukebox = (Jukebox) location.getBlock().getState();
        jukebox.setPlaying(discs[random.nextInt(discs.length)]);
        jukebox.update(true);
    }
}