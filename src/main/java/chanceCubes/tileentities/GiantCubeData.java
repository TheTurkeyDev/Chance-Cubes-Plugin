package chanceCubes.tileentities;

import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.items.CCubesItems;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import static org.apache.commons.lang.Validate.isTrue;

public class GiantCubeData implements ConfigurationSerializable
{
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final int minX;
    private final int minY;
    private final int minZ;
    private final String worldName;
    
    public GiantCubeData(Map<String, Object> map) {
        isTrue(map.containsKey("maxX"), "GiantCubeData missing maxX coordinate.");
        isTrue(map.containsKey("maxY"), "GiantCubeData missing maxY coordinate.");
        isTrue(map.containsKey("maxZ"), "GiantCubeData missing maxZ coordinate.");
        isTrue(map.containsKey("minX"), "GiantCubeData missing minX coordinate.");
        isTrue(map.containsKey("minY"), "GiantCubeData missing minY coordinate.");
        isTrue(map.containsKey("minZ"), "GiantCubeData missing minZ coordinate.");
        isTrue(map.containsKey("world"), "GiantCubeData missing world");
        maxX = (int) map.get("maxX");
        maxY = (int) map.get("maxY");
        maxZ = (int) map.get("maxZ");
        minX = (int) map.get("minX");
        minY = (int) map.get("minY");
        minZ = (int) map.get("minZ");
        worldName = map.get("world").toString();
    }
    
    public GiantCubeData(Location location) {
        minX = location.getBlockX() - 1;
        minY = location.getBlockY();
        minZ = location.getBlockZ() - 1;
        maxX = location.getBlockX() + 2;
        maxY = location.getBlockY() + 3;
        maxZ = location.getBlockZ() + 2;
        worldName = location.getWorld().getName();

        processBlocks(loc -> {
            loc.getBlock().setType(CCubesItems.giantChanceCube.getType());
            CCubesBlocks.addGiantChanceCube(this);
        });
    }

    public void removeStructure() {
        processBlocks(location -> location.getBlock().setType(Material.AIR));
    }

    private void processBlocks(Consumer<Location> consumer) {
        for (int x = minX; x < maxX; x++)
            for (int y = minY; y < maxY; y++)
                for (int z = minZ; z < maxZ; z++)
                    consumer.accept(new Location(Bukkit.getWorld(worldName), x, y, z));
    }

    public boolean isInRegion(Location location) {
        return worldName.equals(location.getWorld().getName()) && location.getX() > minX && location.getX() < maxX && location.getX() > minY && location.getX() < maxY && location.getZ() > minX && location.getZ() < maxZ;
    }

    public boolean validateStructure() {
        for (int x = minX; x < maxX; x++)
            for (int y = minY; y < maxY; y++)
                for (int z = minZ; z < maxZ; z++)
                    if (new Location(Bukkit.getWorld(worldName), x, y, z).getBlock().getType() != CCubesItems.giantChanceCube.getType())
                        return false;
        return true;
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("maxX", maxX);
        map.put("maxY", maxY);
        map.put("maxZ", maxZ);
        map.put("minX", minX);
        map.put("minY", minY);
        map.put("minZ", minZ);
        map.put("world", worldName);
        return map;
    }
}