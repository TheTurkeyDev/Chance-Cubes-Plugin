package chanceCubes.util;

import chanceCubes.config.ConfigLoader;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.OffsetTileEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.server.v1_10_R1.Block;
import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.MinecraftKey;
import net.minecraft.server.v1_10_R1.MojangsonParseException;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.util.CraftMagicNumbers;
import org.bukkit.material.MaterialData;

public class SchematicUtil {

    public static Location[] selectionPoints = new Location[2];
    private static Gson gson = new GsonBuilder().create();

    public static OffsetTileEntity OffsetBlockToTileEntity(OffsetBlock osb, String nbt) {
        try {
            return OffsetBlockToTileEntity(osb, MojangsonParser.parse(nbt));
        }
        catch (MojangsonParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static OffsetTileEntity OffsetBlockToTileEntity(OffsetBlock osb, NBTTagCompound nbt) {
        OffsetTileEntity oste = new OffsetTileEntity(osb.xOff, osb.yOff, osb.zOff, osb.getMaterial(), osb.getMaterialData(), nbt, osb.isFalling(), osb.getDelay());
        oste.setRelativeToPlayer(osb.isRelativeToPlayer());
        oste.setFalling(osb.isFalling());
        return oste;
    }

    public static void createCustomSchematic(Location loc1, Location loc2, String fileName) {
        World world = ((CraftWorld) loc1.getWorld()).getHandle();
        List<Integer> blocks = new ArrayList<>();
        List<SimpleEntry<Integer, String>> blockDataIds = new ArrayList<>();
        List<SimpleEntry<String, List<Integer>>> tileEntityData = new ArrayList<>();
        int largeX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int smallX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int largeY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int smallY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int largeZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        int smallZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        for (int y = smallY; y < largeY; y++) {
            for (int x = smallX; x < largeX; x++) {
                for (int z = smallZ; z < largeZ; z++) {
                    BlockPosition pos = new BlockPosition(x, y, z);
                    IBlockData state = world.c(pos);
                    MinecraftKey mk = Block.REGISTRY.b(state.getBlock());
                    //TODO need to look into a way to easily grab a non magic value for the variants
                    String blockData = mk.toString() + ":" + state.getBlock().toLegacyData(state);
                    int id = -1;
                    for (Entry<Integer, String> data : blockDataIds)
                        if (blockData.equalsIgnoreCase(data.getValue()))
                            id = data.getKey();

                    if (id == -1) {
                        id = blockDataIds.size();
                        blockDataIds.add(new SimpleEntry<>(id, blockData));
                    }

                    blocks.add(id);
                    //TODO left off here
                    TileEntity te = world.getTileEntity(pos);
                    if (te != null) {
                        NBTTagCompound nbt = te.save(new NBTTagCompound());
                        for (SimpleEntry<String, List<Integer>> data : tileEntityData) {
                            if (nbt.toString().equalsIgnoreCase(data.getKey())) {
                                data.getValue().add(blocks.size() - 1);
                                break;
                            }
                        }

                        List<Integer> list = new ArrayList<>();
                        list.add(blocks.size() - 1);
                        tileEntityData.add(new SimpleEntry<>(nbt.toString(), list));
                    }
                }
            }
        }

        JsonObject json = new JsonObject();

        JsonArray blockArray = new JsonArray();

        int row = 0;
        int last = -1;
        for (int i : blocks) {
            if (last == i) {
                row++;
            }
            else {
                if (row != 0) {
                    String value = "" + last;
                    if (row != 1)
                        value += "x" + row;
                    blockArray.add(new JsonPrimitive(value));
                }
                last = i;
                row = 1;
            }

        }

        String value = "" + last;
        if (row != 1)
            value += "x" + row;
        blockArray.add(new JsonPrimitive(value));

        json.add("Blocks", blockArray);

        JsonArray blockDataArray = new JsonArray();
        for (SimpleEntry<Integer, String> i : blockDataIds) {
            JsonObject index = new JsonObject();
            index.addProperty(i.getValue(), i.getKey());
            blockDataArray.add(index);
        }
        json.add("Block Data", blockDataArray);

        JsonArray tileEntityDataArray = new JsonArray();
        for (SimpleEntry<String, List<Integer>> i : tileEntityData) {
            JsonObject index = new JsonObject();
            JsonArray tileEntityBlockIds = new JsonArray();
            for (int id : i.getValue())
                tileEntityBlockIds.add(new JsonPrimitive(id));
            index.add(i.getKey(), tileEntityBlockIds);
            tileEntityDataArray.add(index);
        }
        json.add("TileEntities", tileEntityDataArray);

        JsonObject info = new JsonObject();
        info.addProperty("xSize", largeX - smallX);
        info.addProperty("ySize", largeY - smallY);
        info.addProperty("zSize", largeZ - smallZ);
        json.add("Schematic Data", info);

        FileUtil.writeToFile(ConfigLoader.folder.getAbsolutePath() + "/CustomRewards/Schematics/" + fileName, gson.toJson(json));
    }

    public static CustomSchematic loadCustomSchematic(String file, int xOffSet, int yOffSet, int zOffSet, float delay, boolean falling, boolean relativeToPlayer, boolean includeAirBlocks) {
        JsonElement elem = FileUtil.readJsonfromFile(ConfigLoader.folder.getAbsolutePath() + "/CustomRewards/Schematics/" + file);
        return SchematicUtil.loadCustomSchematic(elem, xOffSet, yOffSet, zOffSet, delay, falling, relativeToPlayer, includeAirBlocks);
    }

    public static CustomSchematic loadCustomSchematic(JsonElement elem, int xOffSet, int yOffSet, int zOffSet, float delay, boolean falling, boolean relativeToPlayer, boolean includeAirBlocks) {

        if (elem == null)
            return null;
        JsonObject json = elem.getAsJsonObject();
        List<OffsetBlock> offsetBlocks = new ArrayList<>();
        JsonObject info = json.get("Schematic Data").getAsJsonObject();
        int xSize = info.get("xSize").getAsInt();
        int ySize = info.get("ySize").getAsInt();
        int zSize = info.get("zSize").getAsInt();
        List<SimpleEntry<Integer, String>> blockDataIds = new ArrayList<>();

        JsonArray blockDataArray = json.get("Block Data").getAsJsonArray();
        for (JsonElement i : blockDataArray) {
            JsonObject index = i.getAsJsonObject();
            for (Entry<String, JsonElement> obj : index.entrySet())
                blockDataIds.add(new SimpleEntry<>(obj.getValue().getAsInt(), obj.getKey()));
        }

        int index = 0;
        List<Integer> blockArray = new ArrayList<>();
        for (JsonElement ids : json.get("Blocks").getAsJsonArray()) {
            String entry = ids.getAsString();
            String[] parts = entry.split("x");
            int id = Integer.parseInt(parts[0]);
            int recurse = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
            for (int i = 0; i < recurse; i++)
                blockArray.add(id);
        }

        for (int yOff = 0; yOff < ySize; yOff++) {
            for (int xOff = (xSize / 2) - xSize; xOff < (xSize / 2); xOff++) {
                for (int zOff = (zSize / 2) - zSize; zOff < (zSize / 2); zOff++) {
                    int id = blockArray.get(index);
                    String blockData = "";
                    for (SimpleEntry<Integer, String> entry : blockDataIds) {
                        if (entry.getKey() == id) {
                            blockData = entry.getValue();
                            break;
                        }
                    }

                    String[] dataParts = blockData.split(":");
                    Material m = CraftMagicNumbers.getMaterial(Block.REGISTRY.get(new MinecraftKey(dataParts[0], dataParts[1])));
                    MaterialData data = new MaterialData(m);
                    //TODO Still need to try and find a better way to do this
                    data.setData(Byte.parseByte(dataParts[2]));
                    OffsetBlock osb = new OffsetBlock(xOff + xOffSet, yOff + yOffSet, zOff + zOffSet, m, data, falling, 0);
                    osb.setRelativeToPlayer(relativeToPlayer);
                    offsetBlocks.add(osb);
                    index++;
                }
            }
        }

        JsonArray teArray = json.get("TileEntities").getAsJsonArray();
        for (JsonElement i : teArray) {
            for (Entry<String, JsonElement> obj : i.getAsJsonObject().entrySet()) {
                String teData = obj.getKey();
                for (JsonElement ids : obj.getValue().getAsJsonArray()) {
                    int id = ids.getAsInt();
                    OffsetBlock osb = offsetBlocks.get(id);
                    OffsetTileEntity oste = OffsetBlockToTileEntity(osb, teData);
                    if (oste != null)
                        offsetBlocks.set(id, oste);
                }
            }
        }

        for (int i = offsetBlocks.size() - 1; i >= 0; i--) {
            OffsetBlock osb = offsetBlocks.get(i);
            if (osb.getMaterial() == Material.AIR && !includeAirBlocks)
                offsetBlocks.remove(i);
        }

        return new CustomSchematic(offsetBlocks, xSize, ySize, zSize, relativeToPlayer, includeAirBlocks, delay);
    }

    public static CustomSchematic loadLegacySchematic(String fileName, int xoff, int yoff, int zoff, float delay, boolean falling, boolean relativeToPlayer, boolean includeAirBlocks) {
        File schematic = new File(ConfigLoader.folder.getParentFile().getAbsolutePath() + "/CustomRewards/Schematics/" + fileName);
        NBTTagCompound nbtdata;
        try {
            FileInputStream is = new FileInputStream(schematic);
            nbtdata = NBTCompressedStreamTools.a(is);
            is.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        short width = nbtdata.getShort("Width");
        short height = nbtdata.getShort("Height");
        short length = nbtdata.getShort("Length");

        byte[] blocks = nbtdata.getByteArray("Blocks");
        byte[] data = nbtdata.getByteArray("Data");
        List<OffsetBlock> offsetBlocks = new ArrayList<>();

        NBTTagList tileentities = nbtdata.getList("TileEntities", 10);

        int i = 0;
        short halfLength = (short) (length / 2);
        short halfWidth = (short) (width / 2);

        for (int yy = 0; yy < height; yy++) {
            for (int zz = 0; zz < length; zz++) {
                for (int xx = 0; xx < width; xx++) {
                    int j = blocks[i];
                    if (j < 0)
                        j = 128 + (128 + j);

                    Material m = CraftMagicNumbers.getMaterial(Block.getById(j));
                    if (m != Material.AIR) {
                        MaterialData materialData = new MaterialData(m);
                        materialData.setData(data[i]);
                        OffsetBlock block = new OffsetBlock(halfWidth - xx, yy, halfLength - zz, m, materialData, falling);
                        block.setRelativeToPlayer(relativeToPlayer);
                        offsetBlocks.add(block);
                    }
                    
                    i++;
                }
            }
        }

        if (tileentities != null) {
            for (int i1 = 0; i1 < tileentities.size(); ++i1) {
                NBTTagCompound nbttagcompound4 = tileentities.get(i1);
                TileEntity tileentity = TileEntity.a(null, nbttagcompound4);

                if (tileentity != null) {
                    Material m = null;
                    for (OffsetBlock osb : offsetBlocks)
                        if (osb.xOff == tileentity.getPosition().getX() && osb.yOff == tileentity.getPosition().getY() && osb.zOff == tileentity.getPosition().getZ())
                            m = osb.getMaterial();
                    
                    if (m == null)
                        m = Material.STONE;

                    MaterialData materialData = new MaterialData(m);
                    materialData.setData(data[i1]);
                    OffsetTileEntity block = new OffsetTileEntity(tileentity.getPosition().getX(), tileentity.getPosition().getY(), tileentity.getPosition().getZ(), m, materialData, nbttagcompound4, falling);
                    block.setRelativeToPlayer(relativeToPlayer);
                    offsetBlocks.add(block);
                }
            }
        }

        return new CustomSchematic(offsetBlocks, width, height, length, relativeToPlayer, includeAirBlocks, delay);
    }
}
