package oceany.blocks.itemblocks;

import java.util.List;

import oceany.items.ModItems;
import baubles.api.BaublesApi;
import danylibs.libs.ItemUtils;
import danylibs.libs.KeyBoardHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ModItemBlockBase extends ItemBlock
{
	public ModItemBlockBase(Block block)
	{
		super(block);
	}
	
	public abstract void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack);
	
	public abstract void addTooltipInfo(List list, EntityPlayer player, ItemStack stack);
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		addTooltipInfo(list, player, stack);
		
		boolean hasBrain = false;
		for (int i = 0; i < player.inventory.getSizeInventory(); i++)
		{
			ItemStack maybeBrain = player.inventory.getStackInSlot(i);
			if (maybeBrain != null && ItemUtils.compare(maybeBrain, ModItems.danys_brain) && maybeBrain.getItemDamage() != 0)
			{
				hasBrain = true;
				break;
			}
		}
		for (int i = 0; i < BaublesApi.getBaubles(player).getSizeInventory(); i++)
		{
			ItemStack maybeBrain = BaublesApi.getBaubles(player).getStackInSlot(i);
			if (maybeBrain != null && ItemUtils.compare(maybeBrain, ModItems.danys_brain) && maybeBrain.getItemDamage() != 0)
			{
				hasBrain = true;
				break;
			}
		}
		
		if (KeyBoardHelper.isShiftDown() && hasBrain)
		{
			list.add("");
			list.add("=== Dany's Knowledge ===");
			addDetailedInfoToList(list, player, stack);
		}
	}
}