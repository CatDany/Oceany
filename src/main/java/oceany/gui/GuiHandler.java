package oceany.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import oceany.gui.client.GuiOceanyCore;
import oceany.gui.client.GuiOceanyInfuser;
import oceany.gui.server.ContainerOceanyCore;
import oceany.gui.server.ContainerOceanyInfuser;
import oceany.tile.TileOceanyCore;
import oceany.tile.TileOceanyInfuser;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
		if (ID == 0)
		{
			if (world.getTileEntity(x, y, z) instanceof TileOceanyCore)
			{
				TileOceanyCore tile = (TileOceanyCore)world.getTileEntity(x, y, z);
				return new GuiOceanyCore(tile);
			}
		}
		else if (ID == 1)
		{
			if (world.getTileEntity(x, y, z) instanceof TileOceanyInfuser)
			{
				TileOceanyInfuser tile = (TileOceanyInfuser)world.getTileEntity(x, y, z);
				return new GuiOceanyInfuser(player.inventory, tile);
			}
		}
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z)
	{
		if (ID == 0)
		{
			if (world.getTileEntity(x, y, z) instanceof TileOceanyCore)
			{
				TileOceanyCore tile = (TileOceanyCore)world.getTileEntity(x, y, z);
				tile.markDirty();
				return new ContainerOceanyCore(tile);
			}
		}
		else if (ID == 1)
		{
			if (world.getTileEntity(x, y, z) instanceof TileOceanyInfuser)
			{
				TileOceanyInfuser tile = (TileOceanyInfuser)world.getTileEntity(x, y, z);
				return new ContainerOceanyInfuser(player.inventory, tile);
			}
		}
		return null;
	}
}