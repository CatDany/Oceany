package oceany.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StringUtils;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import oceany.tile.TileOceanyCore;
import danylibs.libs.IconRegHelper;
import danylibs.libs.KeyBoardHelper;

public class ItemOceanyChipset extends ModItemBase
{
	/*
	 * 0 = Oceany Chipset,
	 * 1 = Advanced Oceany Chipset,
	 * 2 = Oceany Core Connection Card,
	 * 3 = Biometric Identification Card
	 * 4 = Pretty Oceany Chipset TODO Planned: make Pretty Oceany Chipset a villager's trade [1 emerald = 1 pretty oceany chipset]
	 * 101 = Biometric Identification Card (Bound)
	 */
	private IIcon[] icons = new IIcon[256];
	public static int subItems = 5;
	
	public ItemOceanyChipset()
	{
		super();
		setUnlocalizedName("oceany_chipset");
		setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regItem(this, reg);
		icons[1] = IconRegHelper.regItem(this, reg, "_adv");
		icons[2] = IconRegHelper.regItem(this, reg, "_pretty");
		icons[3] = IconRegHelper.regItem(this, reg, "_cc");		
		icons[4] = IconRegHelper.regItem(this, reg, "_bio");
		icons[101] = IconRegHelper.regItem(this, reg, "_biobound");
	}
	
	/*
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		if (stack.getItemDamage() == 4 && stack.getTagCompound() != null && stack.getTagCompound().hasKey("player"))
		{
			return icons[101]; 
		}
		return icons[stack.getItemDamage()];
	}
	*/
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "|" + stack.getItemDamage();
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < subItems; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack)
	{
		switch (stack.getItemDamage())
		{
		case 3:
			if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("CoreData"))
			{
				int x = stack.getTagCompound().getCompoundTag("CoreData").getInteger("x");
				int y = stack.getTagCompound().getCompoundTag("CoreData").getInteger("y");
				int z = stack.getTagCompound().getCompoundTag("CoreData").getInteger("z");
				list.add("Bound to Oceany Core at " + x + ", " + y + ", " + z);
			}
			break;
		case 4:
			if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("player"))
			{
				list.add("Bound to " + stack.getTagCompound().getString("player"));
			}
			list.add("Not Yet Implemented");
			break;
		}
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack)
	{
		switch (stack.getItemDamage())
		{
		case 2:
			list.add("Can be found in dungeons and desert temples");
			break;
		case 3:
			list.add("Used to connect machines to Oceany Core");
			break;
		case 4:
			list.add("Used to identify players");
		}
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (stack.getItemDamage() == 4)
		{
			String targetName = null;
			if (player.isSneaking())
			{
				targetName = player.getCommandSenderName();
			}
			else
			{
				// TODO Need to test MovingObjectPosition on players
				// Hey, Dear GitHuber. If you're sure that this will work, please leave a comment
				// Otherwise create a pull request with the right way to do it
				// I owe you a credit! ;)
				MovingObjectPosition obj = getMovingObjectPositionFromPlayer(world, player, false);
				if (obj != null && obj.typeOfHit == MovingObjectType.ENTITY && obj.entityHit instanceof EntityPlayer)
				{
					EntityPlayer target = (EntityPlayer)obj.entityHit;
					targetName = player.getCommandSenderName();
				}
			}
			if (!StringUtils.isNullOrEmpty(targetName))
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setString("player", targetName);
				stack.setTagCompound(tag);
			}
		}
		return stack;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world,
			int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		if (stack.getItemDamage() == 3)
		{
			if (world.getTileEntity(x, y, z) instanceof TileOceanyCore)
			{
				NBTTagCompound tag = new NBTTagCompound();
				NBTTagCompound coreData = new NBTTagCompound();
				coreData.setInteger("x", x);
				coreData.setInteger("y", y);
				coreData.setInteger("z", z);
				tag.setTag("CoreData", coreData);
				stack.setTagCompound(tag);
				return true;
			}
		}
		return false;
	}
}