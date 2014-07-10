package oceany;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import oceany.libs.EventBusHelper;
import oceany.libs.EventBusHelper.EventBusType;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class Config
{
	private static Config instance = new Config(); 
	private static final HashMap<String, Object> map = new HashMap<String, Object>();
	private static Configuration config;
	
	public static void initConfigurationFile(FMLPreInitializationEvent e)
	{
		config = new Configuration(e.getSuggestedConfigurationFile());
		EventBusHelper.register(instance, EventBusType.FML);
	}
	
	public static void setConfigVariables()
	{
		config.load();
		
		addConfig("settings.squidogen.energy_per_squid", 5000, null);
		
		if (config.hasChanged())
		{
			config.save();
		}
	}
	
	private static void addConfig(String name, String def, String comment)
	{
		int dotIndex = name.indexOf(".");
		String category = name.substring(0, dotIndex);
		String key = name.substring(dotIndex + 1);
		if (comment == null || comment.equals(""))
		{
			map.put(name, config.get(category, key, def).getString());
		}
		else
		{
			map.put(name, config.get(category, key, def, comment).getString());
		}
	}
	
	private static void addConfig(String name, int def, String comment)
	{
		int dotIndex = name.indexOf(".");
		String category = name.substring(0, dotIndex);
		String key = name.substring(dotIndex + 1);
		if (comment == null || comment.equals(""))
		{
			map.put(name, config.get(category, key, def).getInt(def));
		}
		else
		{
			map.put(name, config.get(category, key, def, comment).getInt(def));
		}
	}
	
	private static void addConfig(String name, boolean def, String comment)
	{
		int dotIndex = name.indexOf(".");
		String category = name.substring(0, dotIndex);
		String key = name.substring(dotIndex + 1);
		if (comment == null || comment.equals(""))
		{
			map.put(name, config.get(category, key, def).getBoolean(def));
		}
		else
		{
			map.put(name, config.get(category, key, def, comment).getBoolean(def));
		}
	}
	
	public static Object getAsObject(String key)
	{
		return map.get(key);
	}
	
	public static String getString(String key)
	{
		return (String)getAsObject(key);
	}
	
	public static int getInteger(String key)
	{
		return (Integer)getAsObject(key);
	}
	
	public static boolean getBoolean(String key)
	{
		return (Boolean)getAsObject(key);
	}
}