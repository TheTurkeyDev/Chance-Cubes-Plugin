package chanceCubes.commands;

import chanceCubes.CCubesCore;
import chanceCubes.config.CustomRewardsLoader;
import chanceCubes.hookins.ModHookUtil;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.MinecraftKey;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CCubesServerCommands implements CommandExecutor {

    private final List<String> tab = new ArrayList<>();
    private final List<String> aliases = new ArrayList<>();

    public CCubesServerCommands() {
        aliases.add("Chancecubes");
        aliases.add("chancecubes");
        aliases.add("ChanceCube");
        aliases.add("Chancecube");
        aliases.add("chancecube");
        aliases.add("CCubes");
        
        tab.add("reload");
        tab.add("version");
        tab.add("handNBT");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            ChanceCubeRegistry.INSTANCE.ClearRewards();
            GiantCubeRegistry.INSTANCE.ClearRewards();
            ChanceCubeRegistry.loadDefaultRewards();
            GiantCubeRegistry.loadDefaultRewards();
            CustomRewardsLoader.instance.loadCustomRewards();
            CustomRewardsLoader.instance.loadHolidayRewards();
            ChanceCubeRegistry.loadCustomUserRewards();
            ModHookUtil.loadCustomModRewards();
            sender.sendMessage("Rewards Reloaded");
        }
        else if (args.length > 0 && args[0].equalsIgnoreCase("version")) {
            sender.sendMessage("Chance Cubes Version " + CCubesCore.instance().getDescription().getVersion());
        }
        else if (args[0].equalsIgnoreCase("handNBT")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.getInventory().getItemInMainHand() != null) {
                    NBTTagCompound nbt = CraftItemStack.asNMSCopy(player.getInventory().getItemInMainHand()).getTag();
                    if (nbt != null) {
                        sender.sendMessage(nbt.toString());
                    }
                    else {
                        sender.sendMessage("This item has no tag nbt data");
                    }
                }
            }
        }
        else if (args[0].equalsIgnoreCase("handID")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                ItemStack stack = player.getInventory().getItemInMainHand();
                if (stack != null) {
                    MinecraftKey mk = Item.REGISTRY.b(CraftItemStack.asNMSCopy(stack).getItem());
                    sender.sendMessage(mk != null ? mk.toString() : "null");
                    sender.sendMessage("meta: " + stack.getDurability());
                }
            }
        }
        else if (args[0].equalsIgnoreCase("disableReward")) {
            if (args.length > 1) {
                if (ChanceCubeRegistry.INSTANCE.unregisterReward(args[1])) {
                    sender.sendMessage(args[1] + " Has been temporarily disabled.");
                }
                else {
                    sender.sendMessage(args[1] + " is either not currently enabled or is not a valid reward name.");
                }
            }
            else {
                sender.sendMessage("Try /chancecubes enableReward <Reward Name>");
            }
        }
        else if (args[0].equalsIgnoreCase("enableReward")) {
            if (args.length > 1) {
                if (ChanceCubeRegistry.INSTANCE.enableReward(args[1])) {
                    sender.sendMessage(args[1] + " Has been enabled.");
                }
                else {
                    sender.sendMessage(args[1] + " is either not currently disabled or is not a valid reward name.");
                }
            }
            else {
                sender.sendMessage("Try /chancecubes disableReward <Reward Name>");
            }
        }
        else if (args[0].equalsIgnoreCase("schematic")) {
            /*if (Minecraft.getMinecraft().isSingleplayer()) {
                if (sender instanceof Player) {
                    World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
                    Player player = (Player) sender;
                    if (player.capabilities.isCreativeMode) {
                        if (args.length >= 3) {
                            if (args[1].equalsIgnoreCase("setPoint")) {
                                if (args[2].equalsIgnoreCase("1")) {
                                    SchematicUtil.selectionPoints[0] = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
                                    sender.sendMessage("Point 1 set");
                                }
                                if (args[2].equalsIgnoreCase("2")) {
                                    SchematicUtil.selectionPoints[1] = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
                                    sender.sendMessage("Point 2 set");
                                }
                            }
                            else if (args[1].equalsIgnoreCase("create")) {
                                if (SchematicUtil.selectionPoints[0] == null || SchematicUtil.selectionPoints[1] == null) {
                                    sender.sendMessage("Both points are not set!");
                                    return false;
                                }
                                SchematicUtil.createCustomSchematic(world, SchematicUtil.selectionPoints[0], SchematicUtil.selectionPoints[1], args[2].endsWith(".ccs") ? args[2] : args[2] + ".ccs");
                                sender.sendMessage("Schematic file named " + (args[2].endsWith(".ccs") ? args[2] : args[2] + ".ccs") + " created!");
                                SchematicUtil.selectionPoints[0] = null;
                                SchematicUtil.selectionPoints[1] = null;
                            }
                        }
                        else {
                            sender.sendMessage("invalid arguments");
                        }
                    }
                    else {
                        sender.sendMessage("Sorry, you need to be in creative to use this command");
                    }
                }
            }
            else {*/
            //TODO need to check to see if this can work in multiplayer
                sender.sendMessage("Sorry, but this command only works in single player");
            //}
        }
        else if (args[0].equalsIgnoreCase("rewardsInfo")) {
            sender.sendMessage("There are currently " + ChanceCubeRegistry.INSTANCE.getNumberOfLoadedRewards() + " rewards loaded and " + ChanceCubeRegistry.INSTANCE.getNumberOfDisabledRewards() + " rewards disabled");
        }
        else if (args[0].equalsIgnoreCase("test")) {
            
        }
        else {
            sender.sendMessage("Invalid arguments for the Chance Cubes command");
        }
        
        return true;
    }

}