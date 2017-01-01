package chanceCubes.util;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.CommandPart;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.ParticlePart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.MinecraftKey;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class RewardsUtil {

    private static Random rand = new Random();

    public static OffsetBlock[] addBlocksLists(OffsetBlock[]... lists) {
        int size = 0;
        for (OffsetBlock[] list : lists)
            size += list.length;

        OffsetBlock[] toReturn = new OffsetBlock[size];

        int i = 0;
        for (OffsetBlock[] list : lists) {
            for (OffsetBlock osb : list) {
                toReturn[i] = osb;
                i++;
            }
        }

        return toReturn;
    }

    public static CommandPart[] executeXCommands(String command, int amount) {
        CommandPart[] toReturn = new CommandPart[amount];
        for (int i = 0; i < amount; i++)
            toReturn[i] = new CommandPart(command);
        return toReturn;
    }

    public static CommandPart[] executeXCommands(String command, int amount, int delay) {
        CommandPart[] toReturn = new CommandPart[amount];
        for (int i = 0; i < amount; i++) {
            CommandPart part = new CommandPart(command);
            part.setDelay(delay);
            toReturn[i] = part;
        }
        return toReturn;
    }

    /**
     * @param xSize
     * @param ySize
     * @param zSize
     * @param material
     * @param xOff
     * @param yOff
     * @param zOff
     * @param falling
     * @param delay
     * @param causesUpdate
     * @param relativeToPlayer
     * @return
     */
    public static OffsetBlock[] fillArea(int xSize, int ySize, int zSize, Material material, int xOff, int yOff, int zOff, boolean falling, int delay, boolean causesUpdate, boolean relativeToPlayer) {
        List<OffsetBlock> toReturn = new ArrayList<>();
        for (int y = 0; y < ySize; y++)
            for (int z = 0; z < zSize; z++)
                for (int x = 0; x < xSize; x++)
                    toReturn.add(new OffsetBlock(x + xOff, y + yOff, z + zOff, material, falling, delay).setCausesBlockUpdate(causesUpdate).setRelativeToPlayer(relativeToPlayer));

        return toReturn.toArray(new OffsetBlock[toReturn.size()]);
    }

    public static net.minecraft.server.v1_10_R1.Block getBlock(String mod, String blockName) {
        return net.minecraft.server.v1_10_R1.Block.REGISTRY.get(new MinecraftKey(mod, blockName));
    }

    public static ItemStack getItemStack(String mod, String itemName, int size) {
        return getItemStack(mod, itemName, size, 0);
    }

    public static ItemStack getItemStack(String mod, String itemName, int size, int meta) {
        Item item = Item.REGISTRY.get(new MinecraftKey(mod, itemName));
        return item == null ? null : CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_10_R1.ItemStack(item, size, meta));
    }

    public static net.minecraft.server.v1_10_R1.Block getRandomBlock() {
        int size = net.minecraft.server.v1_10_R1.Block.REGISTRY.keySet().size();
        int randomblock = rand.nextInt(size);
        net.minecraft.server.v1_10_R1.Block b = net.minecraft.server.v1_10_R1.Block.REGISTRY.getId(randomblock);
        int iteration = 0;
        while (b == null) {
            iteration++;
            randomblock = rand.nextInt(size);
            if (iteration > 100)
                b = net.minecraft.server.v1_10_R1.Blocks.COBBLESTONE;
            else
                b = net.minecraft.server.v1_10_R1.Block.REGISTRY.getId(randomblock);
        }
        return b;
    }

    public static Material getRandomFluid() {
        return Arrays.asList(Material.STATIONARY_LAVA, Material.STATIONARY_WATER).get(rand.nextInt(2));
    }

    public static Material getRandomItem() {
        Material material = Material.values()[256 + rand.nextInt(166)];
        while (material == null)
            material = Material.values()[256 + rand.nextInt(166)];

        return material;
    }

    public static Material getRandomOre() {
        List<Material> ores = Arrays.asList(Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GOLD_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.QUARTZ_ORE, Material.REDSTONE_ORE);
        return ores.get(rand.nextInt(ores.size()));
    }

    //TODO OreDictionary and Fluid Registry not a thing in Vanilla
    /*public static String getRandomOreDict() {
        return RewardsUtil.getOreDicts().get(rand.nextInt(RewardsUtil.getOreDicts().size()));
    }*/

    public static ItemStack getSpawnEggForMob(String entity) {
        net.minecraft.server.v1_10_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(new ItemStack(Material.MONSTER_EGGS));
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entity);
        tag.set("EntityTag", entityTag);
        nmsStack.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static void initData() {
        //TODO OreDictionary and Fluid Registry not a thing in Vanilla
        /*oredicts.add("oreGold");
        oredicts.add("oreIron");
        oredicts.add("oreLapis");
        oredicts.add("oreDiamond");
        oredicts.add("oreRedstone");
        oredicts.add("oreEmerald");
        oredicts.add("oreQuartz");
        oredicts.add("oreCoal");

        for (String oreDict : possibleModOres)
            if (OreDictionary.doesOreNameExist(oreDict))
                oredicts.add(oreDict);

        for (String s : FluidRegistry.getRegisteredFluids().keySet())
            fluids.add(s);*/
    }

    public static boolean isBlockUnbreakable(Location location) {
        net.minecraft.server.v1_10_R1.World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        return world.c(pos).b(world, pos) == -1;
    }

    //TODO need to create a method that id's blocks as chance blocks
    public static boolean placeBlock(Material material, Location location) {
        return placeBlock(material, new MaterialData(material), location);
    }

    public static boolean placeBlock(Material material, MaterialData data, Location location) {
        if (!material.isBlock())
            return false;

        BlockState b = location.getBlock().getState();
        b.setType(material);
        b.setData(data);
        return !RewardsUtil.isBlockUnbreakable(b.getLocation()) && b.update(true);
    }

    public static void sendMessageToAllPlayers(World world, String message) {
        world.getPlayers().forEach(player -> player.sendMessage(message));
    }

    public static void sendMessageToNearPlayers(Location location, int distance, String message) {
        location.getWorld().getPlayers().forEach(player -> {
            double dist = Math.sqrt(Math.pow(location.getBlockX() - player.getLocation().getBlockX(), 2) + Math.pow(location.getBlockY() - player.getLocation().getBlockY(), 2) + Math.pow(location.getBlockZ() - player.getLocation().getBlockZ(), 2));
            if (dist <= distance)
                player.sendMessage(message);
        });
    }

    public static EntityPart[] spawnXEntities(NBTTagCompound entityNbt, int amount) {
        EntityPart[] toReturn = new EntityPart[amount];
        for (int i = 0; i < amount; i++)
            toReturn[i] = new EntityPart(entityNbt);

        return toReturn;
    }

    public static ParticlePart[] spawnXParticles(Particle particle, int amount) {
        ParticlePart[] toReturn = new ParticlePart[amount];
        for (int i = 0; i < amount; i++)
            toReturn[i] = new ParticlePart(particle);
        return toReturn;
    }

    public static int scheduleTask(Runnable runnable, int delay) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), runnable, delay);
    }
}
