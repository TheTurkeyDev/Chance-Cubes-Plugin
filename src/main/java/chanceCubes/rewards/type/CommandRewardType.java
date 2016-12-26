package chanceCubes.rewards.type;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.CommandPart;
import chanceCubes.util.RewardsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRewardType extends BaseRewardType<CommandPart> {

    public CommandRewardType(CommandPart... commands) {
        super(commands);
    }

    @Override
    public void trigger(final CommandPart command, Location location, final Player player) {
        if (command.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> triggerCommand(command, location, player), command.getDelay());
        else {
            triggerCommand(command, location, player);
        }
    }

    public void triggerCommand(CommandPart command, Location location, Player player) {
        String commandToRun = command.getParsedCommand(location, player);
        Bukkit.dispatchCommand((CommandSender) CCubesCore.instance(), commandToRun);
    }
}
