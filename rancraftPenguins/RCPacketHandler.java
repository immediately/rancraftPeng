package rancraftPenguins;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RCPacketHandler implements IPacketHandler {

	@Override
    public void onPacketData(INetworkManager manager,Packet250CustomPayload packet, Player player) {
           
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        if(packet.channel.equals("RanCraftFRS")){ // the only packet that goes from server to client
	    	EntityPlayer playerSP = (EntityPlayer) player;
	        int incoming;
	    	//System.out.printf("Server, PacketHandler: Got SOMETHING from some channel...\n");
	        Side side = FMLCommonHandler.instance().getEffectiveSide();
	        if (side == Side.CLIENT) { // always true?
		        if (packet.channel.equals("RanCraftFRS")) {
		        	if(playerSP.getCurrentEquippedItem().getItem() instanceof ItemPenguinFishingRod){
			        	try{
			        		incoming = inputStream.readInt();
			            	//System.out.printf("Client: Got %d from RanCraftFishingRodState channel.\n", incoming);
			            	((ItemPenguinFishingRod)playerSP.getCurrentEquippedItem().getItem()).setIconTo(incoming);
			        	} catch(IOException e) {
			        		e.printStackTrace();
			        		return;
			        	}
		        	}
		        }
	        }
        } else { // all others go from client to server
	    	EntityPlayerMP playerMP = (EntityPlayerMP)player;
	        int extinguish = 451;
	        int maxAir = 300; /* in case this changes in later versions of MC */
	        int incoming;
	        float incomingF = 0.0F;
	
	    	//System.out.printf("Server, PacketHandler: Got SOMETHING from some channel...\n");
	        Side side = FMLCommonHandler.instance().getEffectiveSide();
	        if (side == Side.SERVER) { // always true?
		        if (packet.channel.equals("RanCraftFRSR")) {
		        	try{
		        		incoming = inputStream.readInt();
		            	//System.out.printf("Server: Got %d from RanCraftFishingRodStateRequest channel. Sending response to %s.\n", incoming, playerMP.getEntityName());
		            	sendFishingRodState(player,((EntityPlayer) player).getCurrentEquippedItem());
		        	} catch(IOException e) {
		        		e.printStackTrace();
		        		return;
		        	}
		        }
		        if (packet.channel.equals("RanCraftExt")) {
		        	try{
		        		incoming = inputStream.readInt();
		            	//System.out.printf("Server: Got %d from RanCraftExtinguish channel\n", incoming);
		            	if(incoming == extinguish){
		            		//System.out.printf("Server: TRYING to extinguish player.\n");
		            		playerMP.extinguish();
		            	}
		        	} catch(IOException e) {
		        		e.printStackTrace();
		        		return;
		        	}
		        }
		        if (packet.channel.equals("RanCraftAL")) {
		        	try{
		        		incoming = inputStream.readInt();
			        	//System.out.printf("Server: Got %d from RanCraftAirLevel channel\n", incoming);
			        	if(incoming > -1 && incoming < maxAir + 1){
			        		playerMP.setAir(incoming);
			        	}
		        	} catch(IOException e) {
		        		e.printStackTrace();
		        		return;
		        	}
		        }
		        if (packet.channel.equals("RanCraftFD")) {
		        	try{
		        		incomingF = inputStream.readFloat();
			        	//System.out.printf("Server: Got %f from RanCraftFallDistance channel\n", incomingF);
			        	if(true){
			        		playerMP.fallDistance = incomingF;
			        	}
		        	} catch(IOException e) {
		        		e.printStackTrace();
		        		return;
		        	}
		        }
	        }
        }
    }

	public void sendFishingRodState(Player player, ItemStack currentItem){

    	if(currentItem != null && currentItem.itemID == RanCraftPenguins.PenguinFishingRod.itemID){
    		rancraftPenguins.ItemPenguinFishingRod pfr = (rancraftPenguins.ItemPenguinFishingRod)currentItem.getItem();
        	//System.out.printf("Server, PacketHandler: About to reply to client with fishing rod state = %d\n", pfr.rodIcon);

    		ByteArrayOutputStream bos = new ByteArrayOutputStream(4); /* 4 bytes b/c it's an int */
	    	DataOutputStream outputStream = new DataOutputStream(bos);
	    	try {
	    	        outputStream.writeInt(pfr.rodIcon);
	    	} catch (Exception ex) {
	    	        ex.printStackTrace();
	                System.out.println("Client: Error creating packet");
	    	}
	    	Packet250CustomPayload packet = new Packet250CustomPayload();
	    	packet.channel = "RanCraftFRS";
	    	packet.data = bos.toByteArray();
	    	packet.length = bos.size();
	
	    	Side side = FMLCommonHandler.instance().getEffectiveSide();
	        if (side == Side.SERVER)
	        {
				//System.out.printf("[SERVER]Sending packet on this channel: [%s] \n", packet.channel);
	        	PacketDispatcher.sendPacketToPlayer(packet, player);
	        }
    	}
    }

}