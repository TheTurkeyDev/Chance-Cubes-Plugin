package chanceCubes.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;

public class MazeGenerator {

    private final int nonWall = 0;
    private final int wall = 1;
    public Location endBlockWorldCords;
    private Map<Location, Material> blockStorage = new HashMap<>();
    private int currentX = 1;
    private int currentY = 1;
    private Location2I endBlock;
    private int height;
    private int[][] map;
    private Random r = new Random();
    private Map<Location, NBTTagCompound> tileStorage = new HashMap<>();
    private ArrayList<Location2I> walls = new ArrayList<>();
    private int width;

    private boolean checkwalls(Location2I loc) {
        Location2I north = loc.add(0, -1);
        Location2I east = loc.add(1, 0);
        Location2I south = loc.add(0, 1);
        Location2I west = loc.add(-1, 0);

        int yes = 0;
        if (north.getY() >= 0 && map[north.getX()][north.getY()] == nonWall)
            yes++;
        if (east.getX() < width && map[east.getX()][east.getY()] == nonWall)
            yes++;
        if (south.getY() < height && map[south.getX()][south.getY()] == nonWall)
            yes++;
        if (west.getX() >= 0 && map[west.getX()][west.getY()] == nonWall)
            yes++;
        return yes > 1;
    }

    public void endMaze() {
        blockStorage.forEach(((location, material) -> {
            BlockState blockState = location.getBlock().getState();
            blockState.setType(material);
            blockState.update(true);
        }));

        tileStorage.forEach(((location, nbtTagCompound) -> {
            World world = ((CraftWorld) location.getWorld()).getHandle();
            world.setTileEntity(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), TileEntity.a(world, nbtTagCompound));
        }));
    }

    /**
     * @param location
     * @param width
     * @param height
     */
    public void generate(Location location, int width, int height) {
        this.width = width;
        this.height = height;
        map = new int[width][height];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                map[x][y] = wall;

        map[1][1] = nonWall;
        currentX = 1;
        currentY = 1;
        Location2I current = new Location2I(currentX, currentY);
        Location2I north = current.add(0, -1);
        Location2I east = current.add(1, 0);
        Location2I south = current.add(0, 1);
        Location2I west = current.add(-1, 0);

        if ((north.getY() > 0) && (map[north.getX()][north.getY()] == wall)) {
            if (map[north.getX()][north.getY() - 1] == wall)
                walls.add(north);
        }
        if ((east.getX() < width) && (map[east.getX()][east.getY()] == wall)) {
            if (map[east.getX() + 1][east.getY()] == wall)
                walls.add(east);
        }
        if ((south.getY() < height) && (map[south.getX()][south.getY()] == wall)) {
            if (map[south.getX()][south.getY() + 1] == wall)
                walls.add(south);
        }
        if ((west.getX() > 0) && (map[west.getX()][west.getY()] == wall)) {
            if (map[west.getX() - 1][west.getY()] == wall)
                walls.add(west);
        }

        int randomLoc = 0;
        while (walls.size() > 0) {
            randomLoc = r.nextInt(walls.size());
            currentX = walls.get(randomLoc).getX();
            currentY = walls.get(randomLoc).getY();
            current.setXY(currentX, currentY);
            north = current.add(0, -1);
            east = current.add(1, 0);
            south = current.add(0, 1);
            west = current.add(-1, 0);

            if (!checkwalls(current)) {
                map[currentX][currentY] = nonWall;
                walls.remove(randomLoc);

                if ((north.getY() - 1 > 0) && (map[north.getX()][north.getY()] == wall)) {
                    if (map[north.getX()][north.getY() - 1] == wall)
                        walls.add(north);
                }
                if ((east.getX() + 1 < width) && (map[east.getX()][east.getY()] == wall)) {
                    if (map[east.getX() + 1][east.getY()] == wall)
                        walls.add(east);
                }
                if ((south.getY() + 1 < height) && (map[south.getX()][south.getY()] == wall)) {
                    if (map[south.getX()][south.getY() + 1] == wall)
                        walls.add(south);
                }
                if ((west.getX() - 1 > 0) && (map[west.getX()][west.getY()] == wall)) {
                    if (map[west.getX() - 1][west.getY()] == wall)
                        walls.add(west);
                }
            }
            else {
                walls.remove(randomLoc);
            }
        }
        int endBlockX = width - 1;
        int endBlockZ = height - 1;
        boolean run = true;
        int i = 0;
        int xx = 0;
        int zz = 0;
        while (run) {
            for (xx = 0; xx <= i; xx++) {
                for (zz = i; zz >= 0; zz--) {
                    if (this.map[endBlockX - xx][endBlockZ - zz] == this.nonWall && run) {
                        endBlock = new Location2I(endBlockX - xx, endBlockZ - zz);
                        run = false;
                    }
                }
            }
            i++;
        }

        placeBlocks(location);
    }

    private void placeBlocks(Location location) {
        int xoff = (location.getBlockX() - (this.width / 2));
        int zoff = (location.getBlockZ() - (this.height / 2));

        TileEntity temp;
        for (int xx = 0; xx < this.width; xx++) {
            for (int zz = 0; zz < this.height; zz++) {
                if (this.map[xx][zz] == 0) {
                    for (int yy = -1; yy < 3; yy++) {
                        Location tempLoc = new Location(location.getWorld(), xoff + xx, location.getBlockY() + yy, zoff + zz);
                        blockStorage.put(tempLoc, tempLoc.getBlock().getType());
                        temp = ((CraftWorld) location.getWorld()).getHandle().getTileEntity(new BlockPosition(xoff + xx, location.getBlockY() + yy, zoff + zz));
                        if (temp != null)
                            tileStorage.put(tempLoc, temp.c());
                    }

                    BlockState state = new Location(location.getWorld(), xoff + xx, location.getBlockY() - 1, zoff + zz).getBlock().getState();
                    state.setType(Material.BEDROCK);
                    state.update(true);
                    state = new Location(location.getWorld(), xoff + xx, location.getBlockY(), zoff + zz).getBlock().getState();
                    state.setType(Material.TORCH);
                    state.update(true);
                    state = new Location(location.getWorld(), xoff + xx, location.getBlockY() + 1, zoff + zz).getBlock().getState();
                    state.setType(Material.AIR);
                    state.update(true);
                    state = new Location(location.getWorld(), xoff + xx, location.getBlockY() + 2, zoff + zz).getBlock().getState();
                    state.setType(Material.BEDROCK);
                    state.update(true);
                }
                else {
                    for (int yy = -1; yy < 3; yy++) {
                        Location tempLoc = new Location(location.getWorld(), xoff + xx, location.getBlockY() + yy, zoff + zz);
                        blockStorage.put(tempLoc, tempLoc.getBlock().getType());
                        temp = ((CraftWorld) location.getWorld()).getHandle().getTileEntity(new BlockPosition(xoff + xx, location.getBlockY() + yy, zoff + zz));
                        if (temp != null) {
                            this.tileStorage.put(tempLoc, temp.c());
                        }
                    }

                    BlockState state = new Location(location.getWorld(), xoff + xx, location.getBlockY() - 1, zoff + zz).getBlock().getState();
                    state.setType(Material.AIR);
                    state.update(true);
                    state = new Location(location.getWorld(), xoff + xx, location.getBlockY(), zoff + zz).getBlock().getState();
                    state.setType(Material.BEDROCK);
                    state.update(true);
                    state = new Location(location.getWorld(), xoff + xx, location.getBlockY() + 1, zoff + zz).getBlock().getState();
                    state.setType(Material.BEDROCK);
                    state.update(true);
                    state = new Location(location.getWorld(), xoff + xx, location.getBlockY() + 2, zoff + zz).getBlock().getState();
                    state.setType(Material.AIR);
                    state.update(true);
                }
            }
        }

        endBlockWorldCords = new Location(location.getWorld(), xoff + this.endBlock.getX(), location.getBlockY(), zoff + this.endBlock.getY());
        BlockState state = endBlockWorldCords.getBlock().getState();
        state.setType(Material.SIGN_POST);
        state.update(true);
        if (state instanceof Sign) {
            Sign sign = (Sign) state;
            sign.setLine(0, "Break me");
            sign.setLine(1, "To beat the");
            sign.setLine(2, "Maze");
        }
    }
}