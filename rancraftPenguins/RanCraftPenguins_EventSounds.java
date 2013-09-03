package rancraftPenguins;

import java.net.URL;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class RanCraftPenguins_EventSounds {

	@ForgeSubscribe
    public void onSound(SoundLoadEvent event)
    {
    	int i, ii;
    	String[] species = {
    			"penguinEmp",
    			"penguinKing",
    			"penguinMag",
    			"penguinGal",
    			"penguinYE",
    			"penguinAdel",
    			"penguinWF",
    			"penguinGent",
    			"penguinCS",
    			"penguinLB",
    			"penguinFlam"
		};
    	String[] soundType = {
    			"angry",
    			"hurt",
    			"death",
    			"healtha",
    			"healthb",
    			"healthc",
    			"healthd",
		};
    	
    	for(i = 0; i < species.length; i++){
	    	for(ii = 0; ii < soundType.length; ii++){
		        try
		        {
		            //event.manager.soundPoolSounds.addSound(species[i]+"/"+soundType[ii]+".ogg", RanCraftPenguins.class.getResource("client/sounds/"+species[i]+"/"+soundType[ii]+".ogg"));
		            event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":"+species[i]+"/"+soundType[ii]+".ogg");
		        }
		        catch (Exception e)
		        {
		            System.err.println("Failed to register one or more sounds.");
		        }
	    	}
    	}
    	/* Ninja and cloud penguins don't have all seven of the standard sounds */
        try
        {
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinNinj/angry.ogg");
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinNinj/hurt.ogg");
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinNinj/death.ogg");
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinCloud/healtha.ogg");
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinCloud/hurt.ogg");
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinCloud/death.ogg");
	        event.manager.soundPoolSounds.addSound(RanCraftPenguins.modID+":penguinCloud/wilhelm.ogg");
	        //event.manager.soundPoolSounds.addSound("penguinNinj/shurikenA.ogg", RanCraftPenguins.class.getResource("client/sounds/penguinNinj/shurikenSwish1.ogg"));
        }
        catch (Exception e)
        {
            System.err.println("Failed to register one or more sounds.");
        }
    }
	//"/assets/"+RanCraftPenguins.modID+"/sounds/"+sound+".ogg"
/*    private URL getSound(String sound) {
        return this.getClass().getResource("/assets/" + RanCraftPenguins.modID + "/sounds/" + sound + ".ogg"); // note that mine has assets as the base... That is because I am using 1.6.1. Should be mods if 1.5.2
    }*/
    /*@ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent evt) {
        for (String sound : SOUNDS) {
            evt.manager.soundPoolSounds.addSound("minepg/" + sound + ".ogg");
            System.out.println("[MinePG Sound Loader] Loading sounds...");
            SoundLoader.didSoundsLoad = true;
            System.out.println("[MinePG Sound Loader] Sounds Loaded");
        }
    }*/
}
