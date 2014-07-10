package oceany.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import oceany.Oceany;
import oceany.api.IOceanyBlock;
import oceany.tile.TileOceanyInfuser;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import danylibs.libs.IconRegHelper;
import danylibs.libs.RotationUtils;

public class BlockOceanyInfuser extends ModBlockContainerBase implements IOceanyBlock
{
	private IIcon icon_top;
	private IIcon icon_front;
	
	public BlockOceanyInfuser()
	{
		super(Material.rock);
		setBlockName("oceany_infuser");
		setHardness(4.5F);
		setResistance(45.0F);
		setHarvestLevel("pickaxe", 1);
		setStepSound(soundTypeStone);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = IconRegHelper.regBlock(this, reg, "_side");
		icon_top = IconRegHelper.regBlock(this, reg, "_top");
		icon_front = IconRegHelper.regBlock(this, reg, "_front");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		 return side == 1 ? icon_top : (side == 0 ? icon_top : (side != meta ? blockIcon : icon_front));
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side,
			float hitX, float hitY, float hitZ)
	{
		if (world.getTileEntity(x, y, z) instanceof TileOceanyInfuser)
		{
			TileOceanyInfuser tile = (TileOceanyInfuser)world.getTileEntity(x, y, z);
			FMLNetworkHandler.openGui(player, Oceany.instance, 1, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase living, ItemStack stack)
	{
		int meta = RotationUtils.getMetadataForSidedBlock(x, y, z, RotationUtils.getRotationFromEntity(living));
		world.setBlockMetadataWithNotify(x, y, z, meta, 3);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int par2)
	{
		return new TileOceanyInfuser();
	}
}