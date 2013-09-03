package rancraftPenguins;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumToolMaterial;
import net.minecraftforge.common.BiomeDictionary;
import static net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = "rancraftpenguins", name = "Rancraft Penguins", version = "1.6.2n")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = {
		"RanCraftExt", "RanCraftAL", "RanCraftFD", "RanCraftFRS", "RanCraftFRSR" }, packetHandler = RCPacketHandler.class)
public class RanCraftPenguins {

	public static final String modID = "rancraftpenguins"; // must match modid above!

	// The instance of your mod that Forge uses.
	@Instance("RanCraftPenguins")
	public static RanCraftPenguins instance;

	// Says where the client and server 'proxy' code is loaded.
	@SidedProxy(clientSide = "rancraftPenguins.client.ClientProxy", serverSide = "rancraftPenguins.CommonProxy")
	public static CommonProxy proxy;
	public static LocalizePenguins loc;

	static EnumToolMaterial WEAPONPENGUINITE0 = EnumHelper.addToolMaterial(
			"Penguinite", 0, 60, 2.0F, -3, 15);
	static EnumToolMaterial WEAPONPENGUINITE1 = EnumHelper.addToolMaterial(
			"Penguinite", 0, 60, 2.0F, 2, 15);

	// (name, harvestLevel, maxUses, efficiency, damage, enchantability)
	/*
	name, durability, protection, enchantability
	CLOTH(5, new int[]{1, 3, 2, 1}, 15),
    CHAIN(15, new int[]{2, 5, 4, 1}, 12),
    IRON(15, new int[]{2, 6, 5, 2}, 9),
    GOLD(7, new int[]{2, 5, 3, 1}, 25),
    DIAMOND(33, new int[]{3, 8, 6, 3}, 10);
	 */

	static EnumArmorMaterial ARMORPENGUINITE1 = EnumHelper.addArmorMaterial(
			"ARMORPENGUINITE1", 7, new int[] { 2, 4, 3, 2 }, 13); // name, dur,
																	// reduction[],
																	// enchant
	static EnumArmorMaterial ARMORPENGUINITE2 = EnumHelper.addArmorMaterial(
			"ARMORPENGUINITE2", 18, new int[] { 3, 6, 5, 3 }, 15); // name, dur,
																	// reduction[],
																	// enchant
	static EnumArmorMaterial ARMORPENGUINITE3 = EnumHelper.addArmorMaterial(
			"ARMORPENGUINITE3", 26, new int[] { 3, 8, 6, 3 }, 17); // name, dur,
																	// reduction[],
																	// enchant

	//customCraftingMaterial(PengScaleRed);

	public static Item PengScaleRed, PengDownCloud, PengFeatherBlack,
			PengFeatherBlue, PengFeatherBrown;
	public static Item PengFeatherBrownStriped, PengFeatherStriped,
			PengFeatherWhite, PengFeatherYellow;
	public static Item PengSkinBlack, PengSkinBlue, PengSkinLightBlue,
			PengSkinBrown, PengSkinRed, PenguinCrown, FishMagma, PenguinSauce;
	public static Item PenguinHatEmp, PenguinHatKing, PenguinHatAdel,
			PenguinHatYE, PenguinHatGal;
	public static Item PenguinHatMag, PenguinHatWF, PenguinHatLB,
			PenguinHatGent, PenguinHatCS;
	public static Item PenguinTunic, PenguinPants, PenguinFlippers, PenguinBootsCloud;
	public static Item PenguinFlameMask, PenguinFlameChestPlate,
			PenguinFlameLeggings, PenguinFlameFlippers;
	public static Item PenguinBow, PenguinFlameRepeater, PenguinKatanaD, 
			PenguinKatanaS, PenguinShuriken, PenguinFishingRod, PenguinShears;

	public static int PengScaleRedID, PengDownCloudID, PengFeatherBlackID,
			PengFeatherBlueID, PengFeatherBrownID;
	public static int PengSkinBlackID, PengSkinBlueID, PengSkinLightBlueID,
			PengSkinBrownID, PengSkinRedID, PenguinCrownID, FishMagmaID, PenguinSauceID;
	public static int PengFeatherBrownStripedID, PengFeatherStripedID,
			PengFeatherWhiteID, PengFeatherYellowID;
	public static int PenguinHatEmpID, PenguinHatKingID, PenguinHatAdelID,
			PenguinHatYEID, PenguinHatGalID;
	public static int PenguinHatMagID, PenguinHatWFID, PenguinHatLBID,
			PenguinHatGentID, PenguinHatCSID;
	public static int PenguinTunicID, PenguinPantsID, PenguinFlippersID, PenguinBootsCloudID;
	public static int PenguinFlameMaskID, PenguinFlameChestPlateID,
			PenguinFlameLeggingsID, PenguinFlameFlippersID;
	public static int PenguinBowID, PenguinFlameRepeaterID, PenguinKatanaDID,
			PenguinKatanaSID, PenguinShurikenID, PenguinFishingRodID, PenguinShearsID;

	public static int PengEmpSpawnFreq, PengKingSpawnFreq, PengAdelSpawnFreq,
			PengYESpawnFreq;
	public static int PengGalSpawnFreq, PengMagSpawnFreq, PengWFSpawnFreq,
			PengLBSpawnFreq;
	public static int PengGentSpawnFreq, PengCSSpawnFreq;
	public static int PengFlamSpawnFreq, PengNinjSpawnFreq, PengCloudSpawnFreq;

	public static int pengQuietInt; // chance that a calm penguin will remain
									// quiet
	private static int pengCallFreqInt; // how often healthy penguins call

	//@PreInit
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		// System.out.printf("Config file is %s.\n",
		// event.getSuggestedConfigurationFile().getName());
		config.load();

		PengScaleRedID = config.getItem("Penguin Scale Red", 4200).getInt();
		PengFeatherBlackID = config.getItem("Penguin Feather Black ID", 4201)
				.getInt();
		PengFeatherBlueID = config.getItem("Penguin Feather Blue ID", 4202)
				.getInt();
		PengFeatherBrownID = config.getItem("Penguin Feather Brown ID", 4203)
				.getInt();
		PengFeatherBrownStripedID = config.getItem(
				"Penguin Feather Brown Striped ID", 4207).getInt();
		PengFeatherStripedID = config.getItem("Penguin Feather Striped ID",
				4204).getInt();
		PengFeatherWhiteID = config.getItem("Penguin Feather White ID", 4205)
				.getInt();
		PengFeatherYellowID = config.getItem("Penguin Feather Yellow ID", 4206)
				.getInt();
		PengDownCloudID = config.getItem("Penguin Down Cloud ID", 4208)
				.getInt();

		PengSkinBlackID = config.getItem("Penguin Skin Black ID", 4251)
				.getInt();
		PengSkinBlueID = config.getItem("Penguin Skin Blue ID", 4252).getInt();
		PengSkinRedID = config.getItem("Penguin Skin Red ID", 4253).getInt();
		PengSkinBrownID = config.getItem("Penguin Skin Brown ID", 4254)
				.getInt();
		PengSkinLightBlueID = config
				.getItem("Penguin Skin Light Blue ID", 4255).getInt();
		FishMagmaID = config.getItem("Magma Fish ID", 4231).getInt();
		PenguinSauceID = config.getItem("Penguin Sauce ID", 4232).getInt();
		PenguinShearsID = config.getItem("Penguin Shears ID", 4232).getInt();
		PenguinCrownID = config.getItem("Penguin Crown ID", 4280).getInt();

		PenguinHatEmpID = config.getItem("Penguin Hat Emperor ID", 4300)
				.getInt();
		PenguinHatKingID = config.getItem("Penguin Hat King ID", 4301).getInt();
		PenguinHatAdelID = config.getItem("Penguin Hat Adélie ID", 4305)
				.getInt();
		PenguinHatYEID = config.getItem("Penguin Hat Yellow-Eyed ID", 4304)
				.getInt();
		PenguinHatMagID = config.getItem("Penguin Hat Magellanic ID", 4303)
				.getInt();
		PenguinHatGalID = config.getItem("Penguin Hat Galápagos ID", 4306)
				.getInt();
		PenguinHatWFID = config.getItem("Penguin Hat White Flippered ID", 4307)
				.getInt();
		PenguinHatLBID = config.getItem("Penguin Hat Little Blue ID", 4302)
				.getInt();
		PenguinHatGentID = config.getItem("Penguin Hat Gentoo ID", 4308)
				.getInt();
		PenguinHatCSID = config.getItem("Penguin Hat Chinstrap ID", 4309)
				.getInt();

		PenguinTunicID = config.getItem("Penguin Tunic ID", 4350).getInt();
		PenguinPantsID = config.getItem("Penguin Pants ID", 4351).getInt();
		PenguinFlippersID = config.getItem("Penguin Flippers ID", 4352)
				.getInt();
		PenguinBootsCloudID = config.getItem("Penguin Boots Cloud ID", 4353)
				.getInt();
		PenguinFlameMaskID = config.getItem("Penguin Hat Flame ID", 4400)
				.getInt();
		PenguinFlameChestPlateID = config.getItem(
				"Penguin Chestplate Flame ID", 4401).getInt();
		PenguinFlameLeggingsID = config.getItem("Penguin Leggings Flame ID",
				4402).getInt();
		PenguinFlameFlippersID = config.getItem("Penguin Flippers Flame ID",
				4403).getInt();

		PenguinBowID = config.getItem("Penguin Bow ID", 4501).getInt();
		PenguinFlameRepeaterID = config.getItem("Penguin Flame Repeater ID",
				4511).getInt();
		PenguinKatanaDID = config.getItem("Penguin's Katana ID", 4512).getInt();
		PenguinKatanaSID = config.getItem("Penguin's Katana Sheathed ID", 4513)
				.getInt();
		PenguinShurikenID = config.getItem("Penguin's Shuriken ID", 4514)
				.getInt();
		PenguinFishingRodID = config.getItem("Penguin Fishing Rod ID",
				4515).getInt();

		PengEmpSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Emperor", 20).getInt(20);
		PengKingSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency King", 16).getInt(16);
		PengAdelSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Adélie", 30).getInt(30);
		PengYESpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Yellow-Eyed", 20).getInt(20);
		PengMagSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Magellanic", 20).getInt(20);
		PengGalSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Galápagos", 20).getInt(20);
		PengWFSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency White-Flippered", 20).getInt(20);
		PengLBSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Little Blue", 30).getInt(30);
		PengGentSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Gentoo", 20).getInt(20);
		PengCSSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Chinstrap", 20).getInt(20);
		PengFlamSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Flame", 40).getInt(40);
		PengNinjSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Ninja", 20).getInt(20);
		PengCloudSpawnFreq = config.get(config.CATEGORY_GENERAL,
				"Spawn Frequency Cloud Penguin", 20).getInt(20);

		pengCallFreqInt = config.get(config.CATEGORY_GENERAL,
				"How often healthy penguins call (0-9)", 9).getInt(9);
		switch (pengCallFreqInt) {
		case 0:
			pengQuietInt = 255; // 1 in 255 chance of a call
			break;
		case 1 - 9:
			pengQuietInt = 11 - pengCallFreqInt; // 9 is default pengCallFreqInt
													// so 2 is default
													// pengQuietInt
			break;
		default:
			pengQuietInt = 2; // out of range values default to pengQuietInt of
								// 2
			break;
		}
		// System.out.printf("pengQuietInt is now %d\n", pengQuietInt);

		config.save();

		proxy.registerSounds();

		// For better looking roll-over labels in English AND localization!
		LocalizePenguins.localize();

		// second arg of ItemPenguinStuff constructor is actual filename of the
		// texture png
		PengScaleRed = new ItemRCSimple(PengScaleRedID, "pengScaleFlam")
				.setUnlocalizedName("PSCR").setCreativeTab(
						CreativeTabs.tabMaterials);
		PengFeatherBlack = new ItemRCSimple(PengFeatherBlackID,
				"pengFeatherBlack").setUnlocalizedName("PFBLK").setCreativeTab(
				CreativeTabs.tabMaterials);
		PengFeatherBlue = new ItemRCSimple(PengFeatherBlueID, "pengFeatherBlue")
				.setUnlocalizedName("PFBLU").setCreativeTab(
						CreativeTabs.tabMaterials);
		PengFeatherBrown = new ItemRCSimple(PengFeatherBrownID,
				"pengFeatherBrown").setUnlocalizedName("PFBR").setCreativeTab(
				CreativeTabs.tabMaterials);
		PengFeatherBrownStriped = new ItemRCSimple(PengFeatherBrownStripedID,
				"pengFeatherBrownStriped").setUnlocalizedName("PFBRST")
				.setCreativeTab(CreativeTabs.tabMaterials);
		PengFeatherStriped = new ItemRCSimple(PengFeatherStripedID,
				"pengFeatherStriped").setUnlocalizedName("PFST")
				.setCreativeTab(CreativeTabs.tabMaterials);
		PengFeatherWhite = new ItemRCSimple(PengFeatherWhiteID,
				"pengFeatherWhite").setUnlocalizedName("PFW").setCreativeTab(
				CreativeTabs.tabMaterials);
		PengFeatherYellow = new ItemRCSimple(PengFeatherYellowID,
				"pengFeatherYellow").setUnlocalizedName("PFY").setCreativeTab(
				CreativeTabs.tabMaterials);
		PengDownCloud = new ItemRCSimple(PengDownCloudID, "pengDownCloud")
				.setUnlocalizedName("PDCL").setCreativeTab(
						CreativeTabs.tabMaterials);

		PengSkinBlack = new ItemRCSimple(PengSkinBlackID, "pengSkinBlack")
				.setUnlocalizedName("PSBLK").setCreativeTab(
						CreativeTabs.tabMaterials);
		PengSkinBlue = new ItemRCSimple(PengSkinBlueID, "pengSkinBlue")
				.setUnlocalizedName("PSBLU").setCreativeTab(
						CreativeTabs.tabMaterials);
		PengSkinLightBlue = new ItemRCSimple(PengSkinLightBlueID,
				"pengSkinLightBlue").setUnlocalizedName("PSLBLU")
				.setCreativeTab(CreativeTabs.tabMaterials);
		PengSkinRed = new ItemRCSimple(PengSkinRedID, "pengSkinFlam")
				.setUnlocalizedName("PSKR").setCreativeTab(
						CreativeTabs.tabMaterials);
		PengSkinBrown = new ItemRCSimple(PengSkinBrownID, "pengSkinBrown")
				.setUnlocalizedName("PSBR").setCreativeTab(
						CreativeTabs.tabMaterials);
		PenguinCrown = new ItemRCSimple(PenguinCrownID, "pengCrown")
				.setUnlocalizedName("PCR").setCreativeTab(
						CreativeTabs.tabMaterials);
		FishMagma = new ItemRCSimple(FishMagmaID, "magmaFish")
				.setUnlocalizedName("MF").setCreativeTab(
						CreativeTabs.tabMaterials);
		PenguinSauce = new ItemRCSimple(PenguinSauceID, "pengSauce")
				.setUnlocalizedName("PSC").setCreativeTab(
						CreativeTabs.tabMaterials);

		PenguinHatEmp = new PenguinArmor(PenguinHatEmpID, ARMORPENGUINITE1,
				"pengHatEmp", 3, 0).setUnlocalizedName("EPH");
		PenguinHatKing = new PenguinArmor(PenguinHatKingID, ARMORPENGUINITE1,
				"pengHatKing", 3, 0).setUnlocalizedName("KPH");
		PenguinHatAdel = new PenguinArmor(PenguinHatAdelID, ARMORPENGUINITE1,
				"pengHatAdel", 3, 0).setUnlocalizedName("APH");
		PenguinHatYE = new PenguinArmor(PenguinHatYEID, ARMORPENGUINITE1,
				"pengHatYE", 3, 0).setUnlocalizedName("YEPH");
		PenguinHatLB = new PenguinArmor(PenguinHatLBID, ARMORPENGUINITE1,
				"pengHatLB", 3, 0).setUnlocalizedName("LBPH");
		PenguinHatMag = new PenguinArmor(PenguinHatMagID, ARMORPENGUINITE1,
				"pengHatMag", 3, 0).setUnlocalizedName("MPH");
		PenguinHatGal = new PenguinArmor(PenguinHatGalID, ARMORPENGUINITE1,
				"pengHatGal", 3, 0).setUnlocalizedName("GPH");
		PenguinHatWF = new PenguinArmor(PenguinHatWFID, ARMORPENGUINITE1,
				"pengHatWF", 3, 0).setUnlocalizedName("WFPH");
		PenguinHatGent = new PenguinArmor(PenguinHatGentID, ARMORPENGUINITE1,
				"pengHatGent", 3, 0).setUnlocalizedName("GTPH");
		PenguinHatCS = new PenguinArmor(PenguinHatCSID, ARMORPENGUINITE1,
				"pengHatCS", 3, 0).setUnlocalizedName("CSPH");

		PenguinTunic = new PenguinArmor(PenguinTunicID, ARMORPENGUINITE1,
				"pengTunic", 3, 1).setUnlocalizedName("PT");
		PenguinPants = new PenguinArmor(PenguinPantsID, ARMORPENGUINITE1,
				"pengPants", 3, 2).setUnlocalizedName("PP");
		PenguinFlippers = new PenguinArmor(PenguinFlippersID, ARMORPENGUINITE1,
				"pengFlippers", 3, 3).setUnlocalizedName("PF");
		PenguinBootsCloud = new PenguinArmor(PenguinBootsCloudID, ARMORPENGUINITE1,
				"pengBootsCloud", 3, 3).setUnlocalizedName("CPB");

		PenguinFlameMask = new PenguinArmor(PenguinFlameMaskID,
				ARMORPENGUINITE2, "pengMask", 3, 0).setUnlocalizedName("FPM");
		PenguinFlameChestPlate = new PenguinArmor(PenguinFlameChestPlateID,
				ARMORPENGUINITE2, "pengChestPlate", 3, 1)
				.setUnlocalizedName("FPCP");
		PenguinFlameLeggings = new PenguinArmor(PenguinFlameLeggingsID,
				ARMORPENGUINITE2, "pengLeggings", 3, 2)
				.setUnlocalizedName("FPL");
		PenguinFlameFlippers = new PenguinArmor(PenguinFlameFlippersID,
				ARMORPENGUINITE2, "pengFlamFlippers", 3, 3)
				.setUnlocalizedName("FPF");

		PenguinBow = new ItemPenguinBow(PenguinBowID).setUnlocalizedName("PB")
				.setCreativeTab(CreativeTabs.tabCombat);
		PenguinFlameRepeater = new ItemPenguinFlameRepeater(
				PenguinFlameRepeaterID).setUnlocalizedName("PFR")
				.setCreativeTab(CreativeTabs.tabCombat);
		PenguinKatanaD = new ItemPenguinKatana(PenguinKatanaDID, WEAPONPENGUINITE1, 1)
				.setMaxStackSize(1).setUnlocalizedName("PKD")
				.setCreativeTab(CreativeTabs.tabCombat);
		PenguinKatanaS = new ItemPenguinKatana(PenguinKatanaSID, WEAPONPENGUINITE0, 0)
				.setMaxStackSize(1).setUnlocalizedName("PKS")
				.setCreativeTab(null);
		PenguinShuriken = new ItemPenguinShuriken(PenguinShurikenID)
				.setMaxStackSize(16).setUnlocalizedName("PS")
				.setCreativeTab(CreativeTabs.tabCombat);
		PenguinFishingRod = new ItemPenguinFishingRod(
				PenguinFishingRodID).setUnlocalizedName("PFRD")
				.setCreativeTab(CreativeTabs.tabTools);
		PenguinShears = new ItemPenguinShears(
				PenguinShearsID).setUnlocalizedName("PSS")
				.setCreativeTab(CreativeTabs.tabTools);
	
	}

	//@Init
	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {

		proxy.registerRenderers();

		// items won't show up in item list without this
		LanguageRegistry.addName(PenguinKatanaD, "Penguin's Katana");
		LanguageRegistry.addName(PenguinKatanaS, "Penguin's Katana Sheath");
		LanguageRegistry.addName(PenguinShuriken, "Penguin's Shuriken");
		LanguageRegistry.addName(PenguinFlameRepeater, "Flame Penguin Repeater");
		LanguageRegistry.addName(PenguinBow, "Penguin Bow");
		LanguageRegistry.addName(PenguinFishingRod, "Penguin Fishing Rod");
		LanguageRegistry.addName(PengScaleRed, "Flame Penguin Scale");
		LanguageRegistry.addName(PengFeatherBlack, "Black Penguin Feather");
		LanguageRegistry.addName(PengFeatherBlue, "Blue Penguin Feather");
		LanguageRegistry.addName(PengDownCloud, "Cloud Penguin Down");
		LanguageRegistry.addName(PengFeatherBrown, "Brown Penguin Feather");
		LanguageRegistry.addName(PengFeatherBrownStriped,
				"Brown Striped Penguin Feather");
		LanguageRegistry.addName(PengFeatherStriped, "Striped Penguin Feather");
		LanguageRegistry.addName(PengFeatherWhite, "White Penguin Feather");
		LanguageRegistry.addName(PengFeatherYellow, "Yellow Penguin Feather");
		LanguageRegistry.addName(PengSkinBlack, "Black Penguin Skin");
		LanguageRegistry.addName(PengSkinBlue, "Blue Penguin Skin");
		LanguageRegistry.addName(PengSkinLightBlue, "Light Blue Penguin Skin");
		LanguageRegistry.addName(PengSkinRed, "Red Penguin Skin");
		LanguageRegistry.addName(PengSkinBrown, "Brown Penguin Skin");
		LanguageRegistry.addName(PenguinCrown, "Penguin Crown");
		LanguageRegistry.addName(FishMagma, "Magma Fish");
		LanguageRegistry.addName(PenguinShears, "Penguin Shears");
		LanguageRegistry.addName(PenguinSauce, "Penguin Sauce");
		LanguageRegistry.addName(PenguinHatEmp, "Emperor Penguin Hat");
		LanguageRegistry.addName(PenguinHatKing, "King Penguin Hat");
		LanguageRegistry.addName(PenguinHatLB, "Little Blue Penguin Hat");
		LanguageRegistry.addName(PenguinHatMag, "Magellanic Penguin Hat");
		LanguageRegistry.addName(PenguinHatGal, "Galápagos Penguin Hat");
		LanguageRegistry.addName(PenguinHatWF, "White-Flippered Penguin Hat");
		LanguageRegistry.addName(PenguinHatYE, "Yellow-Eyed Penguin Hat");
		LanguageRegistry.addName(PenguinHatAdel, "Adélie Penguin Hat");
		LanguageRegistry.addName(PenguinHatGent, "Gentoo Penguin Hat");
		LanguageRegistry.addName(PenguinHatCS, "Chinstrap Penguin Hat");
		LanguageRegistry.addName(PenguinPants, "Penguin Pants");
		LanguageRegistry.addName(PenguinTunic, "Penguin Tunic");
		LanguageRegistry.addName(PenguinFlippers, "Penguin Flippers");
		LanguageRegistry.addName(PenguinBootsCloud, "Cloud Penguin Boots");
		LanguageRegistry.addName(PenguinFlameMask, "Flame Penguin Mask");
		LanguageRegistry.addName(PenguinFlameChestPlate,
				"Flame Penguin Chestplate");
		LanguageRegistry
				.addName(PenguinFlameLeggings, "Flame Penguin Leggings");
		LanguageRegistry
				.addName(PenguinFlameFlippers, "Flame Penguin Flippers");

		/* Handle Biome Types for spawning */
		BiomeGenBase[] biomesMountain = BiomeDictionary
				.getBiomesForType(Type.MOUNTAIN);
		BiomeGenBase[] biomesCoastal = BiomeDictionary
				.getBiomesForType(Type.BEACH);
		BiomeGenBase[] biomesWater = BiomeDictionary
				.getBiomesForType(Type.WATER);
		BiomeGenBase[] biomesFrozen = BiomeDictionary
				.getBiomesForType(Type.FROZEN);
		BiomeGenBase[] biomesNether = BiomeDictionary
				.getBiomesForType(Type.NETHER);

		SpawnListEntry spawnlistEmp = new SpawnListEntry(
				EntityPenguinEmp.class, PengEmpSpawnFreq, 3, 5);
		SpawnListEntry spawnlistKing = new SpawnListEntry(
				EntityPenguinKing.class, PengKingSpawnFreq, 3, 5);
		SpawnListEntry spawnlistCS = new SpawnListEntry(EntityPenguinCS.class,
				PengCSSpawnFreq, 3, 5);
		SpawnListEntry spawnlistAdel = new SpawnListEntry(
				EntityPenguinAdel.class, PengAdelSpawnFreq, 3, 5);
		SpawnListEntry spawnlistGent = new SpawnListEntry(
				EntityPenguinGent.class, PengGentSpawnFreq, 3, 5);
		SpawnListEntry spawnlistLB = new SpawnListEntry(EntityPenguinLB.class,
				PengLBSpawnFreq, 3, 5);
		SpawnListEntry spawnlistWF = new SpawnListEntry(EntityPenguinWF.class,
				PengWFSpawnFreq, 3, 5);
		SpawnListEntry spawnlistYE = new SpawnListEntry(EntityPenguinYE.class,
				PengYESpawnFreq, 3, 5);
		SpawnListEntry spawnlistMag = new SpawnListEntry(
				EntityPenguinMag.class, PengMagSpawnFreq, 3, 5);
		SpawnListEntry spawnlistGal = new SpawnListEntry(
				EntityPenguinGal.class, PengGalSpawnFreq, 3, 5);
		SpawnListEntry spawnlistFlam = new SpawnListEntry(
				EntityPenguinFlam.class, PengFlamSpawnFreq, 3, 5);
		SpawnListEntry spawnlistNinj = new SpawnListEntry(
				EntityPenguinNinj.class, PengNinjSpawnFreq, 1, 3);
		SpawnListEntry spawnlistCloud = new SpawnListEntry(EntityPenguinCloud.class, PengCloudSpawnFreq, 3, 5);

		int i = 0;
		for (i = 0; i < biomesCoastal.length; i++) {
			biomesCoastal[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistLB);
			biomesCoastal[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistWF);
			biomesCoastal[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistYE);
			biomesCoastal[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistMag);
			biomesCoastal[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistGal);
		}

		for (i = 0; i < biomesWater.length; i++) {
			biomesWater[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistLB);
			biomesWater[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistWF);
			biomesWater[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistYE);
			biomesWater[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistMag);
			biomesWater[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistGal);
		}

		for (i = 0; i < biomesNether.length; i++) {
			biomesNether[i].getSpawnableList(EnumCreatureType.monster).add(
					spawnlistFlam);
		}

		for (i = 0; i < biomesMountain.length; i++) {
			biomesMountain[i].getSpawnableList(EnumCreatureType.creature).add(
					spawnlistCloud);
		}

		for (i = 0; i < biomesFrozen.length; i++) {
			if (BiomeDictionary.isBiomeOfType(biomesFrozen[i], Type.FOREST)) {
				biomesFrozen[i].getSpawnableList(EnumCreatureType.creature)
						.add(spawnlistAdel);
				biomesFrozen[i].getSpawnableList(EnumCreatureType.creature)
						.add(spawnlistGent);
			} else {
				biomesFrozen[i].getSpawnableList(EnumCreatureType.creature)
						.add(spawnlistEmp);
				biomesFrozen[i].getSpawnableList(EnumCreatureType.creature)
						.add(spawnlistKing);
				biomesFrozen[i].getSpawnableList(EnumCreatureType.creature)
						.add(spawnlistCS);
				biomesFrozen[i].getSpawnableList(EnumCreatureType.monster).add(
						spawnlistNinj);
			}
		}

		// Registers the entities
		int shurikenID = 3; // ModLoader.getUniqueEntityId();
		/* registerModEntity gives entity an ID specific to this mod */
		EntityRegistry.registerModEntity(EntityPenguinShuriken.class,
				"PenguinShuriken", shurikenID, this, 48, 3, true);

		// little blue
		int pengLBID = 4; // ModLoader.getUniqueEntityId(); //Just local now, so
							// keep it low
		EntityRegistry.registerModEntity(EntityPenguinLB.class,
				"LittleBluePenguin", pengLBID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengLBID, EntityPenguinLB.class);
		EntityList.entityEggs.put(pengLBID, new EntityEggInfo(pengLBID,
				0x223b4d, 0xf7f7f7));

		// white-flippered
		int pengWFID = 5; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinWF.class,
				"White-FlipperedPenguin", pengWFID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengWFID, EntityPenguinWF.class);
		EntityList.entityEggs.put(pengWFID, new EntityEggInfo(pengWFID,
				0x555555, 0));

		// adélie
		int pengAdelID = 6; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinAdel.class,
				"AdéliePenguin", pengAdelID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengAdelID, EntityPenguinAdel.class);
		EntityList.entityEggs.put(pengAdelID, new EntityEggInfo(pengAdelID, 0,
				0xf7f7f7));

		// yellow-eyed
		int pengYEID = 7; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinYE.class,
				"Yellow-EyedPenguin", pengYEID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengYEID, EntityPenguinYE.class);
		EntityList.entityEggs.put(pengYEID, new EntityEggInfo(pengYEID,
				0x663333, 0xe7e7a7));

		// magellanic
		int pengMagID = 8; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinMag.class,
				"MagellanicPenguin", pengMagID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengMagID, EntityPenguinMag.class);
		EntityList.entityEggs.put(pengMagID, new EntityEggInfo(pengMagID,
				0xf7f7f7, 0));

		// gallapagos
		int pengGalID = 9; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinGal.class,
				"GalápagosPenguin", pengGalID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengGalID, EntityPenguinGal.class);
		EntityList.entityEggs.put(pengGalID, new EntityEggInfo(pengGalID,
				0xf7f7f7, 0x878787));

		// king
		int pengKingID = 10; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinKing.class,
				"KingPenguin", pengKingID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengKingID, EntityPenguinKing.class);
		EntityList.entityEggs.put(pengKingID, new EntityEggInfo(pengKingID, 0,
				0xf79900));

		// emperor
		int pengEmpID = 11; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinEmp.class,
				"EmperorPenguin", pengEmpID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengEmpID, EntityPenguinEmp.class);
		EntityList.entityEggs.put(pengEmpID, new EntityEggInfo(pengEmpID, 0,
				0xf7f7b7));

		// flame
		int pengFlamID = 12; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinFlam.class,
				"FlamePenguin", pengFlamID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengFlamID, EntityPenguinFlam.class);
		EntityList.entityEggs.put(pengFlamID, new EntityEggInfo(pengFlamID,
				0xa00f10, 0x878787));

		// ninja
		int pengNinjID = 13; // ModLoader.getUniqueEntityId();
		EntityRegistry.registerModEntity(EntityPenguinNinj.class,
				"NinjaPenguin", pengNinjID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengNinjID, EntityPenguinNinj.class);
		EntityList.entityEggs.put(pengNinjID, new EntityEggInfo(pengNinjID, 0,
				0));

		// gentoo
		int pengGentID = 14; // ModLoader.getUniqueEntityId(); //Just local now,
								// so keep it low
		EntityRegistry.registerModEntity(EntityPenguinGent.class,
				"GentooPenguin", pengGentID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengGentID, EntityPenguinGent.class);
		EntityList.entityEggs.put(pengGentID, new EntityEggInfo(pengGentID,
				0x151515, 0xa7a7a7));

		// chinstrap
		int pengCSID = 15; // ModLoader.getUniqueEntityId(); //Just local now,
							// so keep it low
		EntityRegistry.registerModEntity(EntityPenguinCS.class, "CSPenguin",
				pengCSID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengCSID, EntityPenguinCS.class);
		EntityList.entityEggs.put(pengCSID, new EntityEggInfo(pengCSID,
				0x050505, 0x777777));

		// cloud
		int pengCloudID = 16; // ModLoader.getUniqueEntityId(); //Just local
								// now, so keep it low
		EntityRegistry.registerModEntity(EntityPenguinCloud.class,
				"CloudPenguin", pengCloudID, this, 80, 3, false);
		EntityList.IDtoClassMapping.put(pengCloudID, EntityPenguinCloud.class);
		EntityList.entityEggs.put(pengCloudID, new EntityEggInfo(pengCloudID,
				0x60c0e0, 0xffffff));

		// Crafting Recipes
		GameRegistry.addRecipe(new ItemStack(PenguinFishingRod), "  X",
				" XY", "XZY", Character.valueOf('X'), PengSkinBlack,
				Character.valueOf('Y'), PengFeatherBlack, Character.valueOf('Z'),
				Item.fishingRod);
		GameRegistry.addRecipe(new ItemStack(PenguinFlameRepeater), "VWV",
				"XYZ", "VWV", Character.valueOf('V'), PengScaleRed,
				Character.valueOf('W'), PengSkinRed, Character.valueOf('X'),
				Item.ingotIron, Character.valueOf('Y'), Item.redstoneRepeater,
				Character.valueOf('Z'), PenguinBow);
		GameRegistry.addRecipe(new ItemStack(PenguinBow), " XY", "XVY", " XY",
				Character.valueOf('X'), PengSkinBlack, Character.valueOf('Y'),
				PengFeatherBlack, Character.valueOf('V'), Item.bow);
		GameRegistry.addRecipe(new ItemStack(PenguinHatEmp), "XYX", "Z Z",
				Character.valueOf('X'), PengFeatherBlack,
				Character.valueOf('Y'), PengSkinBlack, Character.valueOf('Z'),
				PengFeatherWhite);
		GameRegistry.addRecipe(new ItemStack(PenguinHatKing), " W ", "XYX",
				"Z Z", Character.valueOf('W'), PenguinCrown,
				Character.valueOf('X'), PengFeatherBlack,
				Character.valueOf('Y'), PengSkinBlack, Character.valueOf('Z'),
				PengFeatherWhite);
		GameRegistry.addShapelessRecipe(new ItemStack(PenguinHatKing),
				new ItemStack(PenguinCrown), new ItemStack(PenguinHatEmp));
		GameRegistry.addRecipe(new ItemStack(PenguinHatLB), "XYX", "X X",
				Character.valueOf('X'), PengFeatherBlue,
				Character.valueOf('Y'), PengSkinBlue);
		GameRegistry.addRecipe(new ItemStack(PenguinHatYE), "XYX", "Z Z",
				Character.valueOf('X'), PengFeatherBrown,
				Character.valueOf('Y'), PengSkinBrown, Character.valueOf('Z'),
				PengFeatherYellow);
		GameRegistry.addRecipe(new ItemStack(PenguinHatMag), "XYX", "X X",
				Character.valueOf('X'), PengFeatherStriped,
				Character.valueOf('Y'), PengSkinBlack);
		GameRegistry.addRecipe(new ItemStack(PenguinHatGal), "XYX", "Z Z",
				Character.valueOf('X'), PengFeatherBlack,
				Character.valueOf('Y'), PengSkinBlack, Character.valueOf('Z'),
				PengFeatherBrownStriped);
		GameRegistry.addRecipe(new ItemStack(PenguinHatWF), "XYX", "Z Z",
				Character.valueOf('X'), PengFeatherBrown,
				Character.valueOf('Y'), PengSkinBlack, Character.valueOf('Z'),
				PengFeatherBrownStriped);
		GameRegistry.addRecipe(new ItemStack(PenguinHatAdel), "XYX", "X X",
				Character.valueOf('X'), PengFeatherBlack,
				Character.valueOf('Y'), PengSkinBlack);
		GameRegistry.addRecipe(new ItemStack(PenguinHatGent), "XYX", "Z Z",
				Character.valueOf('X'), PengFeatherWhite,
				Character.valueOf('Y'), PengSkinBlack, Character.valueOf('Z'),
				PengFeatherBlack);
		GameRegistry.addRecipe(new ItemStack(PenguinHatCS), "XYX", "Z Z",
				Character.valueOf('X'), PengFeatherStriped,
				Character.valueOf('Y'), PengSkinBlack, Character.valueOf('Z'),
				PengFeatherBlack);

		GameRegistry.addRecipe(new ItemStack(PenguinFlameMask), "XYX", "XZX",
				Character.valueOf('X'), PengScaleRed, Character.valueOf('Y'),
				PengSkinRed, Character.valueOf('Z'), Block.thinGlass);

		GameRegistry.addRecipe(new ItemStack(PenguinTunic), "X X", "XYX",
				"XYX", Character.valueOf('X'), PengFeatherBlack,
				Character.valueOf('Y'), Block.cloth);
		GameRegistry.addRecipe(new ItemStack(PenguinFlameChestPlate), "X X",
				"XYX", "XYX", Character.valueOf('X'), PengScaleRed,
				Character.valueOf('Y'), Block.cloth);
		GameRegistry.addRecipe(new ItemStack(PenguinPants), "ZYZ", "X X",
				"W W", Character.valueOf('W'), PengFeatherBlack,
				Character.valueOf('X'), PengFeatherBlue,
				Character.valueOf('Y'), PengSkinBlue, Character.valueOf('Z'),
				PengSkinBlack);
		GameRegistry.addRecipe(new ItemStack(PenguinFlameLeggings), "XXX",
				"Y Y", "Y Y", Character.valueOf('X'), PengSkinRed,
				Character.valueOf('Y'), PengScaleRed);
		GameRegistry.addRecipe(new ItemStack(PenguinFlippers), "X X", "Y Y",
				"Y Y", Character.valueOf('X'), PengSkinBlack,
				Character.valueOf('Y'), PengFeatherBlack);
		GameRegistry.addRecipe(new ItemStack(PenguinBootsCloud), "X X", "Y Y",
				"Y Y", Character.valueOf('X'), PengSkinLightBlue,
				Character.valueOf('Y'), PengDownCloud);
		GameRegistry.addRecipe(new ItemStack(PenguinFlameFlippers), "X X",
				"Y Y", "Y Y", Character.valueOf('X'), PengSkinRed,
				Character.valueOf('Y'), PengScaleRed);
		
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherWhite);
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherBlue);
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherStriped);
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherBrownStriped);
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherBlack);
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherBrown);
		GameRegistry.addRecipe(new ItemStack(Item.arrow, 4), "X",
				"Y", "Z", Character.valueOf('X'), Item.flint,
				Character.valueOf('Y'), Item.stick, Character.valueOf('Z'), PengFeatherYellow);
		
		GameRegistry.addRecipe(new ItemStack(Item.itemFrame, 1), "XXX",
				"XYX", "XXX", Character.valueOf('X'), Item.stick,
				Character.valueOf('Y'), PengSkinBlack);
		GameRegistry.addRecipe(new ItemStack(Item.itemFrame, 1), "XXX",
				"XYX", "XXX", Character.valueOf('X'), Item.stick,
				Character.valueOf('Y'), PengSkinBlue);
		GameRegistry.addRecipe(new ItemStack(Item.itemFrame, 1), "XXX",
				"XYX", "XXX", Character.valueOf('X'), Item.stick,
				Character.valueOf('Y'), PengSkinLightBlue);
		GameRegistry.addRecipe(new ItemStack(Item.itemFrame, 1), "XXX",
				"XYX", "XXX", Character.valueOf('X'), Item.stick,
				Character.valueOf('Y'), PengSkinRed);
		GameRegistry.addRecipe(new ItemStack(Item.itemFrame, 1), "XXX",
				"XYX", "XXX", Character.valueOf('X'), Item.stick,
				Character.valueOf('Y'), PengSkinBrown);
		
		GameRegistry.addRecipe(new ItemStack(PenguinShears, 1), " X ", "YZX", " Y ",
				Character.valueOf('X'), PengFeatherBlack, Character.valueOf('Y'), PengSkinBlack,
				Character.valueOf('Z'), Item.shears);

		GameRegistry.addShapelessRecipe(new ItemStack(FishMagma, 4),
				new ItemStack(Item.fishRaw, 1, 0), new ItemStack(Item.fishRaw,
						1, 0), new ItemStack(Item.fishRaw, 1, 0),
				new ItemStack(Item.fishRaw, 1, 0), new ItemStack(
						Item.magmaCream, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(FishMagma, 1),
				new ItemStack(Item.fishRaw, 1, 0), new ItemStack(
						Item.magmaCream, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(PenguinSauce, 1),
				new ItemStack(Item.fireballCharge, 1, 0), new ItemStack(
						Item.bowlEmpty, 1, 0), new ItemStack(Item.sugar, 1, 0), new ItemStack(
						Block.mushroomBrown, 1, 0), new ItemStack(PengScaleRed, 1, 0));

		GameRegistry.addShapelessRecipe(new ItemStack(Item.book, 1),
				new ItemStack(PengSkinBlack, 1, 0), new ItemStack(Item.paper,
						1, 0), new ItemStack(Item.paper, 1, 0), new ItemStack(
						Item.paper, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.book, 1),
				new ItemStack(PengSkinBlue, 1, 0), new ItemStack(Item.paper, 1,
						0), new ItemStack(Item.paper, 1, 0), new ItemStack(
						Item.paper, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.book, 1),
				new ItemStack(PengSkinLightBlue, 1, 0), new ItemStack(Item.paper, 1,
						0), new ItemStack(Item.paper, 1, 0), new ItemStack(
						Item.paper, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.book, 1),
				new ItemStack(PengSkinBrown, 1, 0), new ItemStack(Item.paper,
						1, 0), new ItemStack(Item.paper, 1, 0), new ItemStack(
						Item.paper, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(Item.book, 1),
				new ItemStack(PengSkinRed, 1, 0), new ItemStack(Item.paper, 1,
						0), new ItemStack(Item.paper, 1, 0), new ItemStack(
						Item.paper, 1, 0));
		

		TickRegistry.registerTickHandler(new CommonTickHandler(), Side.SERVER);
	}

	//@PostInit
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		// This runs after all mods are loaded
		// Useful for having penguins spawn in custom biomes? YES.
		BiomeDictionary.registerAllBiomes();
	}
}