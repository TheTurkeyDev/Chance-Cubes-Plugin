package chanceCubes.rewards.rewardparts;

import net.minecraft.server.v1_10_R1.MojangsonParseException;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class EntityPart {

    public static String[] elements = new String[]{"entity:S", "delay:I"};
    private int delay = 0;
    private NBTTagCompound nbtData;
    private boolean removedBlocks = true;

    public EntityPart(NBTTagCompound nbtData) {
        this.nbtData = nbtData;
    }

    public EntityPart(String nbtRaw) {
        try {
            this.nbtData = MojangsonParser.parse(nbtRaw);
        }
        catch (MojangsonParseException e) {
            e.printStackTrace();
        }
    }

    public int getDelay() {
        return delay;
    }

    public EntityPart setDelay(int delay) {
        this.delay = delay;
        return this;
    }

    public NBTTagCompound getNBT() {
        return nbtData;
    }

    public EntityPart setRemovedBlocks(boolean removedBlocks) {
        this.removedBlocks = removedBlocks;
        return this;
    }

    public boolean shouldRemovedBlocks() {
        return removedBlocks;
    }
}
