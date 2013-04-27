package uk.co.minebook.mod.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

import net.minecraft.command.ICommandSender;

public class sendData {
	
	public sendData( FMLServerStartingEvent sender, String urlString, String data ) {
		try{
			URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            
            //write parameters
            writer.write(data);
            writer.flush();
            
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();
            
            //Output the response
            sender.getServer().sendChatToPlayer(answer.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public sendData( ICommandSender sender, String urlString, String data ) {
		try{
			URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            
            //write parameters
            writer.write(data);
            writer.flush();
            
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();
            
            //Output the response
            sender.sendChatToPlayer(answer.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
}
