package oceany.blocks;

import danylibs.libs.IconRegHelper;
import oceany.tile.TileOceanySquidoGen;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOceanySquidoGen extends ModBlockContainerBase
{
	private IIcon icon_top;
	private IIcon icon_front;
	private boolean isAdvanced;
	
	public BlockOceanySquidoGen(boolean adv)
	{
		super(Material.rock);
		setBlockName(adv ? "advanced_oceany_squidogen" : "oceany_squidogen");
		setHardness(4.5F);
		setResistance(45.0F);
		setHarvestLevel("pickaxe", 1);
		setStepSound(soundTypeStone);
		this.isAdvanced = adv;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = IconRegHelper.regBlock(this, reg, isAdvanced ? "_adv_side" : "_side");
		icon_top = IconRegHelper.regBlock(this, reg, isAdvanced ? "_adv_top" : "_top");
		icon_front = IconRegHelper.regBlock(this, reg, isAdvanced ? "_adv_front" : "_front");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		 return side == 1 ? icon_top : (side == 0 ? icon_top : (side != meta ? blockIcon : icon_front));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileOceanySquidoGen(isAdvanced);
	}
}