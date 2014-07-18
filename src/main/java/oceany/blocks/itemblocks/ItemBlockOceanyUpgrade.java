package oceany.blocks.itemblocks;

import java.util.List;

import danylibs.libs.LocalizationHelper;
import oceany.api.IOceanyBlock;
import oceany.blocks.BlockOceanyUpgrade;
import mcp.mobius.waila.api.IWailaBlock;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockOceanyUpgrade extends ModItemBlockBase
{
	public ItemBlockOceanyUpgrade(Block block)
	{
		super(block);
		setUnlocalizedName("oceany_upgrade");
		setHasSubtypes(true);
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack)
	{
		String upgradeName = StatCollector.translateToLocal(getUnlocalizedName() + "|" + stack.getItemDamage() +".desc");
		list.add(upgradeName);
		list.add(LocalizationHelper.get("info.waila.oceany_core.perTick") + " : " + BlockOceanyUpgrade.energyUsage[stack.getItemDamage()]);
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		list.add("Allows you to upgrade");
		list.add("Oceany Core. For it to");
		list.add("work, it needs to be");
		list.add("placed on top of the");
		list.add("core. You can stack");
		list.add("upgrades by placing");
		list.add("them on top of eachother");
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < BlockOceanyUpgrade.maxUpgrades; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
}