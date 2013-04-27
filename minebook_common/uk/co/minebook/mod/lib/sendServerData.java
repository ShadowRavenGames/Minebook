package uk.co.minebook.mod.lib;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class sendServerData implements Runnable {

	public static FMLServerStartingEvent sender;
	
	public sendServerData(FMLServerStartingEvent send) throws Exception {
		sender = send;
	}

	@Override
	@SuppressWarnings("resource")
	public void run() {
		while( sender.getServer().isServerRunning() ) {
			try {
				sender.getServer().sendChatToPlayer("Sending Server Data!");
				FileInputStream fstream  = new FileInputStream("./Minebook-Server-ID.txt");
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String uuid = br.readLine();
				String players    = Integer.toString(sender.getServer().getCurrentPlayerCount());
				String maxPlayers = Integer.toString(sender.getServer().getMaxPlayers());
				String serverStatus = Boolean.toString(sender.getServer().isServerRunning());
				
				new sendData(sender, "http://minebook.co.uk/ingame/serverData", "serverID=" + uuid + "&status=" + serverStatus + "&players=" + players + "/" + maxPlayers );

				Thread.sleep(15000);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}
