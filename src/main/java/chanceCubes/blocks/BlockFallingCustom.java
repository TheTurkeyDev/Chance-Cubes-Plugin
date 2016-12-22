package chanceCubes.blocks;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.EntityFallingBlock;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.World;

public class BlockFallingCustom extends EntityFallingBlock {

    private IBlockData fallTile;
    private int normY;
    private OffsetBlock osb;

    public BlockFallingCustom(World world, double x, double y, double z, IBlockData state, int normY, OffsetBlock osb) {
        super(world, x, y, z, state);
        fallTile = state;
        this.normY = normY;
        this.osb = osb;
    }

    public void onUpdate() {
        Block block = this.fallTile.getBlock();

        if (this.fallTile.getBlock() == Blocks.AIR) {
            this.die();
        }
        else {
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;

            if (this.ticksLived++ == 0) {
                BlockPosition blockpos = new BlockPosition(this);
                
                if (this.world.c(blockpos).getBlock() == block) {
                    this.world.setAir(blockpos);
                }
                //TODO unneeded check, may delete in the future
                else if (this.world.isClientSide) {
                    this.die();
                    return;
                }
            }

            this.motY -= 0.04D;
            this.move(this.motX, this.motY, this.motZ);
            this.motX *= 0.98D;
            this.motY *= 0.98D;
            this.motZ *= 0.98D;
            
            //TODO unneeded check, may delete in the future
            if (!this.world.isClientSide) {
                /*BlockPosition blockpos1 = new BlockPosition(this);

                if (this.onGround) {
                    IBlockState iblockstate = this.worldObj.getBlockState(blockpos1);

                    this.motionX *= 0.7D;
                    this.motionZ *= 0.7D;
                    this.motionY *= -0.5D;

                    if (iblockstate.getBlock() != Blocks.PISTON_EXTENSION) {
                        this.setDead();
                        if (this.worldObj.canBlockBePlaced(block, blockpos1, true, EnumFacing.UP, (Entity) null, (ItemStack) null) && !BlockFalling.canFallThrough(this.worldObj.getBlockState(blockpos1.down())) && this.worldObj.setBlockState(blockpos1, this.fallTile, 3)) {
                            if (block instanceof BlockFalling)
                                osb.placeInWorld(worldObj, blockpos1, false);

                            if (this.tileEntityData != null && block instanceof ITileEntityProvider) {
                                TileEntity tileentity = this.worldObj.getTileEntity(blockpos1);

                                if (tileentity != null) {
                                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                                    tileentity.writeToNBT(nbttagcompound);

                                    for (String s : this.tileEntityData.getKeySet()) {
                                        NBTBase nbtbase = this.tileEntityData.getTag(s);

                                        if (!s.equals("x") && !s.equals("y") && !s.equals("z"))
                                            nbttagcompound.setTag(s, nbtbase.copy());
                                    }

                                    tileentity.readFromNBT(nbttagcompound);
                                    tileentity.markDirty();
                                }
                            }
                        }
                        else if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                            this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);
                        }
                    }
                }
                else if (this.ticksLived > 100 && !this.worldObj.isRemote && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600) {
                    if (this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops"))
                        this.entityDropItem(new ItemStack(block, 1, block.damageDropped(this.fallTile)), 0.0F);

                    this.setDead();
                }
                else if (normY == blockpos1.getY() || this.motionY == 0) {
                    this.setDead();
                    osb.placeInWorld(worldObj, blockpos1, false);
                }*/
            }
        }
    }
}
