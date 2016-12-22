package chanceCubes.listeners;

import chanceCubes.CCubesCore;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.rewards.defaultRewards.CustomUserReward;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectListener implements Listener {

    boolean hasChecked = false;

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        new CustomUserReward(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        ChanceCubeRegistry.INSTANCE.unregisterReward(CCubesCore.instance().getDescription().getPrefix() + ":Custom_Reward_For_" + event.getPlayer().getName());
    }
}