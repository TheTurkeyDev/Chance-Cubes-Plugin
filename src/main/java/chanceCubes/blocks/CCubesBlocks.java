package chanceCubes.blocks;

import chanceCubes.CCubesCore;
import chanceCubes.items.CCubesItems;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.tileentities.ChanceCubeData;
import chanceCubes.tileentities.ChanceD20Data;
import chanceCubes.tileentities.GiantCubeData;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CCubesBlocks
{
	private static List<ChanceCubeData> chanceCubes = new ArrayList<>();
	private static List<ChanceD20Data> chanceD20s = new ArrayList<>();
	private static List<GiantCubeData> giantChanceCubes = new ArrayList<>();

	private CCubesBlocks() {

	}

	public static void triggerChanceBlock(Location location, Player player) {
		chanceCubes.stream().filter(data -> doLocationsMatch(location, data.getLocation())).forEach(data -> ChanceCubeRegistry.INSTANCE.triggerRandomReward(location, player, data.getChance()));
		update();
	}

	public static void triggerGiantChanceCube(Location location, Player player) {
		giantChanceCubes.stream().filter(data -> data.isInRegion(location)).forEach(data -> GiantCubeRegistry.INSTANCE.triggerRandomReward(location, player, 0));
		update();
	}

	public static void triggerD20(Location location, Player player) {
		chanceD20s.stream().filter(data -> doLocationsMatch(location, data.getLocation())).forEach(data -> ChanceCubeRegistry.INSTANCE.triggerRandomReward(location, player, data.getChance()));
		update();
	}

	public static void addChanceCube(ChanceCubeData chanceCubeData) {
		chanceCubes.add(chanceCubeData);
	}

	public static void addChanceD20(ChanceD20Data chanceD20Data) {
		chanceD20s.add(chanceD20Data);
	}

	public static void addGiantChanceCube(GiantCubeData giantCubeData) {
		giantChanceCubes.add(giantCubeData);
	}

	public static void load() {
		File file = new File(CCubesCore.instance().getDataFolder(), "chance_cubes.yml");
		if (!file.exists()) {
			try
			{
				file.createNewFile();
			}
			catch(IOException e)
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, "An error occurred while creating chance_cubes.yml");
				return;
			}
		}

		YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
		if (yaml.isSet("chanceCubes"))
		{
			chanceCubes = yaml.getMapList("chanceCubes").stream().map(map ->
			{
				Map<String, Object> m = new HashMap<>();
				map.forEach((key, value) -> m.put(key.toString(), value));
				return m;
			}).map(ChanceCubeData::new).collect(Collectors.toList());
		}

		if (yaml.isSet("giantChanceCubes")) {
			giantChanceCubes = yaml.getMapList("giantChanceCubes").stream().map(map ->
			{
				Map<String, Object> m = new HashMap<>();
				map.forEach((key, value) -> m.put(key.toString(), value));
				return m;
			}).map(GiantCubeData::new).collect(Collectors.toList());
		}

		if (yaml.isSet("chanceD20s")) {
			chanceD20s = yaml.getMapList("chanceD20s").stream().map(map ->
			{
				Map<String, Object> m = new HashMap<>();
				map.forEach((key, value) -> m.put(key.toString(), value));
				return m;
			}).map(ChanceD20Data::new).collect(Collectors.toList());
		}
	}

	public static void save() {
		File file = new File(CCubesCore.instance().getDataFolder(), "chance_cubes.yml");
		YamlConfiguration yaml = new YamlConfiguration();
		yaml.set("chanceCubes", chanceCubes.stream().map(ChanceCubeData::serialize).collect(Collectors.toList()));
		yaml.set("giantChanceCubes", giantChanceCubes.stream().map(GiantCubeData::serialize).collect(Collectors.toList()));
		yaml.set("chanceD20s", chanceD20s.stream().map(ChanceD20Data::serialize).collect(Collectors.toList()));

		try
		{
			yaml.save(file);
		}
		catch(IOException e)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "An error occurred while saving chance_cubes.yml");
		}
	}

	public static boolean isChanceCube(Block block) {
		update();
		if (block.getType() == CCubesItems.chanceCube.getType() || block.getType() == CCubesItems.chanceIcosahedron.getType() || block.getType() == CCubesItems.giantChanceCube.getType())
		{
			for(ChanceCubeData chanceCube : chanceCubes)
				if(doLocationsMatch(block.getLocation(), chanceCube.getLocation()))
					return true;

			for(GiantCubeData giantChanceCube : giantChanceCubes)
				if(giantChanceCube.isInRegion(block.getLocation()))
					return true;

			for (ChanceD20Data chanceD20 : chanceD20s)
				if (doLocationsMatch(block.getLocation(), chanceD20.getLocation()))
					return true;
		}

		return false;
	}

	private static boolean doLocationsMatch(Location location1, Location location2) {
		if (location1.getWorld().getName().equals(location2.getWorld().getName()))
			if(location1.getBlockX() == location2.getBlockX())
				if(location1.getBlockY() == location2.getBlockY())
					if(location1.getBlockZ() == location2.getBlockZ())
						return true;

		return false;
	}

	private static void update() {
		chanceCubes = chanceCubes.stream().filter(data -> data.getLocation().getBlock().getType() == CCubesItems.chanceCube.getType()).collect(Collectors.toList());
		chanceD20s = chanceD20s.stream().filter(data -> data.getLocation().getBlock().getType() == CCubesItems.chanceCube.getType()).collect(Collectors.toList());
		giantChanceCubes = giantChanceCubes.stream().filter(data -> {
			boolean isValid = data.validateStructure();
			if (!isValid)
				data.removeStructure();

			return isValid;
		}).collect(Collectors.toList());
	}
}
