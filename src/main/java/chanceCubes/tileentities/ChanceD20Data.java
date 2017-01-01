package chanceCubes.tileentities;

import chanceCubes.config.CCubesSettings;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class ChanceD20Data implements ConfigurationSerializable
{
    private static final Random random = new Random();
    private final Location location;
    private int chance;
    private boolean isScanned = false;

    public ChanceD20Data(Map<String, Object> map) {
        chance = (int) map.getOrDefault("chance", generateChance());
        location = Location.deserialize((Map<String, Object>) Validate.notNull(map.get("location"), "Chance20Data is missing location."));
    }

    public ChanceD20Data(Location location) {
        this(location, generateChance());
    }

    private static int generateChance() {
        if (!CCubesSettings.d20UseNormalChances) {
            return random.nextBoolean() ? -100 : 100;
        }
        else {
            int chance = Math.round((float) (random.nextGaussian() * 40));
            while (chance > 100 || chance < -100)
                chance = Math.round((float) (random.nextGaussian() * 40));

            return chance;
        }
    }

    public ChanceD20Data(Location location, int initialChance) {
        if (initialChance > 100 || initialChance < -100)
            initialChance = generateChance();

        this.chance = initialChance;
        this.location = location;
    }

    public int getChance() {
        return this.chance;
    }

    public Location getLocation()
    {
        return location;
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
