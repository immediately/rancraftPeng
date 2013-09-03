package rancraftPenguins;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

public class CommonProxy {
    //public static String RANCRAFTPENGITEMS_PNG = "/rancraftPenguins/client/textures/items.png";
    //public static String BLOCK_PNG = "/tutorial/generic/block.png";
   
    // Client stuff
    public void registerRenderers() {
            // Nothing here as the server doesn't render graphics!
    }

	public int addArmor(String armorName) {
		return 0;
	}

	public void registerSounds() {
		// server doesn't do sounds
	}
}