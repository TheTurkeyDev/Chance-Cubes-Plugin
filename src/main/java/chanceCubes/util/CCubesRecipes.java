package chanceCubes.util;

import chanceCubes.config.CCubesSettings;
import chanceCubes.items.CCubesItems;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.Dye;

public class CCubesRecipes {

    public static void loadRecipes() {
        if (CCubesSettings.craftingRecipe) {
            Dye dye = new Dye();
            dye.setColor(DyeColor.BLUE);
            Bukkit.addRecipe(new ShapedRecipe(CCubesItems.chanceCube).setIngredient('L', Material.LAPIS_BLOCK).setIngredient('B', dye).shape("LLL", "LBL", "LLL"));
            Bukkit.addRecipe(new ShapedRecipe(CCubesItems.chancePendantT1).setIngredient('G', Material.GOLD_BLOCK).setIngredient('B', Material.LAPIS_BLOCK).setIngredient('D', Material.DIAMOND_BLOCK).shape("GBG", "BDB", "GBG"));
            //TODO need to check that the Chance Pendant T1 is being used, not just an ItemFrame
            Bukkit.addRecipe(new ShapedRecipe(CCubesItems.chancePendantT2).setIngredient('P', CCubesItems.chancePendantT1.getType()).setIngredient('B', Material.LAPIS_BLOCK).setIngredient('G', Material.GOLD_BLOCK).shape("GBG", "BPB", "GBG"));
            //TODO need to check that the Chance Pendant T2 is being used, not just an ItemFrame
            Bukkit.addRecipe(new ShapedRecipe(CCubesItems.chancePendantT3).setIngredient('P', CCubesItems.chancePendantT2.getType()).setIngredient('B', Material.LAPIS_BLOCK).setIngredient('G', Material.GOLD_BLOCK).shape("GBG", "BPB", "GBG"));

            Bukkit.addRecipe(new ShapedRecipe(CCubesItems.silkPendant).setIngredient('P', CCubesItems.chancePendantT1.getType()).setIngredient('B', Material.LAPIS_BLOCK).setIngredient('S', Material.STRING).shape("SBS", "SPS", "SBS"));

            Bukkit.addRecipe(new ShapedRecipe(CCubesItems.scanner).setIngredient('P', CCubesItems.chancePendantT1.getType()).setIngredient('G', Material.GLASS).setIngredient('I', Material.IRON_INGOT).shape("IGI", "GPG", "IGI"));
        }
    }
}
