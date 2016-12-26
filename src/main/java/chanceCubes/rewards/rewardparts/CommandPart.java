package chanceCubes.rewards.rewardparts;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CommandPart {

    public static String[] elements = new String[]{"item:I", "chance:I", "meta:I", "amountMin:I", "amountMax:I"};
    private String command;
    private int delay = 0;

    public CommandPart(String command) {
        this.command = command;
    }

    public CommandPart(String command, int delay) {
        this.command = command;
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getParsedCommand(Location location, Player player) {
        String parsedCommand = command;
        parsedCommand = parsedCommand.replace("%player", player.getName());
        parsedCommand = parsedCommand.replace("%x", "" + location.getBlockX());
        parsedCommand = parsedCommand.replace("%y", "" + location.getBlockY());
        parsedCommand = parsedCommand.replace("%z", "" + location.getBlockZ());
        Location playerLoc = player.getLocation();
        parsedCommand = parsedCommand.replace("%px", "" + playerLoc.getBlockX());
        parsedCommand = parsedCommand.replace("%py", "" + playerLoc.getBlockY());
        parsedCommand = parsedCommand.replace("%pz", "" + playerLoc.getBlockZ());

        return parsedCommand;
    }

    public String getRawCommand() {
        return command;
    }
}
