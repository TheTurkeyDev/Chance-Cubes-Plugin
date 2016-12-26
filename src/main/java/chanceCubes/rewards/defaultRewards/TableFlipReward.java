package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.material.Stairs;
import org.bukkit.material.Step;

public class TableFlipReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Table_Flip";
    }

    public void nextStep(final int stage, final Location location) {
        RewardsUtil.scheduleTask(() -> {
            Material woodStep = Material.WOOD_STEP;
            Step step = new Step(woodStep);
            Material woodStairs = Material.WOOD_STAIRS;
            Stairs eastStairs = new Stairs(woodStairs);
            eastStairs.setFacingDirection(BlockFace.EAST);
            Stairs westStairs = new Stairs(woodStairs);
            westStairs.setFacingDirection(BlockFace.WEST);
            switch (stage) {
                case 0: {
                    step.setInverted(true);
                    eastStairs.setInverted(true);
                    westStairs.setInverted(true);
                    RewardsUtil.placeBlock(woodStep, step, location);
                    RewardsUtil.placeBlock(woodStairs, westStairs, location.clone().add(1, 0, 0));
                    RewardsUtil.placeBlock(woodStairs, eastStairs, location.clone().add(-1, 0, 0));
                    break;
                }
                case 1: {
                    RewardsUtil.placeBlock(Material.AIR, location);
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(1, 0, 0));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(-1, 0, 0));

                    step.setInverted(true);
                    eastStairs.setInverted(true);
                    westStairs.setInverted(true);
                    RewardsUtil.placeBlock(woodStep, step, location.clone().add(0, 1, 0));
                    RewardsUtil.placeBlock(woodStairs, westStairs, location.clone().add(1, 1, 0));
                    RewardsUtil.placeBlock(woodStairs, eastStairs, location.clone().add(-1, 1, 0));
                    break;
                }
                case 2: {
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(0, 1, 0));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(1, 1, 0));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(-1, 1, 0));

                    step.setInverted(true);
                    eastStairs.setInverted(true);
                    westStairs.setInverted(true);
                    RewardsUtil.placeBlock(woodStep, step, location.clone().add(0, 2, 1));
                    RewardsUtil.placeBlock(woodStairs, westStairs, location.clone().add(1, 2, 1));
                    RewardsUtil.placeBlock(woodStairs, eastStairs, location.clone().add(-1, 2, 1));
                    break;
                }
                case 3: {
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(0, 2, 1));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(1, 2, 1));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(-1, 2, 1));
                    
                    RewardsUtil.placeBlock(woodStep, step, location.clone().add(0, 1, 2));
                    RewardsUtil.placeBlock(woodStairs, westStairs, location.clone().add(1, 1, 2));
                    RewardsUtil.placeBlock(woodStairs, eastStairs, location.clone().add(-1, 1, 2));
                    break;
                }
                case 4: {
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(0, 1, 2));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(1, 1, 2));
                    RewardsUtil.placeBlock(Material.AIR, location.clone().add(-1, 1, 2));
                    
                    RewardsUtil.placeBlock(woodStep, step, location.clone().add(0, 0, 2));
                    RewardsUtil.placeBlock(woodStairs, westStairs, location.clone().add(1, 0, 2));
                    RewardsUtil.placeBlock(woodStairs, eastStairs, location.clone().add(-1, 0, 2));
                    break;
                }
            }

            if (stage < 4)
                nextStep(stage + 1, location);
            
        }, 10);
    }

    @Override
    public void trigger(Location location, Player player) {
        RewardsUtil.sendMessageToAllPlayers(location.getWorld(), "(╯°□°）╯︵ ┻━┻)");
        nextStep(0, location);
    }
}
