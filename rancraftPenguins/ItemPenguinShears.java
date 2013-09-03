package rancraftPenguins;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemPenguinShears extends ItemShears
{
    public ItemPenguinShears(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
        this.setMaxDamage(576);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    @Override
	public void registerIcons(IconRegister iconRegister)
	{
		/* how it appears in player's inventory */
	    itemIcon = iconRegister.registerIcon(RanCraftPenguins.modID + ":pengShears");
	}
}
