package chanceCubes.rewards.rewardparts;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.material.MaterialData;

public class OffsetTileEntity extends OffsetBlock {

    private NBTTagCompound teNBT;

    public OffsetTileEntity(int x, int y, int z, Material material, MaterialData materialData, NBTTagCompound te, boolean falling) {
        this(x, y, z, material, materialData, te, falling, 0);
    }

	public OffsetTileEntity(int x, int y, int z, Material material, NBTTagCompound te, boolean falling, int delay) {
		this(x, y, z, material, new MaterialData(material), te, falling, delay);
	}

    public OffsetTileEntity(int x, int y, int z, Material material, MaterialData materialData, NBTTagCompound te, boolean falling, int delay) {
        super(x, y, z, material, materialData, falling, delay);
        this.teNBT = te;
    }

    @Override
    public void placeInWorld(Location location, boolean offset) {
        super.placeInWorld(location, offset);
        World world = ((CraftWorld) location.getWorld()).getHandle();
        if (offset)
            world.setTileEntity(new BlockPosition(location.getBlockX() + xOff, location.getBlockY() + yOff, location.getBlockZ() + zOff), TileEntity.a(world, teNBT));
        else
            world.setTileEntity(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), TileEntity.a(world, teNBT));
    }
}
