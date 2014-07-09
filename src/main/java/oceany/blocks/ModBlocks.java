package oceany.blocks;

import net.minecraft.block.Block;
import oceany.Refs;
import oceany.blocks.itemblocks.ItemBlockInfuser;
import oceany.blocks.itemblocks.ItemBlockOceanyCore;
import oceany.blocks.itemblocks.ItemBlockOceanyUpgrade;
import oceany.blocks.itemblocks.ItemBlockSquidoGen;
import oceany.blocks.itemblocks.ItemBlockTentacle;
import oceany.tile.TileOceanyCore;
import oceany.tile.TileOceanyInfuser;
import oceany.tile.TileOceanySquidoGen;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks
{
	public static Block oceany_core;
	public static Block oceany_upgrade;
	public static Block oceany_infuser;
	public static Block oceany_squidogen;
	public static Block advanced_oceany_squidogen;
	public static Block tentacle_block;
	
	public static void initBlocks()
	{
		oceany_core = new BlockOceanyCore();
		GameRegistry.registerBlock(oceany_core, ItemBlockOceanyCore.class, "oceany_core");
		
		oceany_upgrade = new BlockOceanyUpgrade();
		GameRegistry.registerBlock(oceany_upgrade, ItemBlockOceanyUpgrade.class, "oceany_upgrade");
		
		tentacle_block = new BlockTentacle();
		GameRegistry.registerBlock(tentacle_block, ItemBlockTentacle.class, "tentacle_block");
		
		oceany_infuser = new BlockOceanyInfuser();
		GameRegistry.registerBlock(oceany_infuser, ItemBlockInfuser.class, "oceany_infuser");
		
		oceany_squidogen = new BlockOceanySquidoGen(false);
		GameRegistry.registerBlock(oceany_squidogen, ItemBlockSquidoGen.class, "oceany_squidogen", false);
		
		advanced_oceany_squidogen = new BlockOceanySquidoGen(true);
		GameRegistry.registerBlock(advanced_oceany_squidogen, ItemBlockSquidoGen.class, "advanced_oceany_squidogen", true);
		
		initTileEntities();
	}
	
	public static void initTileEntities()
	{
		GameRegistry.registerTileEntity(TileOceanyCore.class, Refs.MOD_ID + ":" + "TileOceanyCore");
		GameRegistry.registerTileEntity(TileOceanyInfuser.class, Refs.MOD_ID + ":" + "TileOceanyInfuser");
		GameRegistry.registerTileEntity(TileOceanySquidoGen.class, Refs.MOD_ID + ":" + "TileOceanySquidoGen");
	}
}