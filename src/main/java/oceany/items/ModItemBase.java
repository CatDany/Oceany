package oceany.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import oceany.CreativeTabOceany;
import baubles.api.BaublesApi;
import danylibs.libs.IconRegHelper;
import danylibs.libs.ItemUtils;
import danylibs.libs.KeyBoardHelper;

public abstract class ModItemBase extends Item
{
	public ModItemBase()
	{
		super();
		setCreativeTab(CreativeTabOceany.tab);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
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