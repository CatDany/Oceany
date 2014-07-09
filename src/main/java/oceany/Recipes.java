package oceany;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import oceany.api.OceanyRecipeManager.InfuserRecipeManager;
import oceany.blocks.ModBlocks;
import oceany.items.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{
	public static void initOreDictNames()
	{
		OreDictionary.registerOre("ingotSquidTentacle", new ItemStack(ModItems.tentacle_bar, 1, 0));
		OreDictionary.registerOre("ingotInfusedSquidTentacle", new ItemStack(ModItems.tentacle_bar, 1, 1));
		OreDictionary.registerOre("blockSquidTentacle", new ItemStack(ModBlocks.tentacle_block, 1, 0));
		OreDictionary.registerOre("blockInfusedSquidTentacle", new ItemStack(ModBlocks.tentacle_block, 1, 1));
	}
	
	public static void initRecipes()
	{
		initOreDictNames();
		
		addOreRecipe(ModItems.tentacle_bar, 1, 0,
				"ttt", "ttt",
				't', new ItemStack(ModItems.squid_tentacle)
				);
		
		addOreRecipe(ModBlocks.tentacle_block, 1, 0,
				"bbb", "bbb", "bbb",
				'b', new ItemStack(ModItems.tentacle_bar)
				);
		
		addShapelessOreRecipe(ModItems.tentacle_bar, 9, 0,
				new ItemStack(ModBlocks.tentacle_block)
				);
		
		addOreRecipe(ModItems.tentacle_bar, 1, 1,
				"ttt", "ttt",
				't', new ItemStack(ModItems.squid_tentacle, 1, 1)
				);
		
		addOreRecipe(ModBlocks.tentacle_block, 1, 1,
				"bbb", "bbb", "bbb",
				'b', new ItemStack(ModItems.tentacle_bar, 1, 1)
				);
		
		addShapelessOreRecipe(ModItems.tentacle_bar, 9, 1,
				new ItemStack(ModBlocks.tentacle_block, 1, 1)
				);
		
		addOreRecipe(ModBlocks.oceany_core, 1, 0,
				"igi", "gtg", "igi",
				'i', Items.gold_ingot,
				'g', Items.glowstone_dust,
				't', "blockSquidTentacle"
				);
		
		addOreRecipe(ModBlocks.oceany_upgrade, 1, 0,
				"t t", "tpt", "t t",
				't', "ingotSquidTentacle",
				'p', new ItemStack(Items.potionitem, 1, 8205)
				);
		
		addOreRecipe(ModBlocks.oceany_upgrade, 1, 1,
				"t t", "tpt", "t t",
				't', "ingotSquidTentacle",
				'p', new ItemStack(Items.potionitem, 1, 8198)
				);
		
		addOreRecipe(ModBlocks.oceany_upgrade, 1, 3,
				"t t", "tpt", "t t",
				't', "ingotInfusedSquidTentacle",
				'p', new ItemStack(Items.potionitem, 1, 8194)
				);
		
		addOreRecipe(ModBlocks.oceany_infuser, 1, 0,
				"opo", "ete", "obo",
				'o', Blocks.obsidian,
				'e', Items.emerald,
				't', "blockSquidTentacle",
				'p', Items.blaze_powder,
				'b', Items.brewing_stand);
		
		addOreRecipe(ModItems.oceanic_cutlass, 1, 0,
				"t", "t", "s",
				't', "ingotInfusedSquidTentacle",
				's', "stickWood");
		
		addOreRecipe(ModItems.bottomless_bucket, 1, 0,
				"i i", " b ",
				'i', Items.iron_ingot,
				'b', Blocks.iron_bars);
		
		//--- Mod Recipes ---//
		InfuserRecipeManager.instance().addRecipe(
				new ItemStack(ModItems.squid_tentacle, 1, 0),
				new ItemStack(ModItems.squid_tentacle, 1, 1),
				150
				);
		
		InfuserRecipeManager.instance().addRecipe(
				new ItemStack(ModItems.tentacle_bar, 1, 0),
				new ItemStack(ModItems.tentacle_bar, 1, 1),
				900
				);
		
		InfuserRecipeManager.instance().addRecipe(
				new ItemStack(ModBlocks.tentacle_block, 1, 0),
				new ItemStack(ModBlocks.tentacle_block, 1, 1),
				8100
				);
	}
	
	private static void addOreRecipe(ItemStack output, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
	}
	
	private static void addOreRecipe(Item output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output, amount, data), recipe));
	}
	
	private static void addOreRecipe(Block output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(output, amount, data), recipe));
	}
	
	private static void addShapelessOreRecipe(ItemStack output, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(output, recipe));
	}
	
	private static void addShapelessOreRecipe(Item output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output, amount, data), recipe));
	}
	
	private static void addShapelessOreRecipe(Block output, int amount, int data, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(output, amount, data), recipe));
	}
}