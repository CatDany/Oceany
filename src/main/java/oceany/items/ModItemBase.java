package oceany.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import oceany.CreativeTabOceany;
import oceany.libs.IconRegHelper;

public class ModItemBase extends Item
{
	public ModItemBase()
	{
		super();
		setCreativeTab(CreativeTabOceany.tab);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
}