package chanceCubes.config;

import chanceCubes.CCubesCore;
import java.io.File;
import java.util.Arrays;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigLoader {

    private static final String genCat = "general_settings";
    private static final String giantRewardCat = "giant_chance_cube_rewards";
    private static final String rewardCat = "rewards";
    public static File folder;

    public static void loadConfigSettings() {
        CCubesCore cc = CCubesCore.instance();
        cc.saveDefaultConfig();
        folder = cc.getDataFolder();
        
        FileConfiguration config = cc.getConfig();
        if (config.isSet(genCat)) {
            ConfigurationSection general = config.getConfigurationSection(genCat);
            CCubesSettings.blockedWorlds = general.isSet("BlockedWorlds") ? general.getStringList("BlockedWorlds").toArray(new String[0]) : new String[0];
            CCubesSettings.chestLoot = general.getBoolean("ChestLoot", true);
            CCubesSettings.craftingRecipe = general.getBoolean("CraftingRecipe", true);
            CCubesSettings.d20UseNormalChances = general.getBoolean("D20UseNormalChanceValues", false);
            CCubesSettings.enableHardCodedRewards = general.getBoolean("EnableDefaultRewards", true);
            CCubesSettings.dropHeight = general.getInt("FallingBlockDropHeight", 20);
            CCubesSettings.oreGeneration = general.getBoolean("GenerateAsOre", true);
            CCubesSettings.surfaceGeneration = general.getBoolean("GenerateOnSurface", true);
            CCubesSettings.holidayRewardTriggered = general.getBoolean("HolidayRewardTriggered", false);
            CCubesSettings.holidayRewards = general.getBoolean("HolidayRewards", true);
            CCubesSettings.userSpecificRewards = general.getBoolean("UserSpecificRewards", true);
            CCubesSettings.rangeMax = general.getInt("chanceRangeMax", 20);
            CCubesSettings.rangeMin = general.getInt("chanceRangeMin", 20);
            CCubesSettings.oreGenAmount = general.getInt("oreGenAmount", 4);
            CCubesSettings.pendantUses = general.getInt("pendantUses", CCubesSettings.pendantUses);
            CCubesSettings.surfaceGenAmount = general.getInt("surfaceGenAmount", 1);
        }
        
        if (config.isSet(giantRewardCat))
            CCubesSettings.giantChanceCubeRewards = config.getConfigurationSection(giantRewardCat);
        
        if (config.isSet(rewardCat))
            CCubesSettings.rewards = config.getConfigurationSection(rewardCat);
        
        File customConfigFolder = new File(folder, "CustomRewards");
        customConfigFolder.mkdirs();

        new File(folder, "Schematics").mkdirs();
        new CustomRewardsLoader(customConfigFolder);
    }
    
    public static void save() {
        CCubesCore cc = CCubesCore.instance();
        cc.saveDefaultConfig();
        FileConfiguration config = cc.getConfig();
        ConfigurationSection general = config.isSet(genCat) ? config.getConfigurationSection(genCat) : config.createSection(genCat);
        general.set("BlockedWorlds", Arrays.asList(CCubesSettings.blockedWorlds));
        general.set("ChestLoot", CCubesSettings.chestLoot);
        general.set("CraftingRecipe", CCubesSettings.craftingRecipe);
        general.set("D20UseNormalChanceValues", CCubesSettings.d20UseNormalChances);
        general.set("EnableDefaultRewards", CCubesSettings.enableHardCodedRewards);
        general.set("FallingBlockDropHeight", CCubesSettings.dropHeight);
        general.set("GenerateAsOre", CCubesSettings.oreGeneration);
        general.set("GenerateOnSurface", CCubesSettings.surfaceGeneration);
        general.set("HolidayRewardTriggered", CCubesSettings.holidayRewardTriggered);
        general.set("HolidayRewards", CCubesSettings.holidayRewards);
        general.set("UserSpecificRewards", CCubesSettings.userSpecificRewards);
        general.set("chanceRangeMax", CCubesSettings.rangeMax);
        general.set("chanceRangeMin", CCubesSettings.rangeMin);
        general.set("oreGenAmount", CCubesSettings.oreGenAmount);
        general.set("pendantUses", CCubesSettings.pendantUses);
        general.set("surfaceGenAmount", CCubesSettings.surfaceGenAmount);
        cc.saveConfig();
    }
}