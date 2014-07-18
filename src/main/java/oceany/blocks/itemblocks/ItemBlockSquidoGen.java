package oceany.blocks.itemblocks;

import java.util.List;

import oceany.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemBlockSquidoGen extends ModItemBlockBase
{
	public ItemBlockSquidoGen(Block block, Boolean adv)
	{
		super(block);
		setUnlocalizedName(adv ? "advanced_oceany_squidogen" : "oceany_squidogen");
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("Generates oceanic power");
		list.add("from killing squids");
		list.add("One squid - " + Config.getInteger("settings.squidogen.energy_per_squid") + " points");
		if (stack.getItemDamage() == 1)
		{
			list.add("Advanced one also collects");
			list.add("all the drops from squid");
			list.add("to an inventory below it");
		}
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack) {}
}