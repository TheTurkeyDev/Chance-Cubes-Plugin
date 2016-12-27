package chanceCubes.rewards.type;

import chanceCubes.CCubesCore;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.util.RewardsUtil;
import java.util.logging.Level;
import net.minecraft.server.v1_10_R1.Entity;
import net.minecraft.server.v1_10_R1.EntityTypes;
import net.minecraft.server.v1_10_R1.MojangsonParseException;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.entity.Player;

public class EntityRewardType extends BaseRewardType<EntityPart>
{
	
	public EntityRewardType(EntityPart... entities)
	{
		super(entities);
	}
	
	public static NBTTagCompound getBasicNBTForEntity(String entity)
	{
		String json = "{id:" + entity + "}";
		NBTTagCompound nbt;
		try
		{
			nbt = MojangsonParser.parse(json);
		}
		catch (MojangsonParseException e)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "Failed to create a simple NBTTagCompound from " + entity);
			return null;
		}

		return nbt;
	}
	
	public void spawnEntity(EntityPart part, Location location)
	{
		if (part.shouldRemovedBlocks())
			for (int yy = 0; yy < 4; yy++)
				for (int xx = -1; xx < 2; xx++)
					for (int zz = -1; zz < 2; zz++)
						RewardsUtil.placeBlock(Material.AIR, location.clone().add(xx, yy, zz));
		
		World world = ((CraftWorld) location.getWorld()).getHandle();
		Entity newEnt = EntityTypes.a(part.getNBT(), world);
		if (newEnt == null)
		{
			CCubesCore.instance().getLogger().log(Level.WARNING, "An entity with " + part.getNBT().getString("id"));
			return;
		}

		newEnt.setPosition(location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5);
		world.addEntity(newEnt);
	}
	
	@Override
	public void trigger(final EntityPart part, final Location location, final Player player)
	{
		if (part.getDelay() != 0)
            RewardsUtil.scheduleTask(() -> spawnEntity(part, location), part.getDelay());
		else
            spawnEntity(part, location);
	}
}
