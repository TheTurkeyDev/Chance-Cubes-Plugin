package chanceCubes.rewards.type;

import chanceCubes.rewards.rewardparts.MessagePart;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MessageRewardType extends BaseRewardType<MessagePart> {

    public MessageRewardType(MessagePart... messages) {
        super(messages);
    }

    public void sendMessage(MessagePart message, Location location, Player player) {
        location.getWorld().getPlayers().forEach(p -> {
            if (p.getUniqueId().equals(player.getUniqueId()))
                p.sendMessage(message.getMessage());
            else {
                Location playerLoc = player.getLocation();
                double dist = Math.sqrt(Math.pow(location.getBlockX() - playerLoc.getBlockX(), 2) + Math.pow(location.getBlockY() - playerLoc.getBlockY(), 2) + Math.pow(location.getBlockZ() - playerLoc.getBlockZ(), 2));
                if (dist <= message.getRange() || message.isServerWide())
                    p.sendMessage(message.getMessage());
            }
        });
    }

    @Override
    public void trigger(final MessagePart message, Location location, final Player player) {
        if (message.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> sendMessage(message, location, player), message.getDelay());
        else
            sendMessage(message, location, player);
    }
}
