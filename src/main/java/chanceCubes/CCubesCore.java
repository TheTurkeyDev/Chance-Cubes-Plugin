package chanceCubes;

import chanceCubes.achievement.CCubesAchievements;
import chanceCubes.commands.CCubesServerCommands;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.ConfigLoader;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.hookins.ModHookUtil;
import chanceCubes.items.CCubesItems;
import chanceCubes.listeners.PlayerConnectListener;
import chanceCubes.listeners.WorldGen;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.util.CCubesRecipes;
import org.bukkit.plugin.java.JavaPlugin;

public class CCubesCore extends JavaPlugin {

    public static final String gameVersion = "1.10.2";

    @Override
    public void onEnable() {
        //Load
        ConfigLoader.loadConfigSettings();
        CCubesItems.loadItems();
        //TODO Code for Packet Handling being moved to server only
        //CCubesPacketHandler.init();
        CCubesAchievements.loadAchievements();

        getServer().getPluginManager().registerEvents(new PlayerConnectListener(), this);
        getServer().getPluginManager().registerEvents(new WorldGen(), this);

        if (CCubesSettings.chestLoot) {
            // ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceCube), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceIcosahedron), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceCube), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceIcosahedron), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceCube), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceIcosahedron), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceCube), 1, 2, 5));
            // ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).addItem(new WeightedRandomChestContent(new ItemStack(CCubesBlocks.chanceIcosahedron), 1, 2, 5));
        }

        //TODO convert to server sided gui
        //NetworkRegistry.INSTANCE.registerGuiHandler(this, new CCubesGuiHandler());

        //Init
        CCubesRecipes.loadRecipes();
        //TODO client side only
        //CCubesSounds.loadSounds();

        CCubesItems.registerItems();

        //TODO rendering is client sided
        //proxy.registerRenderings();

        //Post Init
        ChanceCubeRegistry.loadDefaultRewards();
        GiantCubeRegistry.loadDefaultRewards();
        CustomRewardsLoader.instance.loadCustomRewards();
        CustomRewardsLoader.instance.loadHolidayRewards();
        CustomRewardsLoader.instance.loadDisabledRewards();

        //Server Load
        ModHookUtil.loadCustomModRewards();
        getCommand(getId()).setExecutor(new CCubesServerCommands());
    }

    @Override
    public void onDisable()
    {
        CCubesAchievements.save();
    }

    public static CCubesCore instance() {
        return getPlugin(CCubesCore.class);
    }

    public String getId() {
        return getName().toLowerCase();
    }
}
