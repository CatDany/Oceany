package oceany;

import net.minecraftforge.common.AchievementPage;
import oceany.blocks.ModBlocks;
import oceany.command.CommandGC;
import oceany.command.ModCommandBase;
import oceany.damage.ModDamageSources;
import oceany.event.EventAchievements;
import oceany.event.EventPotions;
import oceany.event.EventSquidDrops;
import oceany.gui.GuiHandler;
import oceany.items.ModItems;
import oceany.misc.JControl;
import oceany.network.PacketHandler;
import oceany.potion.ModPotions;
import oceany.proxy.ProxyCommon;
import oceany.world.DungeonLootHandler;
import oceany.world.GenHandler;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import danylibs.libs.EventBusHelper;
import danylibs.libs.TickHandler;

@Mod
(
	modid = Refs.MOD_ID,
	name = Refs.MOD_NAME,
	dependencies = Refs.DEPENDENCIES,
	version = Refs.VERSION,
	guiFactory = Refs.GUI_FACTORY_PATH,
	certificateFingerprint = Refs.FINGERPRINT
)
public class Oceany
{
	@Instance(Refs.MOD_ID)
	public static Oceany instance;
	
	@SidedProxy(serverSide = "oceany.proxy.ProxyCommon", clientSide = "oceany.proxy.ProxyClient")
	public static ProxyCommon proxy;
	
	public static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger = e.getModLog();
		//JControl.a();
		
		//Config.initConfigurationFile(e); FIXME
		Config.setConfigVariables();
		ModBlocks.initBlocks();
		ModItems.initItems();
		Recipes.initRecipes();
		AchievementPage.registerAchievementPage(new ModAchievementPage());
		
		ModPotions.patchPotionSystem();
		ModPotions.initPotionEffects();
		ModDamageSources.initDamageSources();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		PacketHandler.instance().init();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		EventBusHelper.checkBusAndRegister(new EventAchievements());
		EventBusHelper.checkBusAndRegister(new EventSquidDrops());
		EventBusHelper.checkBusAndRegister(new EventPotions());
		
		TickHandler.registerTickHandler(new VersionChecker());
		
		FMLInterModComms.sendMessage("Waila", "register", "oceany.compat.CompatWaila.register");
		
		GameRegistry.registerWorldGenerator(new GenHandler(), 1);
		DungeonLootHandler.init();
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		// server commands
	}
	
	public void serverStarting(FMLServerStartingEvent e)
	{
		ModCommandBase.registerCommand(new CommandGC(), e);
	}
	
	public void fingerprint(FMLFingerprintViolationEvent e)
	{
		System.exit(0);
		//JControl.a().b();
	}
}