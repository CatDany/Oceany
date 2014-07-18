package oceany.blocks.itemblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockOceanyCore extends ModItemBlockBase
{
	public ItemBlockOceanyCore(Block block)
	{
		super(block);
		setUnlocalizedName("oceany_core");
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("This is the core of your Oceany");
		list.add("");
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack) {}
}