package rancraftPenguins;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

/* For setting the icon of items that don't use their own class file */
public class ItemRCSimple extends Item {

	private String thisIconName;
	
    public ItemRCSimple(int par1,String par2) {
		super(par1);
		
		thisIconName = RanCraftPenguins.modID + ":" + par2;
	}

    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
    	itemIcon = par1IconRegister.registerIcon(thisIconName);
	}
}
