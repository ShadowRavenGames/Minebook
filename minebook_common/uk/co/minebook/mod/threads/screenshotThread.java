package uk.co.minebook.mod.threads;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.util.Iterator;

import javax.imageio.ImageIO;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;


public class screenshotThread {

	public static BufferedImage image;
	
	public static String playersInView = "";
	public static String currentServer = "";

	protected boolean isInFieldOfVision(EntityLiving e1, EntityLiving e2){
        //save Entity 2's original rotation variables
        float rotationYawPrime = e2.rotationYaw;
        float rotationPitchPrime = e2.rotationPitch;
        //make Entity 2 directly face Entity 1
        e2.faceEntity(e1, 360F, 360F);
        //switch values of prime rotation variables with current rotation variables
        float f = e2.rotationYaw;
        float f2 = e2.rotationPitch;
        e2.rotationYaw = rotationYawPrime;
        e2.rotationPitch = rotationPitchPrime;
        rotationYawPrime = f;
        rotationPitchPrime = f2;
        //assuming field of vision consists of everything within X degrees from rotationYaw and Y degrees from rotationPitch, check if entity 2's current rotationYaw and rotationPitch within this X and Y range
        float X = 60F;
        float Y = 60F;
        float yawFOVMin = e2.rotationYaw - X;
        float yawFOVMax = e2.rotationYaw + X;
        float pitchFOVMin = e2.rotationPitch - Y;
        float pitchFOVMax = e2.rotationPitch + Y;
        boolean flag1 = (yawFOVMin < 0F && (rotationYawPrime >= yawFOVMin + 360F || rotationYawPrime <= yawFOVMax)) || (yawFOVMax >= 360F && (rotationYawPrime <= yawFOVMax - 360F || rotationYawPrime >= yawFOVMin)) || (yawFOVMax < 360F && yawFOVMin >= 0F && rotationYawPrime <= yawFOVMax && rotationYawPrime >= yawFOVMin);
        boolean flag2 = (pitchFOVMin <= -180F && (rotationPitchPrime >= pitchFOVMin + 360F || rotationPitchPrime <= pitchFOVMax)) || (pitchFOVMax > 180F && (rotationPitchPrime <= pitchFOVMax - 360F || rotationPitchPrime >= pitchFOVMin)) || (pitchFOVMax < 180F && pitchFOVMin >= -180F && rotationPitchPrime <= pitchFOVMax && rotationPitchPrime >= pitchFOVMin);
        if(flag1 && flag2 && e2.canEntityBeSeen(e1))
                return true;
        else return false;
	}
	
	public screenshotThread() {
	    
		IntBuffer field_74293_b = null;
	    int[] field_74294_c = null;
	    
		int width  = ModLoader.getMinecraftInstance().mcCanvas.getWidth();
    	int height = ModLoader.getMinecraftInstance().mcCanvas.getHeight();
    	
        try {
        	// Take Screenshot
        	int var5 = width * height;

            if (field_74293_b == null || field_74293_b.capacity() < var5)
            {
                field_74293_b = BufferUtils.createIntBuffer(var5);
                field_74294_c = new int[var5];
            }
            
            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            field_74293_b.clear();
            GL11.glReadPixels(0, 0, width, height, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, field_74293_b);
            field_74293_b.get(field_74294_c);
            flipImage(field_74294_c, width, height);
            image = new BufferedImage(width, height, 1);
            image.setRGB(0, 0, width, height, field_74294_c, 0, width);
            ImageIO.write(image, "png", new File("screenshots/minebook_upload.png"));
            
            @SuppressWarnings("unchecked")
			Iterator<EntityPlayer> entities = ModLoader.getMinecraftInstance().theWorld.playerEntities.iterator();
                       
            while( entities.hasNext() ) {
            	EntityPlayer user = entities.next();
            	EntityPlayer currentPlayer = ModLoader.getMinecraftInstance().thePlayer;
            	if( user != currentPlayer && 
            		isInFieldOfVision(user, currentPlayer) && 
            		currentPlayer.canEntityBeSeen(user) &&
            		currentPlayer.getDistanceToEntity(user) <= 20D
            	) {
            		playersInView = playersInView + user.username + ":";
            	}
            }
            
            if( !ModLoader.getMinecraftInstance().isSingleplayer() ) {
            	ModLoader.getMinecraftInstance().thePlayer.sendChatToPlayer("On a server, current will be tagged!");
            }else{
            	ModLoader.getMinecraftInstance().thePlayer.sendChatToPlayer("Not on a server, None will be tagged!");
            }
        	ModLoader.getMinecraftInstance().thePlayer.sendChatToPlayer("Tagged Players: " + playersInView);
            
            new Thread(new imageUpload()).start();

        } catch( Exception e ) {
        	e.printStackTrace();
        }
	}
	
	private static void flipImage(int[] par0ArrayOfInteger, int par1, int par2)
    {
        int[] var3 = new int[par1];
        int var4 = par2 / 2;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            System.arraycopy(par0ArrayOfInteger, var5 * par1, var3, 0, par1);
            System.arraycopy(par0ArrayOfInteger, (par2 - 1 - var5) * par1, par0ArrayOfInteger, var5 * par1, par1);
            System.arraycopy(var3, 0, par0ArrayOfInteger, (par2 - 1 - var5) * par1, par1);
        }
    }
}
