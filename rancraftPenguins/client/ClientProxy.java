package rancraftPenguins.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
//import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import rancraftPenguins.CommonProxy;
import rancraftPenguins.EntityPenguinCS;
import rancraftPenguins.EntityPenguinCloud;
import rancraftPenguins.EntityPenguinGent;
import rancraftPenguins.RanCraftPenguins_EventSounds;

import rancraftPenguins.EntityPenguinShuriken;
//import rancraftPenguins.client.RenderPenguinShuriken;

import rancraftPenguins.EntityPenguinEmp;
//import rancraftPenguins.client.ModelPenguinEmp;
import rancraftPenguins.EntityPenguinKing;
//import rancraftPenguins.client.ModelPenguinKing;
import rancraftPenguins.EntityPenguinGal;
//import rancraftPenguins.client.ModelPenguinGal;
import rancraftPenguins.EntityPenguinMag;
//import rancraftPenguins.client.ModelPenguinMag;
import rancraftPenguins.EntityPenguinAdel;
//import rancraftPenguins.client.ModelPenguinAdel;
import rancraftPenguins.EntityPenguinYE;
//import rancraftPenguins.client.ModelPenguinYE;
import rancraftPenguins.EntityPenguinWF;
//import rancraftPenguins.client.ModelPenguinWF;
import rancraftPenguins.EntityPenguinLB;
//import rancraftPenguins.client.ModelPenguinLB;
import rancraftPenguins.EntityPenguinFlam;
//import rancraftPenguins.client.ModelPenguinFlam;
import rancraftPenguins.EntityPenguinNinj;
//import rancraftPenguins.client.ModelPenguinNinj;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerSounds(){
		MinecraftForge.EVENT_BUS.register(new RanCraftPenguins_EventSounds());
	}

	@Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinShuriken.class, new RenderPenguinShuriken());
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinEmp.class, new RenderPenguin(new ModelPenguinEmp(),new ModelPenguinEmp(), 0.6F, "Emp"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinKing.class, new RenderPenguin(new ModelPenguinKing(),new ModelPenguinKing(), 0.5F, "King"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinYE.class, new RenderPenguin(new ModelPenguinYE(),new ModelPenguinYE(), 0.4F, "YE"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinMag.class, new RenderPenguin(new ModelPenguinMag(),new ModelPenguinMag(), 0.4F, "Mag"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinGal.class, new RenderPenguin(new ModelPenguinGal(),new ModelPenguinGal(), 0.3F, "Gal"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinAdel.class, new RenderPenguin(new ModelPenguinAdel(),new ModelPenguinAdel(), 0.3F, "Adel"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinWF.class, new RenderPenguin(new ModelPenguinWF(),new ModelPenguinWF(), 0.2F, "WF"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinLB.class, new RenderPenguin(new ModelPenguinLB(),new ModelPenguinLB(), 0.2F, "LB"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinGent.class, new RenderPenguin(new ModelPenguinGent(),new ModelPenguinGent(), 0.4F, "Gent"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinCS.class, new RenderPenguin(new ModelPenguinCS(),new ModelPenguinCS(), 0.4F, "CS"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinFlam.class, new RenderPenguin(new ModelPenguinFlam(),new ModelPenguinFlam(), 0.7F, "Flam"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinNinj.class, new RenderPenguinNinj(new ModelPenguinNinj(),0.5F));
        //RenderingRegistry.registerEntityRenderingHandler(EntityPenguinNinj.class, new RenderPenguin(new ModelPenguinNinj(),new ModelPenguinNinj(), 0.5F, "Ninj"));
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguinCloud.class, new RenderPenguin(new ModelPenguinCloud(),new ModelPenguinCloud(), 0.3F, "Cloud"));

        /* not registering a renderer, but these are one-time events, too. */
        TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
    }
    @Override
    public int addArmor(String armorName){
    	return RenderingRegistry.addNewArmourRendererPrefix(armorName);
    }
}