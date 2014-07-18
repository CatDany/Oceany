package oceany.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import danylibs.libs.IconRegHelper;
import danylibs.libs.ItemUtils;
import danylibs.libs.LocalizationHelper;
import danylibs.libs.Paragraph;
import danylibs.libs.PlayerUtils;

public class ItemBrain extends ModItemBase implements IBauble
{
	private static final int subItems = 2;
	
	private IIcon[] icons = new IIcon[subItems];
	
	public ItemBrain()
	{
		super();
		setUnlocalizedName("danys_brain");
		setMaxStackSize(1);
		setHasSubtypes(true);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons[0] = IconRegHelper.regItem(this, reg);
		icons[1] = IconRegHelper.regItem(this, reg, "_ring");
	}
	
	@Override
	public void addTooltipInfo(List list, EntityPlayer player, ItemStack stack)
	{
		if (stack.getTagCompound() != null && stack.getTagCompound().hasKey("Original") && stack.getTagCompound().getBoolean("Original"))
		{
			list.add(OriginalTextHandler.getNextString(Paragraph.lapis, Paragraph.purple));
		}
		else
		{
			list.add(Paragraph.purple + LocalizationHelper.get("info.danys_brain.fake"));
		}
	}
	
	@Override
	public void addDetailedInfoToList(List list, EntityPlayer player, ItemStack stack) {}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
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
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "|" + stack.getItemDamage();
	}
	
	@Override
	public boolean canEquip(ItemStack stack, EntityLivingBase living)
	{
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)living;
			for (int i = 0; i < BaublesApi.getBaubles(player).getSizeInventory(); i++)
			{
				ItemStack maybeBrain = BaublesApi.getBaubles(player).getStackInSlot(i);
				if (maybeBrain != null && ItemUtils.compare(maybeBrain, ModItems.danys_brain))
				{
					PlayerUtils.print(player, "You already have Ring of Dany's Knowledge on! You can't be TOO smart. Nope-nope, don't even try!");
					return false;
				}
			}
		}
		return stack.getItemDamage() == 1;
	}
	
	@Override
	public boolean canUnequip(ItemStack stack, EntityLivingBase living)
	{
		return true;
	}
	
	@Override
	public BaubleType getBaubleType(ItemStack arg0)
	{
		return BaubleType.RING;
	}
	
	@Override
	public void onEquipped(ItemStack arg0, EntityLivingBase arg1) {}
	
	@Override
	public void onUnequipped(ItemStack arg0, EntityLivingBase arg1) {}
	
	@Override
	public void onWornTick(ItemStack arg0, EntityLivingBase arg1) {}
	
	private static class OriginalTextHandler
	{
		private static final String originalStr = LocalizationHelper.get("info.danys_brain.original");
		private static final int maxLetter = originalStr.toCharArray().length;
		
		private static int currentLetter = 0;
		private static int currentTick = 0;
		
		private static void tick()
		{
			if (currentTick >= 20)
			{
				nextLetter();
				currentTick = 0;
			}
			else
			{
				currentTick++;
			}
		}
		
		private static int nextLetter()
		{
			if (currentLetter >= maxLetter)
			{
				currentLetter = 0;
			}
			currentLetter++;
			return currentLetter;
		}
		
		private static String getString(String defaultColor, String highlightColor)
		{
			String left = originalStr.substring(0, currentLetter - 1);
			String highlightedLetter = new String(new char[] {originalStr.charAt(currentLetter)});
			String right = originalStr.substring(currentLetter + 1);
			return defaultColor + left + highlightColor + highlightedLetter + defaultColor + right + Paragraph.reset;
		}
		
		private static String getNextString(String defaultColor, String highlightColor)
		{
			tick();
			return getString(defaultColor, highlightColor);
		}
	}
}