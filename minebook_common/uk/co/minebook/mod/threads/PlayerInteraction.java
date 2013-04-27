package uk.co.minebook.mod.threads;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.util.ChunkCoordinates;

public class PlayerInteraction extends EntityPlayer {
    
	EntityPlayer player = ModLoader.getMinecraftInstance().thePlayer;
	
	public PlayerInteraction() {
		super(ModLoader.getMinecraftServerInstance().worldServerForDimension(0));
	}

	@Override
    public boolean interact(EntityPlayer entityplayer) {
    	player.sendChatToPlayer("test!");
    	
    	return super.interact(player);
    }

	@Override
	protected void entityInit() {
		player.sendChatToPlayer("Entity Initialized!");
	}

	@Override
	public void sendChatToPlayer(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canCommandSenderUseCommand(int i, String s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}
}
