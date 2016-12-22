package chanceCubes.config;

import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import org.bukkit.configuration.ConfigurationSection;

public class CCubesSettings {

    public static String[] blockedWorlds = new String[]{};
    public static boolean chestLoot = true;
    public static boolean craftingRecipe = true;
    public static boolean d20UseNormalChances = false;
    public static boolean doesHolidayRewardTrigger = false;
    public static int dropHeight = 20;
    public static boolean enableHardCodedRewards = true;
    public static boolean hasHolidayTexture = false;
    public static IChanceCubeReward holidayReward = null;
    public static boolean holidayRewardTriggered = false;
    public static boolean holidayRewards = true;
    public static String holidayTextureName = "";
    public static int oreGenAmount = 4;
    public static boolean oreGeneration = true;
    public static int pendantUses = 32;
    public static int rangeMax = 20;
    public static int rangeMin = 20;
    public static String rewardURL = "https://raw.githubusercontent.com/wyldmods/ChanceCubes/master/customRewardsV2";
    public static int surfaceGenAmount = 1;
    public static boolean surfaceGeneration = true;
    public static boolean userSpecificRewards = true;
    public static ConfigurationSection giantChanceCubeRewards;
    public static ConfigurationSection rewards;

    public static boolean isBlockedWorld(String world) {
        for (String blockedWorld : blockedWorlds)
            if (blockedWorld.equalsIgnoreCase(world))
                return true;
        return false;
    }
}
