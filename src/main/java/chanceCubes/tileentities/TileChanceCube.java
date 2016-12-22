package chanceCubes.tileentities;

import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileChanceCube extends TileEntity {

    private static Random random = new Random();

    private int chance;
    private boolean isScanned = false;

    public TileChanceCube() {
        this(Math.round((float) (random.nextGaussian() * 40)));
    }

    public TileChanceCube(int initialChance) {
        while (initialChance > 100 || initialChance < -100)
            initialChance = Math.round((float) (random.nextGaussian() * 40));
        this.setChance(initialChance);
    }

    public int getChance() {
        return this.chance;
    }

    public void setChance(int newChance) {
        this.chance = newChance;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound syncData = new NBTTagCompound();
        syncData = this.writeSyncableDataToNBT(syncData);
        return new SPacketUpdateTileEntity(this.pos, 1, syncData);
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readSyncableDataFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.readSyncableDataFromNBT(nbt);
    }

    private void readSyncableDataFromNBT(NBTTagCompound nbt) {
        this.chance = nbt.getInteger("chance");
    }

    private NBTTagCompound writeSyncableDataToNBT(NBTTagCompound syncData) {
        syncData.setInteger("chance", this.getChance());
        return syncData;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        return this.writeSyncableDataToNBT(super.writeToNBT(nbt));
    }
}
