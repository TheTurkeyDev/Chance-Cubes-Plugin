package chanceCubes.blocks;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.BlockFalling;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.EntityFallingBlock;
import net.minecraft.server.v1_10_R1.EnumDirection;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.ITileEntity;
import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.NBTBase;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;

public class BlockFallingCustom extends EntityFallingBlock {

    private IBlockData fallTile;
    private int normY;
    private OffsetBlock osb;

    public BlockFallingCustom(World world, double x, double y, double z, IBlockData state, int normY, OffsetBlock osb) {
        super(world, x, y, z, state);
        fallTile = state;
        this.normY = normY;
        this.osb = osb;
        ticksLived = 1;
    }

    @Override
    public void m() {
        Block block = fallTile.getBlock();

        if (fallTile.getBlock() == Blocks.AIR) {
            die();
        }
        else {
            lastX = locX;
            lastY = locY;
            lastZ = locZ;

            if (ticksLived++ == 0) {
                BlockPosition blockpos = new BlockPosition(this);
                if (world.c(blockpos).getBlock() == block) {
                    world.setAir(blockpos);
                }
            }

            motY -= 0.04D;
            move(motX, motY, motZ);
            motX *= 0.98D;
            motY *= 0.98D;
            motZ *= 0.98D;

            BlockPosition blockpos = new BlockPosition(this);
            if (onGround) {
                IBlockData iBlockData = world.c(blockpos);

                motX *= 0.7D;
                motZ *= 0.7D;
                motY *= -0.5D;

                if (iBlockData.getBlock() != Blocks.PISTON_EXTENSION) {
                    die();
                    if (world.a(block, blockpos, true, EnumDirection.UP, null, null) && !BlockFalling.i(world.c(blockpos.down())) && world.setTypeAndData(blockpos, fallTile, 3)) {
                        if (block instanceof BlockFalling)
                            osb.placeInWorld(new Location(world.getWorld(), blockpos.getX(), blockpos.getY(), blockpos.getZ()), false);

                        if (tileEntityData != null && block instanceof ITileEntity) {
                            TileEntity tileentity = world.getTileEntity(blockpos);

                            if (tileentity != null) {
                                NBTTagCompound nbttagcompound = new NBTTagCompound();
                                tileentity.save(nbttagcompound);

                                for (String s : tileEntityData.c()) {
                                    NBTBase nbtbase = tileEntityData.get(s);

                                    if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
                                        nbttagcompound.set(s, nbtbase.clone());
                                }

                                tileentity.a(nbttagcompound);
                                tileentity.update();
                            }
                        }
                    }
                    else if (dropItem && world.getGameRules().getBoolean("doEntityDrops"))
                        a(new ItemStack(block, 1, block.getDropData(fallTile)), 0.0F);
                }
            }
            else if (ticksLived > 100 && (blockpos.getY() < 1 || blockpos.getY() > 256) || ticksLived > 600) {
                if (dropItem && world.getGameRules().getBoolean("doEntityDrops"))
                    a(new ItemStack(block, 1, block.getDropData(fallTile)), 0.0F);

                die();
            }
            else if (normY == blockpos.getY() || motY == 0) {
                die();
                osb.placeInWorld(new Location(world.getWorld(), blockpos.getX(), blockpos.getY(), blockpos.getZ()), false);
            }
        }
    }
}
