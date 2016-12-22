package chanceCubes.items;

import chanceCubes.CCubesCore;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseChanceCubesItem extends Item {

    private String itemName = "Chance_Cube_Unnamed";
    private List<String> lore = Lists.newArrayList();

    public BaseChanceCubesItem(String name) {
        itemName = name;
        this.setUnlocalizedName(name);
        this.setRegistryName(CCubesCore.MODID, this.itemName);
        this.setCreativeTab(CCubesCore.modTab);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.addAll(lore);
    }

    public void addLore(String info) {
        lore.add(info);
    }

    public String getItemName() {
        return this.itemName;
    }
}