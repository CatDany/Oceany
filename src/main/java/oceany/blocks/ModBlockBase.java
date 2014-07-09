package oceany.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import oceany.CreativeTabOceany;
import oceany.libs.IconRegHelper;

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