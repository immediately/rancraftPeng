package rancraftPenguins;

import java.io.IOException;
import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.Player;

public class CommonTickHandler implements ITickHandler
{
    public EnumSet ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    public String getLabel()
    {
        return null;
    }
       
    private void onTickInGame()
    {
        //put here any code that needs to be done server side, like set WorldGeneration, and let entities spawn on the first server tick.
        //also put all the code in a if(world != null && !world.isRemote) statement, makes it so that you don't get NPEs from that.
		//System.out.printf("Server: tick.\n");
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if(type.equals(EnumSet.of(TickType.SERVER)))
        {
                onTickInGame();
        }
    }
}