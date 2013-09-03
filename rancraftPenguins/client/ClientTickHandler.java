package rancraftPenguins.client;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.List;

import rancraftPenguins.ItemPenguinFishingRod;
import rancraftPenguins.RCPacketHandler;
import rancraftPenguins.RanCraftPenguins;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class ClientTickHandler implements ITickHandler
{
    static int tickCount = 0; // static to preserve their values between calls
    static int tick2Count = 0; // this one only ticks once each time the data are checked
    static int prevAir = 0;

	@Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
        else if (type.equals(EnumSet.of(TickType.CLIENT)))
        {
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
            if (guiscreen != null)
            {
                onTickInGUI(guiscreen);
            } else {
                onTickInGame();
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
        // In my testing only RENDER, CLIENT, & PLAYER did anything on the client side.
        // Read 'cpw.mods.fml.common.TickType.java' for a full list and description of available types
    }

    @Override
    public String getLabel() { return null; }


    public void onRenderTick()
    {
        //System.out.println("onRenderTick");
        //TODO: Your Code Here
    }

    public void onTickInGUI(GuiScreen guiscreen)
    {
        //System.out.println("onTickInGUI");
        //TODO: Your Code Here
    }

	public void sendExtinguishMessage(EntityPlayerSP entityPlayerSp)
    {
        int extinguish = 451;
    	//ByteArrayOutputStream bos = new ByteArrayOutputStream(4 + entityPlayerSp.username.length()); // 4 bytes b/c it's an int 
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(4); // 4 bytes b/c it's an int 
    	DataOutputStream outputStream = new DataOutputStream(bos);

    	//System.out.printf("Client: Sending extinguish message...\n");
    	try {
    	        outputStream.writeInt(extinguish);
                //outputStream.writeChars(entityPlayerSp.username);
    	} catch (Exception ex) {
    	        ex.printStackTrace();
                System.out.println("Client: Error creating packet");
    	}
    	Packet250CustomPayload packet = new Packet250CustomPayload();
    	packet.channel = "RanCraftExt";
    	packet.data = bos.toByteArray();
    	packet.length = bos.size();

    	Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT)
        {
			//System.out.printf("[CLIENT]Sending packet on this channel: [%s] \n", packet.channel);
        	PacketDispatcher.sendPacketToServer(packet);
        }
    }

	public void sendAirLevelInfo(EntityPlayerSP entityPlayerSp, int airLevel)
    {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(4); /* 4 bytes b/c it's an int */
    	DataOutputStream outputStream = new DataOutputStream(bos);
    	try {
    	        outputStream.writeInt(airLevel);
    	} catch (Exception ex) {
    	        ex.printStackTrace();
                System.out.println("Client: Error creating packet");
    	}
    	Packet250CustomPayload packet = new Packet250CustomPayload();
    	packet.channel = "RanCraftAL";
    	packet.data = bos.toByteArray();
    	packet.length = bos.size();

    	Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT)
        {
			//System.out.printf("[CLIENT]Sending packet on this channel: [%s] \n", packet.channel);
        	PacketDispatcher.sendPacketToServer(packet);
			//PacketDispatcher.sendPacketToAllPlayers(packet);
        }
    }

	public void sendFallDistanceInfo(EntityPlayerSP entityPlayerSp, float fallDistance)
    {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(4); /* 4 bytes b/c it's a float */
    	DataOutputStream outputStream = new DataOutputStream(bos);
    	try {
    	        outputStream.writeFloat(fallDistance);
    	} catch (Exception ex) {
    	        ex.printStackTrace();
                System.out.println("Client: Error creating packet");
    	}
    	Packet250CustomPayload packet = new Packet250CustomPayload();
    	packet.channel = "RanCraftFD";
    	packet.data = bos.toByteArray();
    	packet.length = bos.size();

    	Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT)
        {
			//System.out.printf("[CLIENT]Sending packet on this channel: [%s] \n", packet.channel);
        	PacketDispatcher.sendPacketToServer(packet);
        }
    }

	public void sendFishingRodStateRequest(EntityPlayerSP entityPlayerSp)
    {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(4); /* 4 bytes b/c it's an int */
    	DataOutputStream outputStream = new DataOutputStream(bos);
    	try {
    	        outputStream.writeInt(2);
    	} catch (Exception ex) {
    	        ex.printStackTrace();
                System.out.println("Client: Error creating packet");
    	}
    	Packet250CustomPayload packet = new Packet250CustomPayload();
    	packet.channel = "RanCraftFRSR";
    	packet.data = bos.toByteArray();
    	packet.length = bos.size();

    	Side side = FMLCommonHandler.instance().getEffectiveSide();
        if (side == Side.CLIENT)
        {
			//System.out.printf("[CLIENT]Sending packet on this channel: [%s] \n", packet.channel);
        	PacketDispatcher.sendPacketToServer(packet);
        }
    }

    public void onTickInGame()
    {
    	Minecraft theMinecraft = Minecraft.getMinecraft();
        EntityPlayerSP entityplayersp = theMinecraft.thePlayer;
        InventoryPlayer inventoryplayer = entityplayersp.inventory;
        int maxAir = 300; /* in case this changes in later versions of MC */
        int depleteFreq, curAir;
        float fallDist;

        /* for various infrequent things */
        tickCount++;
        if(tickCount > 199){
        	tickCount = 0; /* to avoid letting tickCount get too high */
        }
    	if(tickCount%4 == 0){ // to avoid using too many cycles
    		tick2Count++; // so this increments every n ticks (currently n=4)
            if(tick2Count > 199){
            	tick2Count = 0; /* to avoid letting tick2Count get too high */
            }

	        if(inventoryplayer.getCurrentItem() != null && inventoryplayer.getCurrentItem().itemID == RanCraftPenguins.PenguinFishingRod.itemID){
	        	//sendFallDistanceInfo(entityplayersp, fallDist); /* sending info to server */
            	//System.out.printf("Client says, holding fishing rod. Requesting distance from server.\n");
            	sendFishingRodStateRequest(entityplayersp);
	        }

	        if(inventoryplayer.armorItemInSlot(0) != null && inventoryplayer.armorItemInSlot(0).itemID == RanCraftPenguins.PenguinBootsCloud.itemID){
	        	if(!entityplayersp.isCollidedVertically){ // not touching ground
	        		//if(!entityplayersp.isCollidedVertically){
	        		fallDist = entityplayersp.fallDistance / 3.0F;
		        	sendFallDistanceInfo(entityplayersp, fallDist); /* sending info to server */
	        	}
	        }

	        if(inventoryplayer.armorItemInSlot(0) != null && inventoryplayer.armorItemInSlot(0).itemID == RanCraftPenguins.PenguinFlippers.itemID)
	        {
	            if(entityplayersp.isInWater()){ // faster in water
	            	//System.out.printf("Current velocity total=%f\n", Math.abs(entityplayersp.motionX) + Math.abs(entityplayersp.motionZ));
	            	if(Math.abs(entityplayersp.motionX) + Math.abs(entityplayersp.motionY) + Math.abs(entityplayersp.motionZ) < 1.5F){
			            entityplayersp.motionX *= 1.3F;
			            entityplayersp.motionY *= 1.1F;
			            entityplayersp.motionZ *= 1.3F;
	            	}
	            } else if(entityplayersp.onGround){ // much slower or slightly slower on land
		            if(inventoryplayer.armorItemInSlot(1) != null && inventoryplayer.armorItemInSlot(1).itemID == RanCraftPenguins.PenguinPants.itemID){
		            	// slightly slower if also wearing PenguinPants
			            entityplayersp.motionX *= 0.8F;
			            entityplayersp.motionZ *= 0.8F;
		            } else if(inventoryplayer.armorItemInSlot(1) == null || inventoryplayer.armorItemInSlot(1).itemID != RanCraftPenguins.PenguinFlameLeggings.itemID) {
		            	// much slower and can't jump if wearing no pants or something other than PenguinPants or PenguinFlameLeggings
			            entityplayersp.motionX *= 0.5F;
			            entityplayersp.motionZ *= 0.5F;
		            }
	            } else if(entityplayersp.movementInput.jump){ // can't jump with flippers unless either Penguin pants or leggings
	            	if(inventoryplayer.armorItemInSlot(1) == null || (inventoryplayer.armorItemInSlot(1).itemID != RanCraftPenguins.PenguinPants.itemID && inventoryplayer.armorItemInSlot(1).itemID != RanCraftPenguins.PenguinFlameLeggings.itemID)) {
		            	entityplayersp.motionY *= 0.5D;
	            	} // else is wearing penguin pants or leggings, so can jump normally
	            }
	        }
	
	        /* Flame flippers increases swimming speed to double the speed of normal penguin flippers */
	        /* Also, less bouncing in the water, but harder to steer. Good for ocean crossings. */
	        /* FlamePenguin leggings eliminate the slowing effects of both types of flippers */
	        if(inventoryplayer.armorItemInSlot(0) != null && inventoryplayer.armorItemInSlot(0).itemID == RanCraftPenguins.PenguinFlameFlippers.itemID)
	        {
	            if(entityplayersp.isInWater()){ // faster in water
		        	if(Math.abs(entityplayersp.motionX) + Math.abs(entityplayersp.motionZ) < 1.5F){
			            entityplayersp.motionX *= 1.7F;
			            entityplayersp.motionZ *= 1.7F;
		        	}
	            } else if(entityplayersp.onGround){ // slower on land unless PenguinFlameLeggings
		            if(inventoryplayer.armorItemInSlot(1) == null || inventoryplayer.armorItemInSlot(1).itemID != RanCraftPenguins.PenguinFlameLeggings.itemID) {
			            entityplayersp.motionX *= 0.5F;
			            entityplayersp.motionZ *= 0.5F;
		            }
	            } else if(entityplayersp.movementInput.jump){ // can't jump unless PenguinFlameLeggings
		            if(inventoryplayer.armorItemInSlot(1) == null || inventoryplayer.armorItemInSlot(1).itemID != RanCraftPenguins.PenguinFlameLeggings.itemID) {
		            	entityplayersp.motionY *= 0.5D;
		            }
	            }
	        }
	
	        /* Flame Penguin chest plate extinguishes the player once they're not in lava */
	        if(inventoryplayer.armorItemInSlot(2) != null && inventoryplayer.armorItemInSlot(2).itemID == RanCraftPenguins.PenguinFlameChestPlate.itemID){
	        	if(entityplayersp.isBurning()){
	        		entityplayersp.extinguish();
		        	//System.out.printf("Client: Player isBurning and wearing chestplate, so sending extinguish.\n");
	        		sendExtinguishMessage(entityplayersp);
	        	}
	        }
	        
	        /* Any penguin hat (quadruples air supply) */
	    	if(entityplayersp.isInsideOfMaterial(Material.water)){ // we're underwater
		        if(inventoryplayer.armorItemInSlot(3) != null && 
		        		((inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatEmp.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatKing.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatYE.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatMag.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatGal.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatWF.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatAdel.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatGent.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatCS.itemID)
		        		  || (inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinHatLB.itemID))) {
		        	// Usually quadruples time underwater, but sometimes much more -- air use stops for awhile
		        	curAir = entityplayersp.getAir();
    	        	//Side side = FMLCommonHandler.instance().getEffectiveSide();
		        	//depleteFreq = theMinecraft.theWorld.isRemote ? 23 : 4; // b/c air depletes faster in smp
    	        	//System.out.printf("isRemote = %b\n", theMinecraft.theWorld.isRemote); // always true
    	        	//System.out.printf("isClientSide = %b\n", (side == Side.CLIENT)); // always true
		        	depleteFreq = 5; // 4
		        	if(tick2Count%depleteFreq == 0 || curAir < 1){ // check air and allow it to drop one in n ticks
	    	        	//System.out.printf("Client: Should I allow air to drop? curAir = %d, prevAir = %d\n", curAir, prevAir);
		        		if(prevAir > curAir){
		        			curAir--; // decrement air (already being done at a higher level?)
		        			prevAir = curAir;
		    	        	//System.out.printf("Client: ALLOWING air to drop: Airlevel = %d\n", curAir);
		        		}
		        	}
	        		entityplayersp.setAir(prevAir); // set air for this user to prevAir, which might be lower now
	        		sendAirLevelInfo(entityplayersp, prevAir); /* sending info to server */
		        	//System.out.printf("client: JUST SET air to prevAir: %d: Deplete freq = %d\n", prevAir, depleteFreq);
		        /* Flame penguin mask (no air loss) */
		        } else if(inventoryplayer.armorItemInSlot(3) != null && inventoryplayer.armorItemInSlot(3).itemID == RanCraftPenguins.PenguinFlameMask.itemID) {
			    	curAir = entityplayersp.getAir();
			    	if(curAir < maxAir){ // fix it every time it goes below maximum
			    		//System.out.printf("From mod_RanCraft: Value for air before setAir: %d\n", entityplayersp.getAir());
		        		entityplayersp.setAir(maxAir);
			        	//System.out.printf("From mod_RanCraft: New value for air: %d\n", entityplayersp.getAir());
		        		sendAirLevelInfo(entityplayersp, maxAir); /* sending info to server */
	    	    	}
	        	} else {  // not wearing penguin mask or hat, so make sure prevAir is ready for hat-on calculations
	        		prevAir = maxAir;
		        	//System.out.printf("Client: Max air for prevAir for when hat goes back on: prevAir = %d\n", prevAir);
	        	}
	        } else { // not underwater
	    		prevAir = maxAir;
	        	curAir = maxAir;
	        	//System.out.printf("Client: Max air b/c not underwater! Airlevel = %d\n", prevAir);
	        }
    	} // tickCount

	    return;
    }
}