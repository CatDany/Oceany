package oceany.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import oceany.CreativeTabOceany;
import oceany.libs.IconRegHelper;

public class ItemSquidTentacle extends ModItemBase
{
	private IIcon[] icons = new IIcon[2];
	
	public ItemSquidTentacle()
	{
		super();
		setUnlocalizedName("squid_tentacle");
		setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regItem(this, reg);
		icons[1] = IconRegHelper.regItem(this, reg, "_infused");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "|" + stack.getItemDamage();
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@Override
	public int getMetadata(int meta)
	{
		return meta;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
}