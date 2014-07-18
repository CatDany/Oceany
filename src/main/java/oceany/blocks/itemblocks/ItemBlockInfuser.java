package oceany.blocks.itemblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockInfuser extends ModItemBlockBase
{
	public ItemBlockInfuser(Block block)
	{
		super(block);
		setUnlocalizedName("oceany_infuser");
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("You can infuse squid tentacles");
		list.add("with oceanic energy to get");
		list.add("cultivated tentacles which");
		list.add("being used in all kinds of");
		list.add("advanced Oceany things");
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack) {}
}