package oceany.compat;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import oceany.Oceany;
import oceany.Refs;
import oceany.blocks.ModBlocks;
import oceany.blocks.itemblocks.ItemBlockOceanyUpgrade;
import oceany.libs.ItemUtils;
import oceany.libs.LocalizationHelper;
import oceany.libs.Paragraph;
import oceany.tile.TileOceanyCore;
import oceany.tile.TileOceanyInfuser;
import oceany.tile.TileOceanySquidoGen;

public class CompatWaila implements IWailaDataProvider
{
	private static final IWailaDataProvider instance = new CompatWaila();
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor data, IWailaConfigHandler config)
	{
		return null;
	}
	
	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> list,
			IWailaDataAccessor data, IWailaConfigHandler config)
	{
		if (ItemUtils.compare(data.getBlock(), ModBlocks.oceany_upgrade))
		{
			((ItemBlockOceanyUpgrade)Item.getItemFromBlock(ModBlocks.oceany_upgrade)).addInformation(data.getStack(), data.getPlayer(), list, false);
		}
		if (data.getTileEntity() instanceof TileOceanyCore)
		{
			TileOceanyCore tile = (TileOceanyCore)data.getTileEntity();
			list.add(LocalizationHelper.get("info.waila.oceany_core.tier") + " : " + tile.tier);
			list.add(LocalizationHelper.get("info.waila.oceany_core.energy") + " : " + tile.energy);
			list.add(LocalizationHelper.get("info.waila.oceany_core.perTick") + " : " + tile.getEnergyPerTick());
		}
		if (data.getTileEntity() instanceof TileOceanyInfuser)
		{
			TileOceanyInfuser tile = (TileOceanyInfuser)data.getTileEntity();
			if (!tile.isCoreValid())
			{
				list.add(Paragraph.rose + LocalizationHelper.get("info.oceany_infuser.core_not_found"));
			}
			else if (tile.getStackInSlot(0) != null && tile.isCoreValid())				
			{
				list.add(LocalizationHelper.get("info.waila.oceany_infuser.process") + " : " + Paragraph.light_green + (int)tile.process + "%");
				list.add("");
				list.add(LocalizationHelper.get("info.waila.oceany_infuser.input") + " : " + Paragraph.cyan + LocalizationHelper.get(tile.getStackInSlot(0).getUnlocalizedName() + ".name"));
				list.add(LocalizationHelper.get("info.waila.oceany_infuser.output") + " : " + Paragraph.dark_purple + LocalizationHelper.get(tile.getOutputFromInput(tile.getStackInSlot(0)).getUnlocalizedName() + ".name"));
				list.add(LocalizationHelper.get("info.waila.oceany_infuser.cost") + " : " + Paragraph.gold + (int)(tile.getEnergyCostFromInput(tile.inventory[0]) - (tile.process / 100 * tile.getEnergyCostFromInput(tile.inventory[0]))));
			}
			else if (tile.getStackInSlot(0) == null && tile.getStackInSlot(1) != null)
			{
				list.add(Paragraph.green + LocalizationHelper.get("info.waila.oceany_infuser.done"));
			}
		}
		if (data.getTileEntity() instanceof TileOceanySquidoGen)
		{
			TileOceanySquidoGen tile = (TileOceanySquidoGen)data.getTileEntity();
			if (tile.isCoreValid())
			{
				list.add(Paragraph.green + LocalizationHelper.get("info.oceany_infuser.connected_to_core"));
			}
			else
			{
				list.add(Paragraph.rose + LocalizationHelper.get("info.oceany_infuser.core_not_found"));
			}
		}
		return list;
	}
	
	@Override
	public List<String> getWailaHead(ItemStack stack, List<String> list,
			IWailaDataAccessor data, IWailaConfigHandler config)
	{
		return list;
	}
	
	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> list,
			IWailaDataAccessor data, IWailaConfigHandler config)
	{
		return list;
	}
	
	public static void register(IWailaRegistrar reg)
	{
		Oceany.logger.info("Adding Waila compatibility for " + Refs.MOD_ID);
		reg.addConfig(Refs.MOD_NAME, Refs.MOD_ID.toLowerCase() + "." + "show_info", "Show Additional Oceany Info");
		reg.registerBodyProvider(instance, Block.class);
	}
}