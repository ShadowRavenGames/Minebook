package uk.co.minebook.mod.threads;

import java.io.File;
import java.net.URL;

import uk.co.minebook.mod.lib.ClientHttpRequest;

import net.minecraft.src.ModLoader;

public class imageUpload implements Runnable {
	
	public void run() {
		try {
			ClientHttpRequest.post(
				new URL("http://minebook.co.uk/ingame/screenshot.php"), 
				new Object[] {
					"account", ModLoader.getMinecraftInstance().thePlayer.username,
					"mb_upload", new File("screenshots/minebook_upload.png"),
					"tagged_users", screenshotThread.playersInView,
					"tagged_server", screenshotThread.currentServer
				}
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModLoader.getMinecraftInstance().thePlayer.sendChatToPlayer("Screenshot Uploaded to Minebook!");
		new File("screenshots/minebook_upload.png").delete();
	}
	
}
