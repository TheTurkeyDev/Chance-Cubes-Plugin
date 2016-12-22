package chanceCubes.rewards.rewardparts;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.BlockFallingCustom;
import chanceCubes.config.CCubesSettings;
import chanceCubes.util.RewardsUtil;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.SoundCategory;
import net.minecraft.server.v1_10_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

public class OffsetBlock {

    public static String[] elements = new String[]{"XOffSet:I", "YOffSet:I", "ZOffSet:I", "Block:S", "Falling:B", "delay:I", "RelativeToPlayer:B"};
    public int xOff;
    public int yOff;
    public int zOff;
    protected Block block;
    protected boolean causeUpdate = false;
    protected int delay = 0;
    protected boolean falling;
    protected boolean relativeToPlayer = false;
    protected IBlockData state = null;

    public OffsetBlock(int x, int y, int z, Block b, boolean falling) {
        this.xOff = x;
        this.yOff = y;
        this.zOff = z;
        this.block = b;
        this.falling = falling;
        this.state = b.getBlockData();
    }

    public OffsetBlock(int x, int y, int z, Block b, boolean falling, int delay) {
        this.xOff = x;
        this.yOff = y;
        this.zOff = z;
        this.block = b;
        this.falling = falling;
        this.delay = delay;
        this.state = b.getBlockData();
    }

    public Block getBlock() {
        return this.block;
    }

    public IBlockData getBlockState() {
        return this.state;
    }

    public OffsetBlock setBlockState(IBlockData state) {
        this.state = state;
        return this;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isFalling() {
        return this.falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isRelativeToPlayer() {
        return this.relativeToPlayer;
    }

    public OffsetBlock setRelativeToPlayer(boolean relative) {
        this.relativeToPlayer = relative;
        return this;
    }

    public void placeInWorld(Location location, boolean offset) {
        int xx = location.getBlockX();
        int yy = location.getBlockY();
        int zz = location.getBlockZ();
        if (offset) {
            xx += xOff;
            yy += yOff;
            zz += zOff;
        }

        RewardsUtil.placeBlock(location.getBlock().getState());
        Sound.valueOf("BLOCK_" + new Location(location.getWorld(), xx, yy - 1, zz).getBlock().getType().toString() + "_PLACE");
        net.minecraft.server.v1_10_R1.World world = ((CraftWorld) location.getWorld()).getHandle();
        Chunk chunk = location.getChunk();
        Block bSurface = new net.minecraft.server.v1_10_R1.Chunk(world, chunk.getX(), chunk.getZ()).getBlockData(new BlockPosition(xx, yy - 1, zz)).getBlock();
        world.a(null, (double) ((float) xx + 0.5F), (double) ((float) yy + 0.5F), (double) ((float) zz + 0.5F), bSurface.w().e(), SoundCategory.BLOCKS, (bSurface.w().a() + 1.0F) / 2.0F, bSurface.w().a() * 0.5F);
    }

    public OffsetBlock setCausesBlockUpdate(boolean flag) {
        this.causeUpdate = flag;
        return this;
    }

    protected void spawnFallingBlock(Location location) {
        double yy = (((double) (location.getBlockY() + yOff + CCubesSettings.dropHeight)) + 0.5) >= 256 ? 255 : (((double) (location.getBlockY() + yOff + CCubesSettings.dropHeight)) + 0.5);
        for (int yyy = (int) yy; yyy >= location.getBlockY() + yOff; yyy--) {
            Location loc = new Location(location.getWorld(), location.getBlockX() + xOff, yyy, location.getBlockZ() + zOff);
            BlockState state = loc.getBlock().getState();
            state.setType(Material.AIR);
            state.getLocation();
            if (RewardsUtil.isBlockUnbreakable(state.getLocation()))
                state.update(true);
        }

        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        BlockFallingCustom entityfallingblock = new BlockFallingCustom(world, ((double) (location.getBlockX() + xOff)) + 0.5, yy, ((double) (location.getBlockZ() + zOff)) + 0.5, this.state, location.getBlockY() + yOff, this);
        world.addEntity(entityfallingblock);
    }

    public void spawnInWorld(Location location) {
        if (!falling) {
            if (delay != 0)
                Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> placeInWorld(location, true));
            else
                placeInWorld(location, true);
        }
        else {
            if (delay != 0)
                Bukkit.getScheduler().scheduleSyncDelayedTask(CCubesCore.instance(), () -> spawnFallingBlock(location));
            else
                spawnFallingBlock(location);
        }
    }
}
