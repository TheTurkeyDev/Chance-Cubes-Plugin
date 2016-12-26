package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.material.Torch;

public class RemoveUsefulThingsReward implements IChanceCubeReward {

    private final List<Material> removables = ImmutableList.<Material>builder().add(Material.TORCH).add(Material.STEP)
            .add(Material.FURNACE).add(Material.GLOWSTONE).add(Material.CHEST).build();

    @Override
    public int getChanceValue() {
        return -55;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Remove_Useful_Stuff";
    }

    @Override
    public void trigger(Location location, Player player) {
        int removed = 0;
        for (int yy = -5; yy <= 5; yy++) {
            for (int xx = -5; xx <= 5; xx++) {
                for (int zz = -5; zz <= 5; zz++) {
                    Block block = location.clone().add(xx, yy, zz).getBlock();
                    if (removables.contains(block.getType())) {
                        block.setType(Material.AIR);
                        removed++;
                    }
                }
            }
        }

        if (removed > 3)
            player.sendMessage("Look at all these useful things! #RIP");
        else {
            player.sendMessage("Wow, only " + removed + " useful things around?");
            player.sendMessage("Here, let me give you a helping hand!");

            for (int yy = -2; yy <= 2; yy++) {
                for (int xx = -5; xx <= 5; xx++) {
                    for (int zz = -5; zz <= 5; zz++) {
                        Block block = location.clone().add(xx, yy, zz).getBlock();
                        if (block.getType() == Material.AIR) {
                            BlockState state = block.getState();
                            Torch torch = new Torch();
                            torch.setFacingDirection(BlockFace.UP);
                            state.setData(torch);
                            state.update(true);
                        }
                    }
                }
            }
        }
    }

}
