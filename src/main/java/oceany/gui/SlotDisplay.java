package oceany.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDisplay extends Slot
{
	protected ItemStack stack;
	
	public SlotDisplay(ItemStack stack, int x, int y)
	{
		super(null, 0, x, y);
		this.stack = stack;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int amount)
	{
		return stack;
	}
	
	@Override
	public boolean getHasStack()
	{
		return true;
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 64;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
	
	@Override
	public ItemStack getStack()
	{
		return stack;
	}
}