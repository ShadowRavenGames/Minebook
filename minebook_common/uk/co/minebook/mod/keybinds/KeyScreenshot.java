package uk.co.minebook.mod.keybinds;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import uk.co.minebook.mod.threads.screenshotThread;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class KeyScreenshot extends KeyHandler {

	private static KeyBinding keyScreenshot = new KeyBinding("Minebook Screenshot", Keyboard.KEY_F2);

	public KeyScreenshot() {
		super(new KeyBinding[]{keyScreenshot}, new boolean[]{false});
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "KeyScreenshot";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
		if ( tickEnd ) {
			new screenshotThread();
		}
	}
	

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

}
