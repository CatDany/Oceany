package oceany.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import oceany.Oceany;
import oceany.api.IOceanyBlock;
import oceany.tile.TileOceanyCore;

public class BlockOceanyCore extends ModBlockContainerBase implements IOceanyBlock
{
	public BlockOceanyCore()
	{
		super(Material.rock);
		setBlockName("oceany_core");
		setHardness(4.5F);
		setResistance(45.0F);
		setHarvestLevel("pickaxe", 1);
		setStepSound(soundTypeStone);
		setLightLevel(1.0F);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side,
			float hitX, float hitY, float hitZ)
	{
		if (world.getTileEntity(x, y, z) instanceof TileOceanyCore)
		{
			TileOceanyCore tile = (TileOceanyCore)world.getTileEntity(x, y, z);
			FMLNetworkHandler.openGui(player, Oceany.instance, 0, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileOceanyCore();
	}
}
