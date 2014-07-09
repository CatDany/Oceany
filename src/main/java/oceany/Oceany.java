package oceany;

import net.minecraftforge.common.AchievementPage;
import oceany.blocks.ModBlocks;
import oceany.damage.ModDamageSources;
import oceany.event.EventAchievements;
import oceany.event.EventPotions;
import oceany.event.EventSquidDrops;
import oceany.gui.GuiHandler;
import oceany.items.ModItems;
import oceany.libs.EventBusHelper;
import oceany.network.PacketHandler;
import oceany.potion.ModPotions;
import oceany.proxy.ProxyCommon;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod
(
	modid = Refs.MOD_ID,
	name = Refs.MOD_NAME,
	dependencies = Refs.DEPENDENCIES,
	version = Refs.VERSION,
	guiFactory = Refs.GUI_FACTORY_PATH
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
		
		Config.initConfigurationFile(e);
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
		
		//FMLInterModComms.sendMessage("Waila", "register", "oceany.compat.CompatWaila.register");
		// WorldGenHandler.initWorldGeneration();
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		// server commands
	}
}