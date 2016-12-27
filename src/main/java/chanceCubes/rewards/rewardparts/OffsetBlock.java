package chanceCubes.rewards.rewardparts;

import chanceCubes.CCubesCore;
import chanceCubes.blocks.BlockFallingCustom;
import chanceCubes.config.CCubesSettings;
import chanceCubes.util.RewardsUtil;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.SoundCategory;
import net.minecraft.server.v1_10_R1.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftMagicNumbers;
import org.bukkit.material.MaterialData;

public class OffsetBlock {

    public static String[] elements = new String[]{"XOffSet:I", "YOffSet:I", "ZOffSet:I", "Block:S", "Falling:B", "delay:I", "RelativeToPlayer:B"};
    public int xOff;
    public int yOff;
    public int zOff;
    protected Material material;
    protected MaterialData materialData;
    protected boolean causeUpdate = false;
    protected int delay = 0;
    protected boolean falling;
    protected boolean relativeToPlayer = false;

    public OffsetBlock(int x, int y, int z, Material m, boolean falling) {
        this(x, y, z, m, new MaterialData(m), falling);
    }

    public OffsetBlock(int x, int y, int z, Material m, MaterialData materialData, boolean falling) {
        this(x, y, z, m, materialData, falling, 0);
    }

    public OffsetBlock(int x, int y, int z, Material m, boolean falling, int delay) {
        this(x, y, z, m, new MaterialData(m), falling, delay);
    }

    public OffsetBlock(int x, int y, int z, Material m, MaterialData materialData, boolean falling, int delay) {
        this.xOff = x;
        this.yOff = y;
        this.zOff = z;
        this.material = m;
        this.falling = falling;
        this.delay = delay;
        this.materialData = materialData;
    }

    public Material getMaterial() {
        return this.material;
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

        RewardsUtil.placeBlock(material, materialData, location);
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
        BlockFallingCustom entityfallingblock = new BlockFallingCustom(world, (location.getX() + xOff) + 0.5, yy, (location.getZ() + zOff) + 0.5, CraftMagicNumbers.getBlock(material).fromLegacyData(materialData.getData()), location.getBlockY() + yOff, this);
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
