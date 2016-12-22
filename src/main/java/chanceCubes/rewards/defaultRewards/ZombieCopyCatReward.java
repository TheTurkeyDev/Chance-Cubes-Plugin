package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Arrays;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ZombieCopyCatReward implements IChanceCubeReward {

    @Override
    public int getChanceValue() {
        return -25;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Copy_Cat_Zombie";
    }

    @Override
    public void trigger(Location location, Player player) {
        Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        ItemStack weapon = null;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack != null && Arrays.asList(Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD).contains(stack.getType())) {
                weapon = stack.clone();
            }
        }

        if (weapon == null && player.getInventory().getItemInMainHand() != null) {
            weapon = player.getInventory().getItemInMainHand().clone();
        }

        EntityEquipment ee = zombie.getEquipment();
        ee.setItemInMainHand(weapon);
        //TODO Current version of the Forge version has this enabled. May change in the future
        ee.setItemInMainHandDropChance(1F);

        PlayerInventory playerInventory = player.getInventory();
        if (playerInventory.getBoots() != null) {
            ee.setBoots(playerInventory.getBoots().clone());
        }
        if (playerInventory.getLeggings() != null) {
            ee.setLeggings(playerInventory.getLeggings().clone());
        }
        if (playerInventory.getChestplate() != null) {
            ee.setChestplate(playerInventory.getChestplate().clone());
        }
        if (playerInventory.getHelmet() != null) {
            ee.setHelmet(playerInventory.getHelmet().clone());
        }
    }
}