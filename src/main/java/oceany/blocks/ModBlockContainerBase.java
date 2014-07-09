package oceany.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class ModBlockContainerBase extends ModBlockBase implements ITileEntityProvider
{
	public ModBlockContainerBase(Material material)
	{
		super(material);
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		super.breakBlock(world, x, y, z, block, meta);
		world.removeTileEntity(x, y, z);
	}
	
	public boolean onBlockEventReceived(World world, int x, int y, int z, int par5, int par6)
	{
		super.onBlockEventReceived(world, x, y, z, par5, par6);
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile != null ? tile.receiveClientEvent(par5, par6) : false;
	}
}