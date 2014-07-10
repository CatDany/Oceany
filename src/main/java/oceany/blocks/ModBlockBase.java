package oceany.blocks;

import danylibs.libs.IconRegHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import oceany.CreativeTabOceany;

public abstract class ModBlockBase extends Block
{
	public ModBlockBase(Material material)
	{
		super(material);
		setCreativeTab(CreativeTabOceany.tab);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		blockIcon = IconRegHelper.regBlock(this, reg);
	}
}