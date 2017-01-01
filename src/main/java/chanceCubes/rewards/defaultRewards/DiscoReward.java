package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.items.CCubesItems;
import chanceCubes.tileentities.ChanceD20Data;
import chanceCubes.util.RewardsUtil;
import java.util.Random;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;

public class DiscoReward implements IChanceCubeReward {

    private Random rand = new Random();

    @Override
    public int getChanceValue() {
        return 40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Disco";
    }

    @Override
    public void trigger(Location location, Player player) {
        for (int xx = -4; xx < 5; xx++)
            for (int zz = -4; zz < 5; zz++) {
                RewardsUtil.placeBlock(Material.WOOL, new Wool(DyeColor.values()[rand.nextInt(16)]), location.clone().add(xx, -1, zz));
            }

        for (int i = 0; i < 10; i++) {
            Sheep sheep = (Sheep) location.getWorld().spawnEntity(location.clone().add(0, 1, 0), EntityType.SHEEP);
            sheep.setCustomName("jeb_");
            sheep.setCustomNameVisible(true);
        }

        if (RewardsUtil.placeBlock(CCubesItems.chanceIcosahedron.getType(), new MaterialData(CCubesItems.chanceIcosahedron.getType()), location.clone().add(0, 3, 0)))
            CCubesBlocks.addChanceD20(new ChanceD20Data(location));

        RewardsUtil.sendMessageToNearPlayers(location, 32, "Disco Party!!!!");
    }
}