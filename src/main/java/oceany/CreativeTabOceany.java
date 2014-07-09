package oceany;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import oceany.items.ModItems;

public class CreativeTabOceany extends CreativeTabs
{
	public static final CreativeTabs tab = new CreativeTabOceany();
	
	public CreativeTabOceany()
	{
		super(Refs.MOD_ID.toLowerCase());
	}
	
	@Override
	public Item getTabIconItem()
	{
		return ModItems.squid_tentacle;
	}
	
	@Override
	public String getTabLabel()
	{
		return Refs.MOD_ID.toLowerCase();
	}
}