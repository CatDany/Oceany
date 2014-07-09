package oceany.gui.server;

import oceany.tile.TileOceanyCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerOceanyCore extends Container
{
	public final TileOceanyCore tile;
	
	public ContainerOceanyCore(TileOceanyCore tile)
	{
		super();
		this.tile = tile;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
}