package rancraftPenguins;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.IArmorTextureProvider;
import net.minecraft.world.World;

// This Is Your Class For Your Armor, Your Have To Extend ItemArmor For Minecraft To Register(Have The Required Methods And Values) This As Armor
// Then You Need To Implement IArmorTextureProvider For It To Be Able To Render
public class PenguinArmor extends ItemArmor {
	// This Is Your Constructor, The First Param Is Item ID, The Second Is Its Material, Third is Icon texture, 
	// The Fourth Is The Rendering ID, and The Final Is The Type (i.e. Helmet, Chestplate, Boots, Legs)
	private String thisIconName;

	public PenguinArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, String par3IconTexture, int par4RenderingID, int par5ArmorType){
		super(par1, par2EnumArmorMaterial, par4RenderingID, par5ArmorType);
		thisIconName = RanCraftPenguins.modID + ":" + par3IconTexture;
	}
	
	//@Override  This could improve efficiency if I can get it to work instead of checking every tick
/*	public void onArmorTickUpdate(EntityPlayer player){
		ItemStack boots = player.getCurrentItemOrArmor(0);
		World world = player.worldObj;
		
		super.onArmorTickUpdate(world, player, boots);

	  	//player takes no fall damage when wearing boots
		if(boots != null) {

			if(boots.getItem() == RanCraftPenguins.PenguinBootsCloud){
				player.fallDistance = 0.0F;
			}
		}
		//System.out.printf("updated fallDistance to %f\n", player.fallDistance);
	}
*/
	
	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
	     itemIcon = par1IconRegister.registerIcon(thisIconName);
	}

	// This Sets What The Armor Is Supposed To Render Like
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer){
		//String armorPath = "/rancraftPenguins/client/armor/";
		String armorPath = RanCraftPenguins.modID + ":textures/armor/";

		if(stack.itemID == RanCraftPenguins.PenguinHatEmp.itemID || stack.itemID == RanCraftPenguins.PenguinTunic.itemID || stack.itemID == RanCraftPenguins.PenguinFlippers.itemID){
			return armorPath + "penguinhideemp.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinPants.itemID){
			return armorPath + "penguinhide_2.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatKing.itemID){
			return armorPath + "penguinhideking.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatAdel.itemID){
			return armorPath + "penguinhideadel.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatGal.itemID){
			return armorPath + "penguinhidegal.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatMag.itemID){
			return armorPath + "penguinhidemag.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatLB.itemID){
			return armorPath + "penguinhidelb.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatWF.itemID){
			return armorPath + "penguinhidewf.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatYE.itemID){
			return armorPath + "penguinhideye.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatCS.itemID){
			return armorPath + "penguinhidecs.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinHatGent.itemID){
			return armorPath + "penguinhidegent.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinBootsCloud.itemID){
			return armorPath + "penguinhidecloud.png";
		}
		if(stack.itemID == RanCraftPenguins.PenguinFlameLeggings.itemID){
			return armorPath + "penguinflamhide_2.png";
		}
		return armorPath + "penguinflamhide_1.png"; // default
	}

	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
    {
		//System.out.printf("getIsRepairable returns %b.\n", (RanCraftPenguins.PengSkinBlack.itemID == par2ItemStack.itemID));
		//System.out.printf("black skin ID returns %d.\n", RanCraftPenguins.PengSkinBlack.itemID);
		//System.out.printf("item to use ID returns %d.\n", par2ItemStack.itemID);
    	//return RanCraftPenguins.PengSkinBlack.itemID == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    	return getArmorRepairItem(par1ItemStack) == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
    }

	// Determines which items can be used to repair armor
	private int getArmorRepairItem(ItemStack stack)
	{
		int retVal = 0;

		if(stack.itemID == RanCraftPenguins.PenguinFlameMask.itemID
			|| stack.itemID == RanCraftPenguins.PenguinFlameChestPlate.itemID
			|| stack.itemID == RanCraftPenguins.PenguinFlameLeggings.itemID
			|| stack.itemID == RanCraftPenguins.PenguinFlameFlippers.itemID){
			retVal = RanCraftPenguins.PengScaleRed.itemID;
		} else {
			if(stack.itemID == RanCraftPenguins.PenguinBootsCloud.itemID){
				retVal = RanCraftPenguins.PengDownCloud.itemID;
			} else {
				if(stack.itemID == RanCraftPenguins.PenguinHatLB.itemID){
					retVal = RanCraftPenguins.PengSkinBlue.itemID;
				} else {
					if(stack.itemID == RanCraftPenguins.PenguinHatYE.itemID
						|| stack.itemID == RanCraftPenguins.PenguinHatGal.itemID
						|| stack.itemID == RanCraftPenguins.PenguinHatWF.itemID){
						retVal = RanCraftPenguins.PengSkinBrown.itemID;
					} else {
						retVal = RanCraftPenguins.PengSkinBlack.itemID;
					}
				}
			}
		}

		return retVal;
	}
}
