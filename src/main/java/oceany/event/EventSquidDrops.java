package oceany.event;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import oceany.damage.EntityDamageSquidoGen;
import oceany.damage.ModDamageSources;
import oceany.items.ModItems;
import oceany.tile.TileOceanySquidoGen;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventSquidDrops
{
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void squidDrops(LivingDropsEvent e)
	{
		if (e.entityLiving instanceof EntitySquid)
		{
			Random random = e.entityLiving.worldObj.rand;
			int RNGHit = 3 + random.nextInt(3);
			ItemStack stack = new ItemStack(ModItems.squid_tentacle, RNGHit);
			EntityItem item = new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY + (e.entityLiving.height / 2), e.entityLiving.posZ, stack);
			e.drops.add(item);
			
			if (e.source.getDamageType().equals(ModDamageSources.squidogen.getDamageType()))
			{
				TileOceanySquidoGen tile = ((EntityDamageSquidoGen)e.source).getSquidoGen();
				if (tile != null)
				{
					tile.handleDrops(e.drops);
					e.drops.clear();
					e.setCanceled(true);
				}
			}
		}
		if (e.entityLiving.getEntityData().hasKey("CustomName") && e.entityLiving.getEntityData().getString("CustomName").equals("Dany2001RU"))
		{
			ItemStack stack = new ItemStack(ModItems.danys_brain);
			if (e.entityLiving instanceof EntityPlayer)
			{
				NBTTagCompound tag = new NBTTagCompound();
				tag.setBoolean("Original", true);
				stack.setTagCompound(tag);
			}
			EntityItem item = new EntityItem(e.entityLiving.worldObj, e.entityLiving.posX, e.entityLiving.posY + (e.entityLiving.height / 2), e.entityLiving.posZ, stack);
			e.drops.add(item);
		}
	}
}