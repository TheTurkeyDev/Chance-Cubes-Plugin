package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ArmorStandArmorReward implements IChanceCubeReward {

    private ItemStack[] bootsItems = {new ItemStack(Material.CHAINMAIL_BOOTS), new ItemStack(Material.DIAMOND_BOOTS),
            new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.LEATHER_BOOTS)};
    private ItemStack[] chestItems = {new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.DIAMOND_CHESTPLATE),
            new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.LEATHER_CHESTPLATE),
            new ItemStack(Material.ELYTRA), new ItemStack(Material.BANNER)};
    private ItemStack[] handItems = {new ItemStack(Material.CAKE), new ItemStack(Material.TORCH),
            new ItemStack(Material.SHIELD), new ItemStack(Material.IRON_SWORD), new ItemStack(Material.DIAMOND_HOE),
            new ItemStack(Material.BANNER), new ItemStack(Material.COOKIE), new ItemStack(Material.STICK),
            new ItemStack(Material.GOLDEN_CARROT)};
    private ItemStack[] headItems = {new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.DIAMOND_HELMET),
            new ItemStack(Material.GOLD_HELMET), new ItemStack(Material.IRON_HELMET), new ItemStack(Material.LEATHER_HELMET),
            new ItemStack(Material.SKULL, 1, (short) 0), new ItemStack(Material.SKULL, 1, (short) 1), new ItemStack(Material.SKULL, 1, (short) 2),
            new ItemStack(Material.SKULL, 1, (short) 3), new ItemStack(Material.SKULL, 1, (short) 4), new ItemStack(Material.SKULL, 1, (short) 5),
            new ItemStack(Material.CHEST)};
    private ItemStack[] legsItems = {new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.DIAMOND_LEGGINGS),
            new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.LEATHER_LEGGINGS)};
    private Random random = new Random();

    @Override
    public int getChanceValue() {
        return 40;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Armor_Stand_Armor";
    }

    @Override
    public void trigger(Location location, Player player) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location.clone().add(0.5, 0.5, 0.5), EntityType.ARMOR_STAND);
        EntityEquipment ee = armorStand.getEquipment();
        ee.setHelmet(headItems[random.nextInt(headItems.length)].clone());
        ee.setChestplate(chestItems[random.nextInt(chestItems.length)].clone());
        ee.setLeggings(legsItems[random.nextInt(legsItems.length)].clone());
        ee.setBoots(bootsItems[random.nextInt(bootsItems.length)].clone());
        ee.setItemInMainHand(handItems[random.nextInt(handItems.length)].clone());
        ee.setItemInOffHand(handItems[random.nextInt(handItems.length)].clone());
    }
}
