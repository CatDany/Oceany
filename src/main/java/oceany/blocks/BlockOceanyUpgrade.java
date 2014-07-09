package oceany.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import oceany.Oceany;
import oceany.api.IOceanyBlock;
import oceany.libs.IconRegHelper;
import oceany.libs.ItemUtils;
import oceany.tile.TileOceanyCore;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

public class BlockOceanyUpgrade extends ModBlockBase implements IOceanyBlock
{
	public static int maxUpgrades = 4;
	public static int[] energyUsage = {1, 1, 0, 2};
	private IIcon[] icons = new IIcon[maxUpgrades];
	
	public BlockOceanyUpgrade()
	{
		super(Material.rock);
		setBlockName("oceany_upgrade");
		setHardness(4.5F);
		setResistance(45.0F);
		setHarvestLevel("pickaxe", 1);
		setStepSound(soundTypeStone);
		setBlockBounds(6 * 0.0625F, 0.0F, 6 * 0.0625F, 10 * 0.0625F, 1.0F, 10 * 0.0625F);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regBlock(this, reg, "_0");
		icons[1] = IconRegHelper.regBlock(this, reg, "_1");
		icons[2] = IconRegHelper.regBlock(this, reg, "_2");
		icons[3] = IconRegHelper.regBlock(this, reg, "_3");
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int side,
			float hitX, float hitY, float hitZ)
	{
		if (player.inventory.getCurrentItem() != null && !ItemUtils.compare(Block.getBlockFromItem(player.inventory.getCurrentItem().getItem()), Blocks.air) && side == 1)
			return false;
		
		for (int iterY = y; iterY >= y - maxUpgrades; iterY--)
		{
			if (world.getTileEntity(x, iterY, z) instanceof TileOceanyCore)
			{
				TileOceanyCore tile = (TileOceanyCore)world.getTileEntity(x, iterY, z);
				FMLNetworkHandler.openGui(player, Oceany.instance, 0, world, x, iterY, z);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canProvidePower()
	{
		return true;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side)
	{
		if (side == 0)
			return 1;
		else
			return 0;
	}
}