package uk.co.minebook.mod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;

import uk.co.minebook.mod.commands.ClientCommands;
import uk.co.minebook.mod.commands.ServerCommands;
import uk.co.minebook.mod.core.CommonProxy;
import uk.co.minebook.mod.items.Profile;
import uk.co.minebook.mod.lib.DeveloperCapesAPI;
import uk.co.minebook.mod.lib.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(
	modid   = Reference.MOD_ID,
	name    = Reference.MOD_NAME,
	version = Reference.VERSION
)

@NetworkMod(
	channels = {
		Reference.CHANNEL_NAME
	},
	clientSideRequired = false,
	serverSideRequired = false
)

public class minebook {
public static MinecraftServer server;
	
	@Instance(Reference.MOD_ID)
    public static minebook instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    
	// CURRENT SERVER ID
    public static String currentServer = "";

	// ITEMS
    //public static Item profile = new Profile(15002);

	@PreInit
	public void preInit( FMLPreInitializationEvent event ) {
		proxy.registerKeyBindingHandler();
		
		proxy.initCapes();

		//LanguageRegistry.addName(profile, "Minebook Profile");
		//GameRegistry.addRecipe(new ItemStack(profile,1), "X","Y", Character.valueOf('X'), Item.map, Character.valueOf('Y'), Item.coal );
	}

	@Init
	public void load( FMLInitializationEvent event ) {
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
	}
	
	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) throws Exception {
		
		if( event.getServer().isDedicatedServer() ) {
			
			File f = new File("./Minebook-Server-ID.txt");
			if( !f.exists() ) {
				String uuid = randomID();
        		BufferedWriter writer;
				writer = new BufferedWriter(new FileWriter(f));
				writer.write(uuid);
				writer.flush();
		        writer.close();
				event.getServer().sendChatToPlayer("Minebook Server ID: " + uuid);
			}else{
				FileInputStream fstream  = new FileInputStream("./Minebook-Server-ID.txt");
				DataInputStream in = new DataInputStream(fstream);
				@SuppressWarnings("resource")
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String uuid = br.readLine();
				event.getServer().sendChatToPlayer("Minebook Server ID: " + uuid);
			}
			event.registerServerCommand(new ServerCommands());
					
			//new Thread(new sendServerData(event));

		}else{
			
			event.registerServerCommand(new ClientCommands());
			
		}
	}
	
	@ServerStarted
	public void serverStarted(FMLServerStartedEvent event) {
	}
	
	private String randomID() throws Exception {
		String id = UUID.randomUUID().toString();
		
        URL url = new URL("http://minebook.co.uk/ingame/isIdUsed.php?id=" + id);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = null;
        
		if( line.toString() == "no" ) {
			return id;
		}else{
			return randomID();
		}
	}
}
