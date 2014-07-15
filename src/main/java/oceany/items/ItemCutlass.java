package oceany.items;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.Language;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import oceany.CreativeTabOceany;
import oceany.Refs;
import oceany.tile.TileOceanyCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import danylibs.libs.IconRegHelper;
import danylibs.libs.LocalizationHelper;
import danylibs.libs.PlayerUtils;
import danylibs.libs.SoundHelper;

public class ItemCutlass extends ItemSword
{
	private static final ToolMaterial mat = EnumHelper.addToolMaterial(Refs.MOD_ID + ":Cutlass", 0, 51, 1.0F, -4.0F, 25);
	
	public ItemCutlass()
	{
		super(mat);
		setUnlocalizedName("oceanic_cutlass");
		setCreativeTab(CreativeTabOceany.tab);
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		itemIcon = IconRegHelper.regItem(this, reg);
	}
	
	@Override
	public void addInformation(ItemStack stack,
			EntityPlayer player, List list, boolean par4)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("Energy") || stack.getTagCompound().getInteger("Energy") == 0)
		{
			list.add(LocalizationHelper.get("info.oceanic_cutlass.not_charged"));
		}
		else
		{
			list.add(LocalizationHelper.get("info.oceanic_cutlass.charged", stack.getTagCompound().getInteger("Energy")));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		Language pirate = new Language("en_PT", "PT", "en", false);
		Minecraft.getMinecraft().getLanguageManager().setCurrentLanguage(pirate);
		Minecraft.getMinecraft().gameSettings.language = pirate.getLanguageCode();
		Minecraft.getMinecraft().gameSettings.saveOptions();
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player,
			World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TileOceanyCore && player.isSneaking())
		{
			TileOceanyCore tile = (TileOceanyCore)world.getTileEntity(x, y, z);
			
			if (tile.tier < 3)
			{
				PlayerUtils.print(player, "This Oceany Core cannot handle charging. Upgrade it to Tier 3.");
				return true;
			}
			
			if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("Energy"))
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("Energy", 0);
				stack.setTagCompound(tag);
			}
			int maxEnergy = 50000;
			int chargeRate = 2000;
			int currentEnergy = stack.getTagCompound().getInteger("Energy");
			if (currentEnergy == maxEnergy)
			{
				SoundHelper.playSoundEntity(player, "random.successful_hit", 0.5F);
				return true;
			}
			else if (currentEnergy > maxEnergy - chargeRate && currentEnergy < maxEnergy)
			{
				int required = maxEnergy - currentEnergy;
				if (tile.consumeEnergy(required))
				{
					stack.getTagCompound().setInteger("Energy", maxEnergy);
					SoundHelper.playSoundEntity(player, "random.splash", 0.5F);
				}
				else
				{
					SoundHelper.playSoundEntity(player, "random.splash", 0.5F);
				}
				return true;
			}
			else if (currentEnergy <= maxEnergy - chargeRate)
			{
				if (tile.consumeEnergy(chargeRate))
				{
					stack.getTagCompound().setInteger("Energy", currentEnergy + chargeRate);
					SoundHelper.playSoundEntity(player, "random.splash", 0.5F);
				}
				else
				{
					SoundHelper.playSoundEntity(player, "random.orb", 0.5F);
				}
				return true;
			}
			updateItemDamage(stack);
		}
		return false;
	}
	
	@Override
	public boolean isRepairable()
	{
		return false;
	}
	
	private void updateItemDamage(ItemStack stack)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("Energy"))
		{
			stack.setItemDamage(51);
		}
		else
		{
			double maxEnergy = 50000;
			double currentEnergy = stack.getTagCompound().getInteger("Energy");
			
			if (currentEnergy >= maxEnergy)
			{
				stack.setItemDamage(1);
			}
			else if (currentEnergy == 0)
			{
				stack.setItemDamage(51);
			}
			else
			{
				stack.setItemDamage((int)(51 - (currentEnergy / maxEnergy * 50)));
			}
		}
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		ItemStack stackEmpty = new ItemStack(item);
		ItemStack stackFull = new ItemStack(item);
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("Energy", 0);
		stackEmpty.setTagCompound(tag);
		stackEmpty.setItemDamage(51);
		tag = new NBTTagCompound();
		tag.setInteger("Energy", 50000);
		stackFull.setTagCompound(tag);
		stackFull.setItemDamage(1);
		list.add(stackEmpty);
		list.add(stackFull);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase source)
	{
		if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("Energy"))
		{
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("Energy", 0);
			stack.setTagCompound(tag);
		}
		int energy = stack.getTagCompound().getInteger("Energy");
		int perHit = 100; // 50000/100=500 hits total (x2 durability of an iron sword)
		if (energy < perHit)
		{
			// ???
		}
		else
		{
			stack.getTagCompound().setInteger("Energy", energy - perHit);
			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)source), 6);
		}
		updateItemDamage(stack);
		return true;
	}
}