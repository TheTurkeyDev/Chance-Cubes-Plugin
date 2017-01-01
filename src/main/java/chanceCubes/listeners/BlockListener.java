package chanceCubes.listeners;

import chanceCubes.blocks.CCubesBlocks;
import chanceCubes.items.CCubesItems;
import chanceCubes.tileentities.ChanceCubeData;
import chanceCubes.tileentities.ChanceD20Data;
import chanceCubes.tileentities.GiantCubeData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener
{
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (!CCubesItems.isGenericChanceCube(event.getItemInHand()))
			return;

		Block block = event.getBlockPlaced();
		Material material = block.getType();
		if (material == CCubesItems.chanceCube.getType()) {
			block.setType(CCubesItems.chanceCube.getType());
			CCubesBlocks.addChanceCube(new ChanceCubeData(block.getLocation()));
		}
		else if (material == CCubesItems.giantChanceCube.getType()) {
			block.setType(CCubesItems.giantChanceCube.getType());
			CCubesBlocks.addGiantChanceCube(new GiantCubeData(block.getLocation()));
		}
		else if (material == CCubesItems.chanceIcosahedron.getType()) {
			block.setType(CCubesItems.chanceIcosahedron.getType());
			CCubesBlocks.addChanceD20(new ChanceD20Data(block.getLocation()));
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (!CCubesBlocks.isChanceCube(block))
			return;

		Material material = block.getType();
		Player player = event.getPlayer();
		if (material == CCubesItems.chanceCube.getType())
			CCubesBlocks.triggerChanceBlock(block.getLocation(), player);
		else if (material == CCubesItems.giantChanceCube.getType())
			CCubesBlocks.triggerGiantChanceCube(block.getLocation(), player);
		else if (material == CCubesItems.chanceIcosahedron.getType())
			CCubesBlocks.triggerD20(block.getLocation(), player);
		else
			return;

		//Fixes a small inventory desync between server and client.
		player.updateInventory();
		event.setCancelled(true);
	}
}
