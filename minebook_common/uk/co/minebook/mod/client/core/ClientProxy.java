package uk.co.minebook.mod.client.core;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import uk.co.minebook.mod.core.CommonProxy;
import uk.co.minebook.mod.keybinds.KeyScreenshot;
import uk.co.minebook.mod.lib.DeveloperCapesAPI;
import uk.co.minebook.mod.lib.HDSkin;

public class ClientProxy extends CommonProxy {

	public ClientProxy() {
	}
	
	@Override
	public void initCapes() {
		DeveloperCapesAPI.getInstance().init("http://admin.shadowravengames.co.uk/capes/capes.txt");
		HDSkin.getInstance().init("http://admin.shadowravengames.co.uk/skins/skins.txt");
	}
	
	public void registerKeyBindingHandler() {
		KeyBindingRegistry.registerKeyBinding(new KeyScreenshot());
	}
		
}
