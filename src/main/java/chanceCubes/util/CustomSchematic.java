package chanceCubes.util;

import chanceCubes.rewards.rewardparts.OffsetBlock;
import java.util.List;

public class CustomSchematic {

    private List<OffsetBlock> blocks;
    private float delay;
    private boolean includeAirBlocks;
    private boolean relativeToPlayer;
    private int xSize;
    private int ySize;
    private int zSize;

    public CustomSchematic(List<OffsetBlock> blocks, int xSize, int ySize, int zSize, boolean relativeToPlayer, boolean includeAirBlocks, float delay) {
        this.blocks = blocks;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        this.relativeToPlayer = relativeToPlayer;
        this.includeAirBlocks = includeAirBlocks;
        this.delay = delay;
    }

    public List<OffsetBlock> getBlocks() {
        return this.blocks;
    }

    public int getXSize() {
        return this.xSize;
    }

    public int getYSize() {
        return this.ySize;
    }

    public int getZSize() {
        return this.zSize;
    }

    public float getdelay() {
        return this.delay;
    }

    public boolean includeAirBlocks() {
        return this.includeAirBlocks;
    }

    public boolean isRelativeToPlayer() {
        return relativeToPlayer;
    }
}