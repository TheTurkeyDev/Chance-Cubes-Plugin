package chanceCubes.config;

import chanceCubes.CCubesCore;
import chanceCubes.registry.ChanceCubeRegistry;
import chanceCubes.registry.GiantCubeRegistry;
import chanceCubes.rewards.defaultRewards.BasicReward;
import chanceCubes.rewards.rewardparts.ChestChanceItem;
import chanceCubes.rewards.rewardparts.CommandPart;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.rewards.rewardparts.ExperiencePart;
import chanceCubes.rewards.rewardparts.ItemPart;
import chanceCubes.rewards.rewardparts.MessagePart;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.ParticlePart;
import chanceCubes.rewards.rewardparts.PotionPart;
import chanceCubes.rewards.type.BlockRewardType;
import chanceCubes.rewards.type.ChestRewardType;
import chanceCubes.rewards.type.CommandRewardType;
import chanceCubes.rewards.type.EntityRewardType;
import chanceCubes.rewards.type.ExperienceRewardType;
import chanceCubes.rewards.type.IRewardType;
import chanceCubes.rewards.type.ItemRewardType;
import chanceCubes.rewards.type.MessageRewardType;
import chanceCubes.rewards.type.ParticleEffectRewardType;
import chanceCubes.rewards.type.PotionRewardType;
import chanceCubes.rewards.type.SchematicRewardType;
import chanceCubes.util.CustomSchematic;
import chanceCubes.util.HTTPUtil;
import chanceCubes.util.RewardsUtil;
import chanceCubes.util.SchematicUtil;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.MojangsonParseException;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_10_R1.potion.CraftPotionUtil;
import org.bukkit.potion.PotionEffect;

public class CustomRewardsLoader
{
	
	public static CustomRewardsLoader instance;
	private static JsonParser json;
	private File folder;
	
	public CustomRewardsLoader(File folder)
	{
		instance = this;
		this.folder = folder;
		json = new JsonParser();
		
		// Custom Sounds not possible
		/*
		 * CustomSoundsLoader customSounds = new CustomSoundsLoader(folder, new
		 * File(folder.getAbsolutePath() + "/CustomSounds-Resourcepack"),
		 * "Chance Cubes Resource Pack"); customSounds.addCustomSounds();
		 * customSounds.assemble();
		 */
	}
	
	public int compareDates(Calendar first, Calendar second)
	{
		int fm = first.get(Calendar.MONTH);
		int sm = second.get(Calendar.MONTH);
		
		int fd = first.get(Calendar.DAY_OF_MONTH);
		int sd = second.get(Calendar.DAY_OF_MONTH);
		
		if (fm < sm)
			return 1;
		else if (fm == sm)
			return fd == sd ? 0 : fd < sd ? 1 : -1;
		else
			return -2;
	}
	
	public File getFolderFile()
	{
		return this.folder;
	}
	
	public List<String> getReward(String file, String rewardName)
	{
		List<String> rewardinfo = Lists.newArrayList();
		
		File rewardsFile = new File(this.folder.getPath() + "\\" + file);
		JsonElement fileJson;
		
		try
		{
			fileJson = json.parse(new FileReader(rewardsFile));
		}
		catch (Exception e)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "Unable to parse the file " + rewardsFile.getName() + ". Skipping file loading.");
			return null;
		}
		
		for (Entry<String, JsonElement> reward : fileJson.getAsJsonObject().entrySet())
		{
			JsonObject rewardElements = reward.getValue().getAsJsonObject();
			for (Entry<String, JsonElement> rewardElement : rewardElements.entrySet())
				rewardinfo.add(rewardElement.getKey());
		}
		return rewardinfo;
	}
	
	public List<String> getRewardType(String file, String s, String type)
	{
		List<String> rewardinfo = Lists.newArrayList();
		
		File rewardsFile = new File(this.folder.getPath() + "\\" + file);
		JsonElement fileJson;
		try
		{
			fileJson = json.parse(new FileReader(rewardsFile));
		}
		catch (Exception e)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "Unable to parse the file " + rewardsFile.getName() + ". Skipping file loading.");
			return null;
		}
		
		for (Entry<String, JsonElement> reward : fileJson.getAsJsonObject().entrySet())
		{
			JsonObject rewardElements = reward.getValue().getAsJsonObject();
			for (Entry<String, JsonElement> rewardElement : rewardElements.entrySet())
			{
				if (rewardElement.getKey().equalsIgnoreCase(type))
				{
					if (rewardElement.getValue() instanceof JsonArray)
					{
						JsonArray rewardTypeArray = rewardElement.getValue().getAsJsonArray();
						for (int i = 0; i < rewardTypeArray.size(); i++)
							rewardinfo.add(rewardTypeArray.get(i).toString());
					}
					else
					{
						rewardinfo.add(rewardElement.getValue().toString());
					}
				}
			}
		}
		return rewardinfo;
	}
	
	public List<String> getRewardsFiles()
	{
		List<String> files = Lists.newArrayList();
		for (File f : folder.listFiles())
		{
			if (!f.isFile())
				continue;
			if (f.getName().substring(f.getName().indexOf(".")).equalsIgnoreCase(".json"))
				files.add(f.getName());
		}
		return files;
	}
	
	public List<String> getRewardsFromFile(String file)
	{
		List<String> rewards = Lists.newArrayList();
		
		File rewardsFile = new File(this.folder.getPath() + "\\" + file);
		
		JsonElement fileJson;
		try
		{
			fileJson = json.parse(new FileReader(rewardsFile));
		}
		catch (Exception e)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "Unable to parse the file " + rewardsFile.getName() + ". Skipping file loading.");
			return null;
		}
		
		for (Entry<String, JsonElement> reward : fileJson.getAsJsonObject().entrySet())
			rewards.add(reward.getKey());
		
		return rewards;
	}
	
	public List<IRewardType> loadBlockReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<OffsetBlock> blocks = new ArrayList<>();
		for (JsonElement element : rawReward)
		{
			int x = element.getAsJsonObject().get("XOffSet").getAsInt();
			int y = element.getAsJsonObject().get("YOffSet").getAsInt();
			int z = element.getAsJsonObject().get("ZOffSet").getAsInt();
			String blockDataParts[] = element.getAsJsonObject().get("Block").getAsString().split(":");
			String mod = blockDataParts[0];
			String blockName = blockDataParts[1];
			Block block = RewardsUtil.getBlock(mod, blockName);
			boolean falling = element.getAsJsonObject().get("Falling").getAsBoolean();
			
			OffsetBlock offBlock = new OffsetBlock(x, y, z, block, falling);
			if (element.getAsJsonObject().has("delay"))
				offBlock.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			
			if (element.getAsJsonObject().has("RelativeToPlayer"))
				offBlock.setRelativeToPlayer(element.getAsJsonObject().get("RelativeToPlayer").getAsBoolean());
			
			if (blockDataParts.length > 2)
				offBlock.setBlockState(block.fromLegacyData(Integer.parseInt(blockDataParts[2])));
			
			blocks.add(offBlock);
		}
		rewards.add(new BlockRewardType(blocks.toArray(new OffsetBlock[blocks.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadChestReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<ChestChanceItem> items = Lists.newArrayList();
		for (JsonElement element : rawReward)
		{
			JsonObject obj = element.getAsJsonObject();
			if (obj.has("item") && obj.has("chance"))
			{
				int meta = 0;
				if (obj.has("meta"))
					meta = obj.get("meta").getAsInt();
				
				int amountMin = 1;
				if (obj.has("amountMin"))
					amountMin = obj.get("amountMin").getAsInt();
				
				int amountMax = 8;
				if (obj.has("amountMax"))
					amountMax = obj.get("amountMax").getAsInt();
				
				items.add(new ChestChanceItem(obj.get("item").getAsString(), meta, obj.get("chance").getAsInt(), amountMin, amountMax));
			}
			else
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, "A chest reward failed to load do to missing params");
			}
			
		}
		rewards.add(new ChestRewardType(items.toArray(new ChestChanceItem[items.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadCommandReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<CommandPart> commands = new ArrayList<>();
		for (JsonElement element : rawReward)
		{
			CommandPart command = new CommandPart(element.getAsJsonObject().get("command").getAsString());
			
			if (element.getAsJsonObject().has("delay"))
				command.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			commands.add(command);
		}
		rewards.add(new CommandRewardType(commands.toArray(new CommandPart[commands.size()])));
		return rewards;
	}
	
	public void loadCustomRewards()
	{
		for (File f : folder.listFiles())
		{
			if (!f.isFile() || !f.getName().contains(".json"))
				continue;
			if (f.getName().substring(f.getName().indexOf(".")).equalsIgnoreCase(".json"))
			{
				JsonElement fileJson;
				try
				{
					CCubesCore.instance().getLogger().log(Level.INFO, "Loading custom rewards file " + f.getName());
					fileJson = json.parse(new FileReader(f));
				}
				catch (Exception e)
				{
					CCubesCore.instance().getLogger().log(Level.WARNING, "Unable to parse the file " + f.getName() + ". Skipping file loading.");
					CCubesCore.instance().getLogger().log(Level.WARNING, "Parse Error: " + e.getMessage());
					continue;
				}
				
				for (Entry<String, JsonElement> reward : fileJson.getAsJsonObject().entrySet())
				{
					SimpleEntry<BasicReward, Boolean> parsedReward = this.parseReward(reward);
					BasicReward basicReward = parsedReward.getKey();
					if (basicReward == null)
					{
						CCubesCore.instance().getLogger().log(Level.WARNING, "Seems your reward is setup incorrectly, or is disabled for this version of minecraft with a depedency, and Chance Cubes was not able to parse the reward " + reward.getKey() + " for the file " + f.getName());
						continue;
					}
					if (parsedReward.getValue())
						GiantCubeRegistry.INSTANCE.registerReward(basicReward);
					else
						ChanceCubeRegistry.INSTANCE.registerReward(basicReward);
				}
				
				CCubesCore.instance().getLogger().log(Level.INFO, "Loaded custom rewards file " + f.getName());
			}
		}
	}
	
	public void loadDisabledRewards()
	{
		JsonElement disabledRewards;
		
		try
		{
			disabledRewards = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/DisabledRewards.json");
		}
		catch (Exception e)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "Chance Cubes failed to get the list of disabled rewards!");
			CCubesCore.instance().getLogger().log(Level.WARNING, e.getMessage());
			return;
		}
		
		for (Entry<String, JsonElement> version : disabledRewards.getAsJsonObject().entrySet())
		{
			String versionString = CCubesCore.instance().getDescription().getVersion();
			if (!versionString.equalsIgnoreCase("@VERSION@"))
			{
				if (version.getKey().equalsIgnoreCase(versionString.substring(versionString.indexOf("-") + 1, versionString.lastIndexOf("."))))
				{
					for (JsonElement reward : version.getValue().getAsJsonArray())
					{
						boolean removed = ChanceCubeRegistry.INSTANCE.unregisterReward(reward.getAsString());
						if (!removed)
							removed = GiantCubeRegistry.INSTANCE.unregisterReward(reward.getAsString());
						
						CCubesCore.instance().getLogger().log(Level.WARNING, "The reward " + reward.getAsString() + " has been disabled by the mod author due to a bug or some other reason.");
					}
				}
			}
		}
	}
	
	public List<IRewardType> loadEntityReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<EntityPart> entities = new ArrayList<>();
		for (JsonElement element : rawReward)
		{
			EntityPart ent;
			
			try
			{
				String jsonEdited = this.removedKeyQuotes(element.getAsJsonObject().get("entity").getAsJsonObject().toString());
				NBTTagCompound nbt = MojangsonParser.parse(jsonEdited);
				
				if (nbt == null)
				{
					CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to convert the JSON to NBT for: " + element.toString());
					continue;
				}
				else
				{
					ent = new EntityPart(nbt);
				}
			}
			catch (Exception e)
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, "The Entity loading failed for a custom reward!");
				CCubesCore.instance().getLogger().log(Level.WARNING, "-------------------------------------------");
				e.printStackTrace();
				continue;
			}
			
			if (element.getAsJsonObject().has("delay"))
				ent.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			entities.add(ent);
		}
		rewards.add(new EntityRewardType(entities.toArray(new EntityPart[entities.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadExperienceReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<ExperiencePart> exp = new ArrayList<>();
		for (JsonElement element : rawReward)
		{
			ExperiencePart exppart = new ExperiencePart(element.getAsJsonObject().get("experienceAmount").getAsInt());
			
			if (element.getAsJsonObject().has("delay"))
				exppart.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			if (element.getAsJsonObject().has("numberOfOrbs"))
				exppart.setNumberofOrbs(element.getAsJsonObject().get("numberOfOrbs").getAsInt());
			exp.add(exppart);
		}
		
		rewards.add(new ExperienceRewardType(exp.toArray(new ExperiencePart[exp.size()])));
		return rewards;
	}
	
	public void loadHolidayRewards()
	{
		if (!CCubesSettings.holidayRewards)
			return;
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd");
		Date date = new Date();
		Calendar today = Calendar.getInstance();
		today.setTime(date);
		JsonElement holidays;
		String holidayName = "";
		
		try
		{
			holidays = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/Holidays.json");
		}
		catch (Exception e1)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to fetch the list of holiday rewards!");
			return;
		}
		
		for (JsonElement holiday : holidays.getAsJsonArray())
		{
			Date parsed;
			try
			{
				parsed = dateFormat.parse(holiday.getAsJsonObject().get("Date").getAsString().trim());
			}
			catch (ParseException e)
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to parse a holiday date. BLAME TURKEY!!!");
				continue;
			}
			
			if (dateFormat.format(date).equalsIgnoreCase(dateFormat.format(parsed)))
			{
				holidayName = holiday.getAsJsonObject().get("Name").getAsString();
			}
			
			if (holiday.getAsJsonObject().has("Texture") && !holiday.getAsJsonObject().get("Texture").getAsString().equalsIgnoreCase(""))
			{
				try
				{
					Calendar start = Calendar.getInstance();
					Calendar end = Calendar.getInstance();
					start.setTime(dateFormat.parse(holiday.getAsJsonObject().get("Start").getAsString().trim()));
					end.setTime(dateFormat.parse(holiday.getAsJsonObject().get("End").getAsString().trim()));
					
					if (this.compareDates(start, today) >= 0 && this.compareDates(end, today) <= 0)
					{
						CCubesSettings.hasHolidayTexture = true;
						CCubesSettings.holidayTextureName = holiday.getAsJsonObject().get("Name").getAsString();
					}
				}
				catch (ParseException e)
				{
					CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to parse a holiday date. BLAME TURKEY!!!");
				}
			}
		}
		
		if (holidayName.equalsIgnoreCase(""))
		{
			CCubesSettings.holidayRewardTriggered = false;
			ConfigLoader.save();
			return;
		}
		
		if (!CCubesSettings.holidayRewardTriggered)
		{
			
			JsonElement userRewards;
			
			try
			{
				userRewards = HTTPUtil.getWebFile(CCubesSettings.rewardURL + "/HolidayRewards/" + holidayName + ".json");
			}
			catch (Exception e)
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, "Chance Cubes failed to get the custom reward for the holiday " + holidayName + "!");
				CCubesCore.instance().getLogger().log(Level.WARNING, e.getMessage());
				return;
			}
			
			for (Entry<String, JsonElement> reward : userRewards.getAsJsonObject().entrySet())
			{
				BasicReward basicReward = this.parseReward(reward).getKey();
				if (basicReward == null)
					continue;
				CCubesSettings.doesHolidayRewardTrigger = true;
				CCubesSettings.holidayReward = basicReward;
				CCubesCore.instance().getLogger().log(Level.WARNING, "Custom holiday reward \"" + holidayName + "\" loaded!");
			}
		}
	}
	
	public List<IRewardType> loadItemReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<ItemPart> items = new ArrayList<>();
		for (JsonElement fullElement : rawReward)
		{
			JsonElement element = fullElement.getAsJsonObject().get("item").getAsJsonObject();
			ItemPart stack;
			
			try
			{
				String jsonEdited = this.removedKeyQuotes(element.toString());
				NBTTagCompound nbt = MojangsonParser.parse(jsonEdited);
				
				if (nbt == null)
				{
					CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to convert the JSON to NBT for: " + element.toString());
					continue;
				}
				else
				{
					ItemStack itemstack = ItemStack.createStack(nbt);
					if (itemstack == null)
					{
						CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to create an itemstack from the JSON of: " + jsonEdited + " and the NBT of: " + nbt.toString());
						continue;
					}
					else
						stack = new ItemPart(CraftItemStack.asBukkitCopy(itemstack));
				}
			}
			catch (MojangsonParseException e)
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, e.getMessage());
				continue;
			}
			
			if (fullElement.getAsJsonObject().has("delay"))
				stack.setDelay(fullElement.getAsJsonObject().get("delay").getAsInt());
			
			items.add(stack);
		}
		rewards.add(new ItemRewardType(items.toArray(new ItemPart[items.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadMessageReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<MessagePart> msgs = new ArrayList<>();
		for (JsonElement element : rawReward)
		{
			MessagePart message = new MessagePart(element.getAsJsonObject().get("message").getAsString());
			
			if (element.getAsJsonObject().has("delay"))
				message.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			if (element.getAsJsonObject().has("serverWide"))
				message.setServerWide(element.getAsJsonObject().get("serverWide").getAsBoolean());
			if (element.getAsJsonObject().has("range"))
				message.setRange(element.getAsJsonObject().get("range").getAsInt());
			
			msgs.add(message);
		}
		rewards.add(new MessageRewardType(msgs.toArray(new MessagePart[msgs.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadParticleReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<ParticlePart> particles = new ArrayList<ParticlePart>();
		for (JsonElement element : rawReward)
		{
			
			ParticlePart particle = new ParticlePart(Particle.valueOf(element.getAsJsonObject().get("particle").getAsString().toUpperCase()));
			
			if (element.getAsJsonObject().has("delay"))
				particle.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			
			particles.add(particle);
		}
		rewards.add(new ParticleEffectRewardType(particles.toArray(new ParticlePart[particles.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadPotionReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		List<PotionPart> potionEffects = new ArrayList<>();
		for (JsonElement element : rawReward)
		{
			PotionPart exppart = new PotionPart(new PotionEffect(CraftPotionUtil.toBukkit(element.getAsJsonObject().get("potionid").getAsString()).getType().getEffectType(), element.getAsJsonObject().get("duration").getAsInt() * 20, 1));
			
			if (element.getAsJsonObject().has("delay"))
				exppart.setDelay(element.getAsJsonObject().get("delay").getAsInt());
			
			potionEffects.add(exppart);
		}
		rewards.add(new PotionRewardType(potionEffects.toArray(new PotionPart[potionEffects.size()])));
		return rewards;
	}
	
	public List<IRewardType> loadSchematicReward(JsonArray rawReward, List<IRewardType> rewards)
	{
		rawReward.forEach(element -> {
			JsonObject jsonObject = element.getAsJsonObject();
			String fileName = jsonObject.get("fileName").getAsString();
			int xoff = 0;
			int yoff = 0;
			int zoff = 0;
			float delay = 0;
			boolean falling = true;
			boolean relativeToPlayer = false;
			boolean includeAirBlocks = false;
			if (jsonObject.has("XOffSet"))
				xoff = jsonObject.get("XOffSet").getAsInt();
			
			if (jsonObject.has("YOffSet"))
				yoff = jsonObject.get("YOffSet").getAsInt();
			
			if (jsonObject.has("ZOffSet"))
				zoff = jsonObject.get("ZOffSet").getAsInt();
			
			if (jsonObject.has("delay"))
				delay = jsonObject.get("delay").getAsFloat();
			
			if (jsonObject.has("falling"))
				falling = jsonObject.get("falling").getAsBoolean();
			
			if (jsonObject.has("RelativeToPlayer"))
				relativeToPlayer = jsonObject.get("RelativeToPlayer").getAsBoolean();
			
			if (jsonObject.has("IncludeAirBlocks"))
				includeAirBlocks = jsonObject.get("IncludeAirBlocks").getAsBoolean();
			
			CustomSchematic schematic = null;
			if (fileName.endsWith(".ccs"))
				schematic = SchematicUtil.loadCustomSchematic(fileName, xoff, yoff, zoff, delay, falling, relativeToPlayer, includeAirBlocks);
			else if (fileName.endsWith(".schematic"))
				schematic = SchematicUtil.loadLegacySchematic(fileName, xoff, yoff, zoff, delay, falling, relativeToPlayer, includeAirBlocks);
			
			if (schematic == null)
				CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to load a schematic reward with the file name " + fileName);
			else
				rewards.add(new SchematicRewardType(schematic));
		});
		
		return rewards;
	}
	
	// TODO client sided only?
	/*
	 * public List<IRewardType> loadSoundReward(JsonArray rawReward,
	 * List<IRewardType> rewards) { List<SoundPart> sounds = new ArrayList<>();
	 * for (JsonElement element : rawReward) { SoundPart sound = new
	 * SoundPart(CCubesSounds.registerSound(element.getAsJsonObject().get(
	 * "sound").getAsString())); if (element.getAsJsonObject().has("delay"))
	 * sound.setDelay(element.getAsJsonObject().get("delay").getAsInt()); if
	 * (element.getAsJsonObject().has("serverWide"))
	 * sound.setServerWide(element.getAsJsonObject().get("serverWide").
	 * getAsBoolean()); if (element.getAsJsonObject().has("range"))
	 * sound.setRange(element.getAsJsonObject().get("range").getAsInt()); if
	 * (element.getAsJsonObject().has("playAtPlayersLocation"))
	 * sound.setAtPlayersLocation(element.getAsJsonObject().get(
	 * "playAtPlayersLocation").getAsBoolean()); if
	 * (element.getAsJsonObject().has("volume"))
	 * sound.setVolume(element.getAsJsonObject().get("volume").getAsInt()); if
	 * (element.getAsJsonObject().has("pitch"))
	 * sound.setPitch(element.getAsJsonObject().get("pitch").getAsInt());
	 * 
	 * sounds.add(sound); }
	 * 
	 * rewards.add(new SoundRewardType(sounds.toArray(new
	 * SoundPart[sounds.size()]))); return rewards; }
	 */
	
	public SimpleEntry<BasicReward, Boolean> parseReward(Entry<String, JsonElement> reward)
	{
		List<IRewardType> rewards = new ArrayList<>();
		JsonObject rewardElements = reward.getValue().getAsJsonObject();
		int chance = 0;
		boolean isGiantCubeReward = false;
		for (Entry<String, JsonElement> rewardElement : rewardElements.entrySet())
		{
			if (rewardElement.getKey().equalsIgnoreCase("chance"))
			{
				chance = rewardElement.getValue().getAsInt();
				continue;
			}
			else if (rewardElement.getKey().equalsIgnoreCase("dependencies"))
			{
				boolean gameversion = false;
				boolean mcversionused = false;
				for (Entry<String, JsonElement> dependencies : rewardElement.getValue().getAsJsonObject().entrySet())
				{
					// TODO mod dependencies not currently supported
					/*
					 * if (dependencies.getKey().equalsIgnoreCase("mod")) { if
					 * (!Loader.isModLoaded(dependencies.getValue().getAsString(
					 * ))) return null; } else
					 */ if (dependencies.getKey().equalsIgnoreCase("mcVersion"))
					{
						mcversionused = true;
						String[] versionsToCheck = dependencies.getValue().getAsString().split(",");
						for (String toCheckV : versionsToCheck)
						{
							String currentMCV = CCubesCore.gameVersion;
							if (toCheckV.contains("*"))
							{
								currentMCV = currentMCV.substring(0, currentMCV.lastIndexOf("."));
								toCheckV = toCheckV.substring(0, toCheckV.lastIndexOf("."));
							}
							if (currentMCV.equalsIgnoreCase(toCheckV))
								gameversion = true;
						}
					}
				}
				if (!gameversion && mcversionused)
					return null;
				continue;
			}
			else if (rewardElement.getKey().equalsIgnoreCase("isGiantCubeReward"))
			{
				isGiantCubeReward = rewardElement.getValue().getAsBoolean();
			}
			
			try
			{
				JsonArray rewardTypes = rewardElement.getValue().getAsJsonArray();
				if (rewardElement.getKey().equalsIgnoreCase("Item"))
					this.loadItemReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Block"))
					this.loadBlockReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Message"))
					this.loadMessageReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Command"))
					this.loadCommandReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Entity"))
					this.loadEntityReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Experience"))
					this.loadExperienceReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Potion"))
					this.loadPotionReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Schematic"))
					this.loadSchematicReward(rewardTypes, rewards);
				/*
				 * else if (rewardElement.getKey().equalsIgnoreCase("Sound"))
				 * this.loadSoundReward(rewardTypes, rewards);
				 */
				else if (rewardElement.getKey().equalsIgnoreCase("Chest"))
					this.loadChestReward(rewardTypes, rewards);
				else if (rewardElement.getKey().equalsIgnoreCase("Particle"))
					this.loadParticleReward(rewardTypes, rewards);
			}
			catch (Exception ex)
			{
				CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to load a custom reward for some reason. I will try better next time.");
				CCubesCore.instance().getLogger().log(Level.WARNING, ex.getMessage());
			}
		}
		
		return new SimpleEntry<>(new BasicReward(reward.getKey(), chance, rewards.toArray(new IRewardType[rewards.size()])), isGiantCubeReward);
	}
	
	public String removedKeyQuotes(String raw)
	{
		StringBuilder sb = new StringBuilder(raw);
		int index = 0;
		while ((index = sb.indexOf("\"", index)) != -1)
		{
			int secondQuote = sb.indexOf("\"", index + 1);
			if (secondQuote == -1)
				break;
			if (sb.charAt(secondQuote + 1) == ':')
			{
				sb.deleteCharAt(index);
				sb.delete(secondQuote - 1, secondQuote);
				index = secondQuote;
			}
			else
			{
				index++;
			}
		}
		return sb.toString();
	}
}