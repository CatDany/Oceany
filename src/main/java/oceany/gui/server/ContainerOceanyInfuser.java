package oceany.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import oceany.gui.SlotInput;
import oceany.gui.SlotOutput;
import oceany.tile.TileOceanyInfuser;

public class ContainerOceanyInfuser extends Container
{
	public final TileOceanyInfuser tile;
	
	public ContainerOceanyInfuser(IInventory player, TileOceanyInfuser tile)
	{
		super();
		this.tile = tile;
		
		addSlotToContainer(new SlotInput(tile, 0, 58, 42));
		addSlotToContainer(new SlotOutput(tile, 1, 102, 42));
		
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 90 + i * 18));
			}
		}
		
		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(player, i, 8 + i * 18, 148));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot)
	{
		return null;
	}
}