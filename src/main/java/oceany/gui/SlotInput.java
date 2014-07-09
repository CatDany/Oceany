package oceany.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot
{
	public SlotInput(IInventory inventory, int slot, int x, int y)
	{
		super(inventory, slot, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return inventory.isItemValidForSlot(slotNumber, stack);
	}
}