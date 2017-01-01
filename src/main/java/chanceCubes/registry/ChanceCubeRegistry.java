package chanceCubes.registry;

import chanceCubes.CCubesCore;
import chanceCubes.config.CCubesSettings;
import chanceCubes.config.ConfigLoader;
import chanceCubes.items.CCubesItems;
import chanceCubes.rewards.defaultRewards.AnvilRain;
import chanceCubes.rewards.defaultRewards.ArmorStandArmorReward;
import chanceCubes.rewards.defaultRewards.BasicReward;
import chanceCubes.rewards.defaultRewards.BookOfMemesReward;
import chanceCubes.rewards.defaultRewards.CakeIsALieReward;
import chanceCubes.rewards.defaultRewards.ChargedCreeperReward;
import chanceCubes.rewards.defaultRewards.ClearInventoryReward;
import chanceCubes.rewards.defaultRewards.CookieMonsterReward;
import chanceCubes.rewards.defaultRewards.CreeperSurroundedReward;
import chanceCubes.rewards.defaultRewards.CustomUserReward;
import chanceCubes.rewards.defaultRewards.DidYouKnowReward;
import chanceCubes.rewards.defaultRewards.DiscoReward;
import chanceCubes.rewards.defaultRewards.DoubleRainbow;
import chanceCubes.rewards.defaultRewards.EnderCrystalTimerReward;
import chanceCubes.rewards.defaultRewards.FiveProngReward;
import chanceCubes.rewards.defaultRewards.HerobrineReward;
import chanceCubes.rewards.defaultRewards.IChanceCubeReward;
import chanceCubes.rewards.defaultRewards.InventoryBombReward;
import chanceCubes.rewards.defaultRewards.ItemOfDestinyReward;
import chanceCubes.rewards.defaultRewards.ItemRenamer;
import chanceCubes.rewards.defaultRewards.JukeboxReward;
import chanceCubes.rewards.defaultRewards.MathReward;
import chanceCubes.rewards.defaultRewards.MazeReward;
import chanceCubes.rewards.defaultRewards.NukeReward;
import chanceCubes.rewards.defaultRewards.OneIsLuckyReward;
import chanceCubes.rewards.defaultRewards.QuestionsReward;
import chanceCubes.rewards.defaultRewards.RandomTeleportReward;
import chanceCubes.rewards.defaultRewards.RemoveUsefulThingsReward;
import chanceCubes.rewards.defaultRewards.RottenFoodReward;
import chanceCubes.rewards.defaultRewards.SkyblockReward;
import chanceCubes.rewards.defaultRewards.SurroundedReward;
import chanceCubes.rewards.defaultRewards.TableFlipReward;
import chanceCubes.rewards.defaultRewards.ThrownInAirReward;
import chanceCubes.rewards.defaultRewards.TorchesToCreepers;
import chanceCubes.rewards.defaultRewards.TrollHoleReward;
import chanceCubes.rewards.defaultRewards.TrollTNTReward;
import chanceCubes.rewards.defaultRewards.WaitForItReward;
import chanceCubes.rewards.defaultRewards.WitherReward;
import chanceCubes.rewards.defaultRewards.WolvesToCreepersReward;
import chanceCubes.rewards.rewardparts.CommandPart;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.rewards.rewardparts.ExperiencePart;
import chanceCubes.rewards.rewardparts.ItemPart;
import chanceCubes.rewards.rewardparts.MessagePart;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.OffsetTileEntity;
import chanceCubes.rewards.rewardparts.ParticlePart;
import chanceCubes.rewards.rewardparts.PotionPart;
import chanceCubes.rewards.rewardparts.SoundPart;
import chanceCubes.rewards.type.BlockRewardType;
import chanceCubes.rewards.type.CommandRewardType;
import chanceCubes.rewards.type.EntityRewardType;
import chanceCubes.rewards.type.ExperienceRewardType;
import chanceCubes.rewards.type.ItemRewardType;
import chanceCubes.rewards.type.MessageRewardType;
import chanceCubes.rewards.type.ParticleEffectRewardType;
import chanceCubes.rewards.type.PotionRewardType;
import chanceCubes.rewards.type.SoundRewardType;
import chanceCubes.util.RewardsUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import javax.annotation.Nullable;
import net.minecraft.server.v1_10_R1.Blocks;
import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.Enchantment;
import net.minecraft.server.v1_10_R1.Items;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.TileEntitySign;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Sign;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ChanceCubeRegistry implements IRewardRegistry {

    public static ChanceCubeRegistry INSTANCE = new ChanceCubeRegistry();
    private static IChanceCubeReward lastReward = null;
    private static Random random = new Random();
    private Map<String, IChanceCubeReward> disabledNameToReward = Maps.newHashMap();
    private Map<String, IChanceCubeReward> nameToReward = Maps.newHashMap();
    private List<IChanceCubeReward> sortedRewards = Lists.newArrayList();

    public static void loadCustomUserRewards() {
        Bukkit.getOnlinePlayers().forEach(CustomUserReward::new);
    }

    /**
     * loads the default rewards of the Chance Cube
     */
    public static void loadDefaultRewards() {
        RewardsUtil.initData();

        if (!CCubesSettings.enableHardCodedRewards)
            return;

        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Tnt_Structure", -30, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(3, 1, 3, Material.TNT, -1, 0, -1, true, 0, false, false), RewardsUtil.fillArea(3, 1, 3, Material.REDSTONE_BLOCK, -1, 1, -1, true, 30, false, false)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":BedRock", -20, new BlockRewardType(new OffsetBlock(0, 0, 0, Material.BEDROCK, false))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Redstone_Diamond", 10, new ItemRewardType(new ItemPart(new ItemStack(Material.REDSTONE)), new ItemPart(new ItemStack(Material.DIAMOND)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Sethbling_Reward", 30, new MessageRewardType(new MessagePart("Welcome back, SethBling here :)")), new ItemRewardType(new ItemPart(new ItemStack(Material.REDSTONE, 32)), new ItemPart(new ItemStack(Material.DIODE, 3)), new ItemPart(new ItemStack(Material.REDSTONE_COMPARATOR, 3)), new ItemPart(new ItemStack(Material.REDSTONE_LAMP_OFF, 3)), new ItemPart(new ItemStack(Material.REDSTONE_TORCH_ON, 3)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":EXP", 35, new ExperienceRewardType(new ExperiencePart(100).setNumberofOrbs(10))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":EXP_Shower", 35, new ExperienceRewardType(new ExperiencePart(10), new ExperiencePart(10, 10), new ExperiencePart(10, 10), new ExperiencePart(10, 20), new ExperiencePart(10, 30), new ExperiencePart(10, 40), new ExperiencePart(10, 50), new ExperiencePart(10, 60), new ExperiencePart(10, 70), new ExperiencePart(10, 80), new ExperiencePart(10, 90), new ExperiencePart(10, 100), new ExperiencePart(10, 110), new ExperiencePart(10, 120), new ExperiencePart(10, 130), new ExperiencePart(10, 140), new ExperiencePart(10, 150))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Poison", -25, new PotionRewardType(new PotionPart(new PotionEffect(PotionEffectType.POISON, 500, 1)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":ChatMessage", 0, new MessageRewardType(new MessagePart("You have escaped the wrath of the Chance Cubes........."), new MessagePart("For now......"))));
        // INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId()+":Command", 15, new CommandRewardType(" /give %player minecraft:painting 1 0 {display:{Name:\"Wylds Bestest friend\",Lore:[\"You know you love me, \"]}}")));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Hearts", 0, new ParticleEffectRewardType(RewardsUtil.spawnXParticles(Particle.HEART, 5))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Explosion", 0, new ParticleEffectRewardType(new ParticlePart(Particle.EXPLOSION_HUGE)), new SoundRewardType(new SoundPart(Sound.ENTITY_GENERIC_EXPLODE))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Wool", 25, new ItemRewardType(new ItemPart(new ItemStack(Material.WOOL, 4)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 1)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 2)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 3)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 4)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 5)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 6)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 7)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 8)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 9)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 10)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 11)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 12)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 13)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 14)), new ItemPart(new ItemStack(Material.WOOL, 4, (short) 15)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Enchanting", 80, new ItemRewardType(new ItemPart(new ItemStack(Material.ENCHANTMENT_TABLE)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Bookshelves", 60, new ItemRewardType(new ItemPart(new ItemStack(Material.BOOKSHELF, 8)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Ores_Galore", 50, new ItemRewardType(new ItemPart(new ItemStack(Material.COAL)), new ItemPart(new ItemStack(Material.REDSTONE)), new ItemPart(new ItemStack(Material.IRON_INGOT)), new ItemPart(new ItemStack(Material.GOLD_INGOT)), new ItemPart(new ItemStack(Material.DIAMOND)), new ItemPart(new ItemStack(Material.EMERALD)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Have_Another", 0, new ItemRewardType(new ItemPart(CCubesItems.getMultipleOfItem(CCubesItems.chanceCube, 3))), new MessageRewardType(new MessagePart("I hear you like Chance Cubes."), new MessagePart("So I put some Chance Cubes in your Chance Cubes!"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Icsahedron", 0, new ItemRewardType(new ItemPart(CCubesItems.chanceIcosahedron))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Saplings", 35, new MessageRewardType(new MessagePart("Seems you have purchased the saplings DLC")), new ItemRewardType(new ItemPart(new ItemStack(Material.SAPLING, 4)), new ItemPart(new ItemStack(Material.SAPLING, 4, (short) 1)), new ItemPart(new ItemStack(Material.SAPLING, 4, (short) 2)), new ItemPart(new ItemStack(Material.SAPLING, 4, (short) 3)), new ItemPart(new ItemStack(Material.SAPLING, 4, (short) 4)), new ItemPart(new ItemStack(Material.SAPLING, 4, (short) 5)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Farmer", 35, new MessageRewardType(new MessagePart("Time to farm!")), new ItemRewardType(new ItemPart(new ItemStack(Material.IRON_HOE)), new ItemPart(new ItemStack(Material.BUCKET)), new ItemPart(new ItemStack(Material.SEEDS, 16)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Rancher", 60, new ItemRewardType(new ItemPart(new ItemStack(Material.FENCE, 32)), new ItemPart(RewardsUtil.getSpawnEggForMob("Pig")), new ItemPart(RewardsUtil.getSpawnEggForMob("Cow")), new ItemPart(RewardsUtil.getSpawnEggForMob("Sheep")))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Fighter", 25, new MessageRewardType(new MessagePart("SPARTAAA!!!")), new ItemRewardType(new ItemPart(new ItemStack(Material.IRON_SWORD)), new ItemPart(new ItemStack(Material.IRON_HELMET)), new ItemPart(new ItemStack(Material.IRON_CHESTPLATE)), new ItemPart(new ItemStack(Material.IRON_LEGGINGS)), new ItemPart(new ItemStack(Material.IRON_BOOTS)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":pssst", -5, new MessageRewardType(new MessagePart("Pssssst.... Over here!")), new EntityRewardType(new EntityPart(EntityRewardType.getBasicNBTForEntity("Creeper")))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Explorer", 30, new MessageRewardType(new MessagePart("Lets go on a journey!")), new ItemRewardType(new ItemPart(new ItemStack(Material.COMPASS)), new ItemPart(new ItemStack(Material.WATCH)), new ItemPart(new ItemStack(Material.TORCH, 64)), new ItemPart(new ItemStack(Material.IRON_PICKAXE)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Mitas", 50, new ItemRewardType(new ItemPart(new ItemStack(Material.GOLD_NUGGET, 32)), new ItemPart(new ItemStack(Material.GOLD_INGOT, 8)), new ItemPart(new ItemStack(Material.GOLDEN_CARROT, 16)), new ItemPart(new ItemStack(Material.GOLD_HELMET)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Horde", -25, new MessageRewardType(new MessagePart("Release the horde!")), new EntityRewardType(RewardsUtil.spawnXEntities(EntityRewardType.getBasicNBTForEntity("Zombie"), 15))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Lava_Ring", -40, new BlockRewardType(new OffsetBlock(1, -1, 0, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(1, -1, 1, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(0, -1, 1, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(-1, -1, 1, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(-1, -1, 0, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(-1, -1, -1, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(0, -1, -1, Material.LAVA, false).setRelativeToPlayer(true), new OffsetBlock(1, -1, -1, Material.LAVA, false).setRelativeToPlayer(true))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Rain", -5, new CommandRewardType(new CommandPart("/weather thunder 20000"))));
        // INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":House", 75, new SchematicRewardType("house.schematic", 3, true, false)));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Silverfish_Surround", -20, new BlockRewardType(new OffsetBlock(1, 0, 0, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(1, 1, 0, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 0, 1, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 1, 1, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(-1, 0, 0, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(-1, 1, 0, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 0, -1, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 1, -1, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, 2, 0, Material.MONSTER_EGG, false).setRelativeToPlayer(true), new OffsetBlock(0, -1, 0, Material.MONSTER_EGG, false).setRelativeToPlayer(true))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Fish_Dog", 20, new ItemRewardType(new ItemPart(new ItemStack(Material.RAW_FISH, 5))), new CommandRewardType(new CommandPart("/summon Item %x %y %z {Item:{id:spawn_egg,Damage:95,Count:1,tag:{EntityTag:{id:\"Wolf\"}}}}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Bone_Cat", 20, new ItemRewardType(new ItemPart(new ItemStack(Material.BONE, 5))), new CommandRewardType(new CommandPart("/summon Item %x %y %z {Item:{id:spawn_egg,Damage:95,Count:1,tag:{EntityTag:{id:\"Ozelot\"}}}}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":XP_Crystal", -60, new CommandRewardType(new CommandPart("/summon XPOrb ~ ~1 ~ {Value:1,Passengers:[{id:\"EnderCrystal\"}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":TNT_Cat", -25, new CommandRewardType(new CommandPart("/summon Ozelot ~ ~1 ~ {CatType:0,Sitting:0,Passengers:[{id:\"PrimedTnt\",Fuse:80}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":SlimeMan", 10, new CommandRewardType(new CommandPart("/summon Slime ~ ~1 ~ {Size:3,Glowing:1b,Passengers:[{id:\"Slime\",Size:2,Glowing:1b,Passengers:[{id:\"Slime\",CustomName:\"Slime Man\",CustomNameVisible:1,Size:1,Glowing:1b}]}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Sail_Away", 5, new BlockRewardType(new OffsetBlock(0, -1, 0, Material.WATER, false)), new CommandRewardType(new CommandPart("/summon Boat %x %y %z")), new MessageRewardType(new MessagePart("Come sail away!"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Witch", -15, new CommandRewardType(new CommandPart("/summon Witch %x %y %z "))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Spawn_Cluckington", 40, new CommandRewardType(new CommandPart("/summon Chicken ~ ~1 ~ {CustomName:\"Cluckington\",CustomNameVisible:1,ActiveEffects:[{Id:1,Amplifier:3,Duration:199980}],Passengers:[{id:\"Zombie\",CustomName:\"Wyld\",CustomNameVisible:1,IsVillager:0,IsBaby:1}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Spawn_Jerry", 40, new CommandRewardType(new CommandPart("/summon Slime %x %y %z {Size:1,CustomName:\"Jerry\",CustomNameVisible:1}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Spawn_Glenn", 40, new CommandRewardType(new CommandPart("/summon Zombie %x %y %z {CustomName:\"Glenn\",CustomNameVisible:1}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Spawn_Dr_Trayaurus", 40, new CommandRewardType(new CommandPart("/summon Villager %x %y %z {CustomName:\"Dr Trayaurus\",CustomNameVisible:1,Profession:1}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Spawn_Pickles", 40, new CommandRewardType(new CommandPart("/summon MushroomCow ~ ~1 ~ {Age:-10000,CustomName:\"Pickles\"}")), new MessageRewardType(new MessagePart("Why is his name pickles? The world may neve know"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Want_To_Build_A_Snowman", 45, new MessageRewardType(new MessagePart("Do you want to build a snowman?")), new ItemRewardType(new ItemPart(new ItemStack(Material.SNOW, 2)), new ItemPart(new ItemStack(Material.PUMPKIN)))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Diamond_Block", 85, new BlockRewardType(new OffsetBlock(0, 0, 0, Material.DIAMOND_BLOCK, true, 200))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":TNT_Diamond", -35, new BlockRewardType(new OffsetBlock(0, 1, 0, Material.DIAMOND_BLOCK, false), new OffsetBlock(0, -1, 0, Material.DIAMOND_BLOCK, false), new OffsetBlock(1, 0, 0, Material.DIAMOND_BLOCK, false), new OffsetBlock(-1, 0, 0, Material.DIAMOND_BLOCK, false), new OffsetBlock(0, 0, 1, Material.DIAMOND_BLOCK, false), new OffsetBlock(0, 0, -1, Material.DIAMOND_BLOCK, false)), new CommandRewardType(RewardsUtil.executeXCommands("/summon PrimedTnt %x %y %z {Fuse:40}", 3, 5))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Anti_Slab", -15, new BlockRewardType(RewardsUtil.fillArea(3, 1, 3, Material.OBSIDIAN, -1, 2, -1, false, 0, false, true))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":chanceCube_Cube", -10, new MessageRewardType(new MessagePart("Hey, at least it isn't a Giant Chance Cube >:)")), new BlockRewardType(new OffsetBlock(-1, 0, -1, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-1, 0, -2, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-2, 0, -1, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-2, 0, -2, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-1, 1, -1, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-1, 1, -2, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-2, 1, -1, CCubesItems.chanceCube.getType(), false), new OffsetBlock(-2, 1, -2, CCubesItems.chanceCube.getType(), false))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Fake_TNT", 0, new SoundRewardType(new SoundPart(Sound.ENTITY_TNT_PRIMED), new SoundPart(Sound.ENTITY_TNT_PRIMED), new SoundPart(Sound.ENTITY_TNT_PRIMED), new SoundPart(Sound.ENTITY_GENERIC_EXPLODE).setDelay(120).setAtPlayersLocation(true))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Invisible_Ghasts", 0, new SoundRewardType(new SoundPart(Sound.ENTITY_GHAST_SCREAM).setServerWide(true), new SoundPart(Sound.ENTITY_GHAST_WARN).setServerWide(true), new SoundPart(Sound.ENTITY_GHAST_WARN).setServerWide(true))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":No", 0, new BlockRewardType(new OffsetBlock(0, 0, 0, CCubesItems.chanceCube.getType(), false)), new MessageRewardType(new MessagePart("No"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Invisible_Creeper", -30, new CommandRewardType(new CommandPart("/summon Creeper %x %y %z {ActiveEffects:[{Id:14,Amplifier:0,Duration:200,ShowParticles:0b}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Knockback_Zombie", -35, new CommandRewardType(new CommandPart("/summon Zombie ~ ~1 ~ {CustomName:\"Leonidas\",CustomNameVisible:1,IsVillager:0,IsBaby:1,HandItems:[{id:stick,Count:1,tag:{AttributeModifiers:[{AttributeName:\"generic.knockbackResistance\",Name:\"generic.knockbackResistance\",Amount:100,Operation:0,UUIDLeast:724513,UUIDMost:715230}],ench:[{id:19,lvl:100}],display:{Name:\"The Spartan Kick\"}}},{}],HandDropChances:[0.0F,0.085F],ActiveEffects:[{Id:1,Amplifier:5,Duration:199980,ShowParticles:0b},{Id:8,Amplifier:2,Duration:199980}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Actual_Invisible_Ghast", -80, new CommandRewardType(new CommandPart("/summon Ghast ~ ~10 ~ {ActiveEffects:[{Id:14,Amplifier:0,Duration:2000,ShowParticles:0b}]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Giant_chanceCube", -45, new BlockRewardType(RewardsUtil.fillArea(3, 3, 3, CCubesItems.chanceCube.getType(), -1, 0, -1, false, 0, true, false))), false);
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Fireworks", 0, new CommandRewardType(RewardsUtil.executeXCommands("/summon FireworksRocketEntity ~ ~1 ~ {FireworksItem:{id:fireworks,Count:1,tag:{Fireworks:{Explosions:[{Type:0,Colors:[16711680],FadeColors:[16711680]}]}}}}", 4))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":STRING!", 7, new BlockRewardType(RewardsUtil.fillArea(11, 5, 11, Material.TRIPWIRE, -5, 0, -5, false, 0, false, true)), new MessageRewardType(new MessagePart("STRING!!!!"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":TNT_Bats", -50, new CommandRewardType(RewardsUtil.executeXCommands("/summon Bat ~ ~1 ~ {Passengers:[{id:\"PrimedTnt\",Fuse:80}]}", 10))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Nether_Jelly_Fish", -40, new CommandRewardType(RewardsUtil.executeXCommands("/summon Bat ~ ~1 ~ {Passengers:[{id:\"LavaSlime\",CustomName:\"Nether Jelly Fish\",CustomNameVisible:1,Size:3}]}", 10))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Pig_Of_Destiny", 15, new CommandRewardType(new CommandPart("/summon Pig ~ ~1 ~ {CustomName:\"The Pig of Destiny\",CustomNameVisible:1,ArmorItems:[{},{},{id:diamond_chestplate,Count:1,tag:{ench:[{id:7,lvl:1000}]}},{}],ArmorDropChances:[0.085F,0.085F,0.0F,0.085F]}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Squid_Horde", 5, new MessageRewardType(new MessagePart("Release the horde!").setRange(32), new MessagePart("Of squids!!").setDelay(20).setRange(32)), new EntityRewardType(RewardsUtil.spawnXEntities(EntityRewardType.getBasicNBTForEntity("Squid"), 15)), new BlockRewardType(RewardsUtil.fillArea(3, 2, 3, Material.WATER, -1, 0, -1, false, 5, true, false))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":D-rude_SandStorm", -10, new BlockRewardType(RewardsUtil.fillArea(5, 3, 5, Material.SAND, -2, 0, -2, true, 0, false, true)), new MessageRewardType(new MessagePart("Well that was D-rude").setDelay(40))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":DIY_Pie", 5, new BlockRewardType(new OffsetBlock(1, 0, 0, Material.PUMPKIN, false), new OffsetBlock(1, 1, 0, Material.SUGAR_CANE_BLOCK, false)), new CommandRewardType(new CommandPart("/summon Chicken ~ ~1 ~ {CustomName:\"Zeeth_Kyrah\",CustomNameVisible:1}")), new MessageRewardType(new MessagePart("Do it yourself Pumpkin Pie!"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Watch_World_Burn", -5, new BlockRewardType(RewardsUtil.fillArea(7, 1, 7, Material.FIRE, -3, 0, -3, false, 0, true, true)), new MessageRewardType(new MessagePart("Some people just want to watch the world burn."))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Coal_To_Diamonds", -35, new BlockRewardType(new OffsetBlock(0, 1, 0, Material.COAL_BLOCK, false), new OffsetBlock(0, -1, 0, Material.COAL_BLOCK, false), new OffsetBlock(1, 0, 0, Material.COAL_BLOCK, false), new OffsetBlock(-1, 0, 0, Material.COAL_BLOCK, false), new OffsetBlock(0, 0, 1, Material.COAL_BLOCK, false), new OffsetBlock(0, 0, -1, Material.COAL_BLOCK, false)), new CommandRewardType(RewardsUtil.executeXCommands("/summon PrimedTnt %x %y %z {Fuse:40}", 3, 5)), new ItemRewardType(new ItemPart(new ItemStack(Material.DIAMOND, 5), 50))));
        // INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Glitch", 0, new CommandRewardType(new CommandPart("/summon Item ~ ~ ~ {Item:{id:dirt,Damage:1,Count:1,tag:{display:{Name:\"Glitch\",Lore:[Doesn't actually do anything...]}}}}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":SpongeBob_SquarePants", 15, new CommandRewardType(new CommandPart("/summon Item ~ ~ ~ {Item:{id:sponge,Count:1,tag:{display:{Name:\"SpongeBob\"}}}}"), new CommandPart("/summon Item ~ ~ ~ {Item:{id:leather_leggings,Count:1,tag:{display:{Name:\"SquarePants\"}}}}"))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Hot_Tub", -15, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(7, 1, 7, Material.WATER, -3, -1, -3, false, 0, true, true), RewardsUtil.fillArea(7, 1, 7, Material.AIR, -3, -1, -3, false, 98, true, true), RewardsUtil.fillArea(7, 1, 7, Material.LAVA, -3, -1, -3, false, 100, true, true))), new MessageRewardType(new MessagePart("No no no. I wanted a hot tub!").setDelay(40))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Quidditch", 0, new CommandRewardType(RewardsUtil.executeXCommands("/summon Bat ~ ~ ~ {Passengers:[{id:\"Witch\"}]}", 7)), new MessageRewardType(new MessagePart("quidditch anyone?").setRange(32))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":One_Man_Army", -10, new EntityRewardType(new EntityPart(EntityRewardType.getBasicNBTForEntity("PigZombie"))), new CommandRewardType(RewardsUtil.executeXCommands("/summon PigZombie ~ ~ ~ {Silent:1,ActiveEffects:[{Id:14,Amplifier:0,Duration:19980,ShowParticles:1b}]}", 9)), new MessageRewardType(new MessagePart("One man army").setRange(32))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Silvermite_Stacks", -15, new CommandRewardType(RewardsUtil.executeXCommands("/summon Silverfish ~ ~1 ~ {Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\",Passengers:[{id:\"Endermite\",Passengers:[{id:\"Silverfish\"}]}]}]}]}]}]}]}]}]}]}", 5))));
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Take_This", 55, new BlockRewardType(new OffsetBlock(0, 0, 0, Material.BRICK, false), new OffsetBlock(0, 1, 0, Material.BRICK, false), new OffsetBlock(0, 2, 0, Material.BRICK, false)), new CommandRewardType(new CommandPart("/summon ItemFrame ~ ~ ~1 {Item:{id:stick},Facing:0,ItemRotation:7}", 2), new CommandPart("/summon ItemFrame ~ ~1 ~1 {Item:{id:diamond},Facing:0,ItemRotation:0}", 2), new CommandPart("/summon ItemFrame ~ ~2 ~1 {Item:{id:diamond},Facing:0,ItemRotation:0}", 2)), new MessageRewardType(new MessagePart("It's dangerous to go alone, here take this!"))));

        net.minecraft.server.v1_10_R1.ItemStack stack;
        NBTTagCompound nbt = new NBTTagCompound();

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.STICK);
        stack.addEnchantment(Enchantment.b("sharpness"), 5);
        stack.c("A Big Stick");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Roosevelt's_Stick", 70, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.FISHING_ROD);
        stack.setData(stack.j() / 2);
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Half_Fishingrod", 5, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.GOLDEN_APPLE, 1, 1);
        stack.c("Notch");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Notch", 70, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.NETHER_STAR);
        stack.c("North Star");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Nether_Star", 100, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.DIAMOND_SWORD);
        stack.addEnchantment(Enchantment.b("sharpness"), 10);
        stack.addEnchantment(Enchantment.b("unbreaking"), 10);
        stack.setData(stack.j() - 2);
        stack.c("The Divine Sword");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Divine", 85, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.WOODEN_PICKAXE);
        stack.addEnchantment(Enchantment.b("efficiency"), 10);
        stack.addEnchantment(Enchantment.b("fortune"), 3);
        stack.c("Giga Breaker");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Giga_Breaker", 70, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.BOW);
        stack.setData(stack.j());
        stack.addEnchantment(Enchantment.b("power"), 5);
        stack.addEnchantment(Enchantment.b("punch"), 3);
        stack.addEnchantment(Enchantment.b("flame"), 2);
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":One_Shot", 75, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)), new ItemPart(new ItemStack(Material.ARROW, 1)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.FISH, 1, 2);
        stack.c("Nemo");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Finding_Nemo", 10, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Items.FISH, 1, 2);
        stack.c("Marlin");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Finding_Marlin", 10, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        stack = new net.minecraft.server.v1_10_R1.ItemStack(Blocks.FIRE, 1);
        stack.addEnchantment(Enchantment.b("fire_aspect"), 2);
        stack.c("Why not?");
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Fire_Aspect_Fire", 60, new ItemRewardType(new ItemPart(CraftItemStack.asBukkitCopy(stack)))));

        TileEntitySign sign = new TileEntitySign();
        sign.lines[0] = new ChatComponentText("The broken path");
        sign.lines[1] = new ChatComponentText("to succeed");
        nbt = new NBTTagCompound();
        sign.a(nbt);
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Path_To_Succeed", 0, new BlockRewardType(new OffsetTileEntity(0, 0, -5, Material.SIGN_POST, nbt, true, 20), new OffsetBlock(0, -1, 0, Material.COBBLESTONE, true, 0), new OffsetBlock(0, -1, -1, Material.COBBLESTONE, true, 4), new OffsetBlock(0, -1, -2, Material.COBBLESTONE, true, 8), new OffsetBlock(0, -1, -3, Material.COBBLESTONE, true, 12), new OffsetBlock(0, -1, -4, Material.COBBLESTONE, true, 16), new OffsetBlock(0, -1, -5, Material.COBBLESTONE, true, 20))));

        OffsetTileEntity[] signs = new OffsetTileEntity[4];
        OffsetTileEntity temp;
        for (int i = 0; i < signs.length; i++) {
            sign = new TileEntitySign();
            sign.lines[0] = new ChatComponentText("Help Me!");
            nbt = new NBTTagCompound();
            sign.a(nbt);
            temp = new OffsetTileEntity(i == 2 ? -2 : i == 3 ? 2 : 0, 1, i == 0 ? -2 : i == 1 ? 2 : 0, Material.WALL_SIGN, nbt, false, 5);
            Sign signData = new Sign();
            signData.setFacingDirection(BlockFace.values()[i]);
            temp.setMaterialData(signData);
            signs[i] = temp;
        }

        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Help_Me", 0, new BlockRewardType(RewardsUtil.addBlocksLists(RewardsUtil.fillArea(3, 1, 3, Material.SMOOTH_BRICK, -1, -1, -1, false, 0, true, false), RewardsUtil.fillArea(3, 3, 3, Material.IRON_FENCE, -1, 0, -1, false, 0, true, false), RewardsUtil.fillArea(1, 3, 1, Material.AIR, 0, 0, 0, false, 1, true, false), signs)), new EntityRewardType(new EntityPart(EntityRewardType.getBasicNBTForEntity("Villager")).setRemovedBlocks(false).setDelay(5)), new CommandRewardType(new CommandPart("/summon PrimedTnt %x %y %z {Fuse:80}", 5))));

        OffsetBlock[] blocks = new OffsetBlock[35];
        int i = 0;
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 5; x++) {
                for (int z = 0; z < 5; z++) {
                    if (y == 1 && (x == 0 || x == 4 || z == 0 || z == 4))
                        continue;
                    blocks[i] = new OffsetBlock(x - 2, y, z - 2, Material.IRON_BLOCK, true, i * 5);
                    i++;
                }
            }
        }
        blocks[i] = new OffsetBlock(0, 2, 0, Material.BEACON, true, 200);
        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Beacon_Build", 100, new BlockRewardType(blocks)));

        ChanceCubeRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":No_Exp", -40) {
            @Override
            public void trigger(Location location, Player player) {
                player.setTotalExperience(0);
                player.sendMessage("Screw you last stand");
            }
        });

        ChanceCubeRegistry.INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Smite", -10) {
            @Override
            public void trigger(Location location, Player player) {
                location.getWorld().strikeLightning(location);
                player.sendMessage("Thou has been smitten!");
            }
        });

        INSTANCE.registerReward(new NukeReward());
        INSTANCE.registerReward(new FiveProngReward());
        INSTANCE.registerReward(new AnvilRain());
        INSTANCE.registerReward(new HerobrineReward());
        INSTANCE.registerReward(new SurroundedReward());
        INSTANCE.registerReward(new CreeperSurroundedReward());
        INSTANCE.registerReward(new RandomTeleportReward());
        INSTANCE.registerReward(new TrollHoleReward());
        INSTANCE.registerReward(new CookieMonsterReward());
        INSTANCE.registerReward(new WitherReward());
        INSTANCE.registerReward(new TrollTNTReward());
        INSTANCE.registerReward(new EnderCrystalTimerReward());
        INSTANCE.registerReward(new WaitForItReward());
        INSTANCE.registerReward(new ChargedCreeperReward());
        INSTANCE.registerReward(new ClearInventoryReward(), false);
        // INSTANCE.registerReward(new ZombieCopyCatReward());
        // INSTANCE.registerReward(new InventoryChestReward());
        INSTANCE.registerReward(new ItemOfDestinyReward());
        INSTANCE.registerReward(new ThrownInAirReward());
        INSTANCE.registerReward(new DiscoReward());
        INSTANCE.registerReward(new InventoryBombReward());
        INSTANCE.registerReward(new JukeboxReward());
        INSTANCE.registerReward(new BookOfMemesReward());
        INSTANCE.registerReward(new RemoveUsefulThingsReward());
        INSTANCE.registerReward(new TableFlipReward());
        INSTANCE.registerReward(new TorchesToCreepers());
        INSTANCE.registerReward(new MazeReward());
        INSTANCE.registerReward(new RottenFoodReward());
        INSTANCE.registerReward(new OneIsLuckyReward());
        INSTANCE.registerReward(new SkyblockReward());
        INSTANCE.registerReward(new CakeIsALieReward());
        INSTANCE.registerReward(new ItemRenamer());
        INSTANCE.registerReward(new DoubleRainbow());
        INSTANCE.registerReward(new WolvesToCreepersReward());
        INSTANCE.registerReward(new DidYouKnowReward());
        INSTANCE.registerReward(new ArmorStandArmorReward());

        INSTANCE.registerReward(new BasicReward(CCubesCore.instance().getId() + ":Half_Heart", -30) {
            @Override
            public void trigger(Location location, Player player) {
                player.setHealth(1D);
            }
        });

        MathReward math = new MathReward();
        Bukkit.getPluginManager().registerEvents(math, CCubesCore.instance());
        INSTANCE.registerReward(math);

        QuestionsReward question = new QuestionsReward();
        Bukkit.getPluginManager().registerEvents(question, CCubesCore.instance());
        INSTANCE.registerReward(question);
    }

    public void ClearRewards() {
        this.sortedRewards.clear();
        this.nameToReward.clear();
        this.disabledNameToReward.clear();
    }

    public boolean enableReward(String reward) {
        if (this.disabledNameToReward.containsKey(reward))
            return this.enableReward(this.disabledNameToReward.get(reward));

        return false;
    }

    public boolean enableReward(IChanceCubeReward reward) {
        this.disabledNameToReward.remove(reward.getName());
        nameToReward.put(reward.getName(), reward);
        redoSort(reward);
        return true;
    }

    public int getNumberOfDisabledRewards() {
        return this.disabledNameToReward.size();
    }

    public int getNumberOfLoadedRewards() {
        return this.sortedRewards.size();
    }

    @Override
    public IChanceCubeReward getRewardByName(String name) {
        return nameToReward.get(name);
    }

    private void redoSort(@Nullable IChanceCubeReward newReward) {
        if (newReward != null)
            sortedRewards.add(newReward);

        Collections.sort(sortedRewards, new Comparator<IChanceCubeReward>() {
            public int compare(IChanceCubeReward o1, IChanceCubeReward o2) {
                return o1.getChanceValue() - o2.getChanceValue();
            }

            ;
        });
    }

    @Override
    public void registerReward(IChanceCubeReward reward) {
        this.registerReward(reward, true);
    }

    public void registerReward(IChanceCubeReward reward, boolean enabledDefault) {
        if (CCubesSettings.rewards.getBoolean(reward.getName(), enabledDefault) && !this.nameToReward.containsKey(reward.getName())) {
            nameToReward.put(reward.getName(), reward);
            redoSort(reward);
        }
        else {
            this.disabledNameToReward.put(reward.getName(), reward);
        }
    }

    @Override
    public void triggerRandomReward(Location location, Player player, int chance) {
        if (this.sortedRewards.size() == 0) {
            CCubesCore.instance().getLogger().log(Level.WARNING, "There are no registered rewards with ChanceCubes and no reward was able to be given");
            return;
        }

        if (CCubesSettings.doesHolidayRewardTrigger && CCubesSettings.holidayReward != null) {
            CCubesSettings.holidayReward.trigger(location, player);
            CCubesSettings.doesHolidayRewardTrigger = false;
            CCubesSettings.holidayRewardTriggered = true;
            ConfigLoader.save();
            return;
        }

        if (player != null) {
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (itemStack != null && itemStack.getType() == CCubesItems.chancePendantT1.getType()) {
                    ItemMeta meta = itemStack.getItemMeta();
                    if (meta.hasDisplayName() && meta.getDisplayName().startsWith("Chance Pendant") && meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        int bonus = Integer.parseInt(lore.get(2).replace("    +", "").replace(" when the block is broken", ""));
                        int uses = Integer.parseInt(lore.get(5).replace("Uses: ", "")) - 1;
                        if (uses <= 0)
                            player.getInventory().setItem(i, null);

                        chance += bonus;
                        if (chance > 100)
                            chance = 100;

                        break;
                    }
                }
            }
        }

        int lowerIndex = 0;
        int upperIndex = sortedRewards.size() - 1;
        int lowerRange = chance - CCubesSettings.rangeMin < -100 ? -100 : chance - CCubesSettings.rangeMin;
        int upperRange = chance + CCubesSettings.rangeMax > 100 ? 100 : chance + CCubesSettings.rangeMax;

        while (sortedRewards.get(lowerIndex).getChanceValue() < lowerRange) {
            lowerIndex++;
            if (lowerIndex >= sortedRewards.size()) {
                lowerIndex--;
                break;
            }
        }
        while (sortedRewards.get(upperIndex).getChanceValue() > upperRange) {
            upperIndex--;
            if (upperIndex < 0) {
                upperIndex++;
                break;
            }
        }
        int range = upperIndex - lowerIndex > 0 ? upperIndex - lowerIndex : 1;
        int pick = random.nextInt(range) + lowerIndex;
        IChanceCubeReward pickedReward = sortedRewards.get(pick);
        if (lastReward != null) {
            byte attempts = 0;
            while (attempts < 5 && lastReward.getName().equals(pickedReward.getName())) {
                pick = random.nextInt(range) + lowerIndex;
                pickedReward = sortedRewards.get(pick);
                attempts++;
            }
        }

        CCubesCore.instance().getLogger().log(Level.INFO, "Triggered the reward with the name of: " + pickedReward.getName());
        pickedReward.trigger(location, player);
        lastReward = pickedReward;
    }

    @Override
    public boolean unregisterReward(String name) {
        IChanceCubeReward reward = nameToReward.remove(name);
        if (reward != null) {
            this.disabledNameToReward.put(name, reward);
            return sortedRewards.remove(reward);
        }
        return false;
    }
}