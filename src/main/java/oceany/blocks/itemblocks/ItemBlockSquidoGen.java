package oceany.blocks.itemblocks;

import net.minecraft.block.Block;

public class ItemBlockSquidoGen extends ModItemBlockBase
{
	public ItemBlockSquidoGen(Block block, Boolean adv)
	{
		super(block);
		setUnlocalizedName(adv ? "advanced_oceany_squidogen" : "oceany_squidogen");
	}
}