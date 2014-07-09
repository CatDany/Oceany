package oceany.blocks;

import oceany.libs.IconRegHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockTentacle extends ModBlockBase
{
	private IIcon[] icons = new IIcon[2];
	
	public BlockTentacle()
	{
		super(Material.clay);
		setBlockName("tentacle_block");
		setHardness(3.0F);
		setResistance(30.0F);
		setStepSound(soundTypeGravel);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regBlock(this, reg);
		icons[1] = IconRegHelper.regBlock(this, reg, "_infused");
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
}