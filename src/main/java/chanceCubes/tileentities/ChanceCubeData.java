package chanceCubes.tileentities;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ChanceCubeData implements ConfigurationSerializable
{
    private static Random random = new Random();
    private final Location location;
    private int chance;
    private boolean isScanned = false;

    public ChanceCubeData(Map<String, Object> map) {
        chance = (int) map.getOrDefault("chance", Math.round((float) (random.nextGaussian() * 40)));
        isScanned = (boolean) map.getOrDefault("isScanned", false);
        Validate.notNull(map.get("location"), "Location is missing from this map.");
        location = Location.deserialize((Map<String, Object>) map.get("location"));
    }

    public ChanceCubeData(Location location) {
        this(location, Math.round((float) (random.nextGaussian() * 40)));
    }

    public ChanceCubeData(Location location, int initialChance) {
        while (initialChance > 100 || initialChance < -100)
            initialChance = Math.round((float) (random.nextGaussian() * 40));

        chance = initialChance;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public int getChance() {
        return this.chance;
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("chance", chance);
        map.put("isScanned", isScanned);
        map.put("location", location.serialize());
        return map;
    }

    public void setChance(int newChance) {
        this.chance = newChance;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }
}
