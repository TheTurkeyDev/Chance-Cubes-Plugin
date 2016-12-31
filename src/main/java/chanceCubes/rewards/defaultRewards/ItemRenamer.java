package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.achievement.CCubesAchievements;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRenamer implements IChanceCubeReward {

    private String[] adjectives = {"Destroyer", "Terror", "Wrath", "Smasher", "P90", "Wisdom", "Savior",
            "Lightning Bringer", "Rage", "Happiness", "Shocker", " Slayer", "Sunshine", "Giant Crayon", "Blade",
            "Tamer", "Order", "Sharp Edge", "Noodle", "Diamond", "Rod", "Big Giant Sharp Pokey Thing"};
    // @formatter:off
    private String[] names = {"Turkey", "qnxb", "Darkosto", "Wyld", "Funwayguy", "ButtonBoy", "SlothMonster",
            "Vash", "Cazador", "KiwiFails", "Matrixis", "FlameGoat", "iChun", "tibbzeh", "Reninsane",
            "PulpJohnFiction", "Zeek", "Sevadus", "Bob Ross", "T-loves", "Headwound", "JonBams", "Sketch"};
    private Random rand = new Random();

    // @formatter:on

    @Override
    public int getChanceValue() {
        return -35;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getName().toLowerCase() + ":Item_Rename";
    }

    //TODO need to test to see if this actually works
    @Override
    public void trigger(Location location, Player player) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : player.getInventory())
            if (stack != null)
                stacks.add(stack);

        for (ItemStack stack : player.getInventory().getArmorContents())
            if (stack != null)
                stacks.add(stack);

        if (stacks.size() == 0) {
            ItemStack dirt = new ItemStack(Material.DIRT);
            ItemMeta meta = dirt.getItemMeta();
            meta.setDisplayName("A lonely piece of dirt");
            dirt.setItemMeta(meta);
            player.getWorld().dropItem(player.getLocation(), dirt);
            CCubesAchievements.award(player, CCubesAchievements.lonelyDirt);
            return;
        }

        for (int i = 0; i < 3; i++) {
            String name = names[rand.nextInt(names.length)];
            String adj = adjectives[rand.nextInt(adjectives.length)];

            if (name.substring(name.length() - 1).equalsIgnoreCase("s"))
                name += "'";
            else
                name += "'s";

            String newName = name + " " + adj;
            ItemStack itemStack = stacks.get(rand.nextInt(stacks.size()));
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(newName);
            itemStack.setItemMeta(meta);
        }

        player.sendMessage("Those items of yours need a little personality!");

    }
}