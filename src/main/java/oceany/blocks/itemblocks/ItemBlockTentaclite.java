package oceany.blocks.itemblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBlockTentaclite extends ModItemBlockBase
{
	public ItemBlockTentaclite(Block block)
	{
		super(block);
		setUnlocalizedName("tentaclite_ore");
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("Put in Oceany Infuser to");
		list.add("get Infused Tentacle Bar");
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack) {}
}