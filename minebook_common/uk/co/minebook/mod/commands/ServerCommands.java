package uk.co.minebook.mod.commands;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import uk.co.minebook.mod.lib.sendData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.src.ModLoader;


public class ServerCommands extends CommandBase {

	@Override
	public String getCommandName() {
        return "minebook";
	}
	
    public int getRequiredPermissionLevel()
    {
        return 4;
    }
    
    public String getCommandUsage(ICommandSender sender) {
        return "Minebook Commands at http://minebook.co.uk/commands";
    }

	@SuppressWarnings("resource")
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if( args[0].equals("server") ) {
			if(args[1].equals("varify")) {
				if( !args[2].isEmpty() ) {
					try {
						FileInputStream fstream  = new FileInputStream("./Minebook-Server-ID.txt");
						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						String uuid = br.readLine();
						new sendData(sender, "http://minebook.co.uk/ingame/varifyServer", "uuid=" + uuid + "&varification=" + args[1]);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					sender.sendChatToPlayer("Usage: /minebook server varify <varification_code>");
				}
			}else if (args[1].equals("id")) {
				try {
					FileInputStream fstream  = new FileInputStream("./Minebook-Server-ID.txt");
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String uuid = br.readLine();
					sender.sendChatToPlayer("Minebook Server ID: " + uuid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (args[1].equals("register")) {
				try {
					FileInputStream fstream  = new FileInputStream("./Minebook-Server-ID.txt");
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String uuid = br.readLine();
					
					FileInputStream fstream2  = new FileInputStream("./server.properties");
					DataInputStream in2 = new DataInputStream(fstream2);
					BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
					
					String fileInfo = "", strLine;
					
					while ((strLine = br2.readLine()) != null)   {
						fileInfo = fileInfo + "\n" + strLine;
					}
					
					new sendData(sender, "http://minebook.co.uk/ingame/registerServer", "uuid=" + uuid + "&admin=" + sender.getCommandSenderName() + "&info=" + fileInfo + "&address=" + ModLoader.getMinecraftServerInstance().getHostname() );
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if (args[0].equals("varify")) {
			if( !args[1].isEmpty() ) {
				new sendData(sender, "http://minebook.co.uk/ingame/varify", "account=" + sender.getCommandSenderName() + "&varification=" + args[1]);
			}else{
				sender.sendChatToPlayer("Usage: /minebook varify <varification_code>");
			}
		}else if (args[0].equals("register")) {
			if( !args[1].isEmpty() ) {
				new sendData(sender, "http://minebook.co.uk/ingame/register", "account=" + sender.getCommandSenderName() + "&password=" + args[1]);
			}else{
				sender.sendChatToPlayer("Usage: /minebook register <password>");
			}
		}
	}

}
