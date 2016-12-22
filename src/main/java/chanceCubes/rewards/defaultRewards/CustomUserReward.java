package chanceCubes.rewards.defaultRewards;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.items.CCubesItems;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.util.HTTPUtil;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.entity.Player;

public class CustomUserReward implements IChanceCubeReward {

    private List<BasicReward> customRewards = new ArrayList<BasicReward>();
    private String type;
    private String userName = "";
    private UUID uuid = null;

    public CustomUserReward(Player player) {
        if (!CCubesSettings.userSpecificRewards)
            return;
        
        Logger logger = CCubesCore.instance().getLogger();
        JsonElement users;
        try {
            users = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/UserList.json");
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Chance Cubes failed to get the list of users with custom rewards!");
            return;
        }

        UUID uuidTemp = this.getPlayerUUID(player.getName());
        if (uuidTemp == null) {
            logger.log(Level.WARNING, "Chance Cubes failed to get the uuid of the user!");
            return;
        }

        for (JsonElement user : users.getAsJsonArray()) {
            if (user.getAsJsonObject().get("UUID").getAsString().equalsIgnoreCase(uuidTemp.toString())) {
                userName = user.getAsJsonObject().get("Name").getAsString();
                uuid = uuidTemp;
                type = user.getAsJsonObject().get("Type").getAsString();
            }
        }

        if (userName.equals("")) {
            logger.log(Level.INFO, "No custom rewards detected for the current user!");
            return;
        }

        JsonElement userRewards;
        try {
            userRewards = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/Users/" + userName + ".json");
        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Chance Cubes failed to get the custom list for " + userName + "!");
            logger.log(Level.WARNING, e.getMessage());
            return;
        }

        for (Entry<String, JsonElement> reward : userRewards.getAsJsonObject().entrySet()) {
            customRewards.add(CustomRewardsLoader.instance.parseReward(reward).getKey());
        }

        ChanceCubeRegistry.INSTANCE.registerReward(this);
        player.sendMessage("Seems you have some custom Chance Cubes rewards " + this.userName + "....");
        player.sendMessage("Let the fun begin! >:)");
    }

    @Override
    public int getChanceValue() {
        return 0;
    }

    @Override
    public String getName() {
        return CCubesCore.instance().getDescription().getPrefix() + ":CR_" + this.userName;
    }

    public UUID getPlayerUUID(String username) {
        GameProfile gp = ((CraftServer) Bukkit.getServer()).getServer().getUserCache().getProfile(username);
        if (gp == null)
            return null;

        return gp.getId();
    }

    @Override
    public void trigger(final Location location, final Player player) {
        GameProfile gp = ((CraftServer) Bukkit.getServer()).getServer().getUserCache().a(uuid);
        if (gp == null)
            return;

        if (!gp.getName().equalsIgnoreCase(player.getName())) {
            player.sendMessage("Hey you aren't " + this.userName + "! You can't have their reward! Try again!");
            location.getWorld().dropItem(location, CCubesItems.chanceCube);
            return;
        }

        player.sendMessage("Selecting best (possibly deadly) reward for " + this.type + " " + this.userName);
        Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> triggerActualReward(location, player), 100);
    }

    public void triggerActualReward(Location location, Player player) {
        this.customRewards.get(new Random().nextInt(this.customRewards.size())).trigger(location, player);
    }

}
