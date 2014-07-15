package oceany.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import danylibs.libs.IconRegHelper;
import danylibs.libs.Paragraph;
import danylibs.libs.PlayerUtils;
import danylibs.libs.RotationUtils;
import oceany.Oceany;
import oceany.tile.TileOceanyInfuser;
import oceany.tile.TileOceanySquidoGen;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockOceanySquidoGen extends ModBlockContainerBase
{
	private IIcon icon_front;
	private IIcon icon_top;
	private IIcon icon_front_adv;
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
		blockIcon = IconRegHelper.regBlock(this, reg, "_side");
		icon_top = IconRegHelper.regBlock(this, reg, "_top");
		icon_front = IconRegHelper.regBlock(this, reg, "_front");
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side,
			float hitX, float hitY, float hitZ)
	{
		if (world.getTileEntity(x, y, z) instanceof TileOceanySquidoGen)
		{
			TileOceanySquidoGen tile = (TileOceanySquidoGen)world.getTileEntity(x, y, z);
			PlayerUtils.print(player, (!tile.isCoreValid() ? Paragraph.rose : "") + (tile.isAdvanced() ? "Advanced " : "") + "SquidoGen is " + (tile.isCoreValid() ? "running smoothly." : "not able to find Oceany Core!"));
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