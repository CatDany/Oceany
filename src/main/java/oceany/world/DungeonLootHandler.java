package oceany.world;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import oceany.Config;
import oceany.Oceany;
import oceany.items.ModItems;

public class DungeonLootHandler
{
	/*
	 * Some useful information about weight and stuff:
	 * http://minecraftforgetutorials.weebly.com/chest-gen-hooks.html
	 * 
	 * NOTE: More weight you give to an item, more commonly it'll spawn
	 * 
	 * Weight examples:
	 * Bread, iron ingot, saddle - 100
	 * Redstone - 50
	 * Music Disc / Record - 5
	 * Standard Binding Agent (Blood Magic) - 30
	 * Golden Apple - 1
	 */
	public static void init()
	{
		registerNewLoot(ModItems.oceany_chipset, 2, 2, 4, 50, "world.enableDungeonLoot.pretty_oceany_chipset",
				ChestGenHooks.DUNGEON_CHEST, ChestGenHooks.PYRAMID_DESERT_CHEST);
	}
	
	private static boolean registerNewLoot(Item item, int metadata, int min, int max, int weight, String configOption, String... categories)
	{
		return registerNewLoot(item, metadata, null, min, max, weight, configOption, categories);
	}
	
	private static boolean registerNewLoot(Item item, int metadata, NBTTagCompound tag, int min, int max, int weight, String configOption, String... categories)
	{
		if (Config.getBoolean(configOption))
		{
			addItem(categories, generateLootData(item, metadata, tag, min, max, weight));
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static void addItem(String[] categories, WeightedRandomChestContent loot)
	{
		for (String cat : categories)
		{
			ChestGenHooks.addItem(cat, loot);
		}
	}
	
	private static WeightedRandomChestContent generateLootData(Item item, int metadata, int min, int max, int weight)
	{
		return generateLootData(item, metadata, null, min, max, weight);
	}
	
	private static WeightedRandomChestContent generateLootData(Item item, int metadata, NBTTagCompound tag, int min, int max, int weight)
	{
		ItemStack stack = new ItemStack(item, 1, metadata);
		stack.setTagCompound(tag);
		/*
		 * Now with registerNewLoot(Item, Integer, NBTTagCompound, Integer, Integer, Integer, String, String[]) it makes sense..
		if (tag == null)
		{
			Oceany.logger.warn("Report this to mod author: Why don't you use generateLootData(Item, meta, min, max, weight) instead!?");
		}
		*/
		return new WeightedRandomChestContent(stack, min, max, weight);
	}
}