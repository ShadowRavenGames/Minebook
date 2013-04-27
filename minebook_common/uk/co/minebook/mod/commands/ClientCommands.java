package uk.co.minebook.mod.commands;

import uk.co.minebook.mod.lib.sendData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class ClientCommands extends CommandBase {

	@Override
	public String getCommandName() {
        return "minebook";
	}
	
    public int getRequiredPermissionLevel()
    {
        return 1;
    }
    
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        return true;
    }   
	
    public String getCommandUsage(ICommandSender sender) {
        return "Minebook Commands at http://minebook.co.uk/commands";
    }

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args[0].equals("varify")) {
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
