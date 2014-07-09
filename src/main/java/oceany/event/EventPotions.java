package oceany.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import oceany.network.PacketHandler;
import oceany.network.packet.PacketFly;
import oceany.potion.ModPotions;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class EventPotions
{
	@SubscribeEvent
	public void livingTick(LivingUpdateEvent e)
	{
		if (e.entityLiving.isPotionActive(ModPotions.speed_of_squid))
		{
			PotionEffect potion = e.entityLiving.getActivePotionEffect(ModPotions.speed_of_squid);
			potion_speed_of_squid(e, e.entityLiving, potion, PotionEffectPhase.TICK);
			if (!e.entityLiving.worldObj.isRemote && potion.getDuration() <= 1)
			{
				potion_speed_of_squid(e, e.entityLiving, potion, PotionEffectPhase.PRE_END);
				e.entityLiving.removePotionEffect(ModPotions.speed_of_squid.id);
				potion_speed_of_squid(e, e.entityLiving, potion, PotionEffectPhase.POST_END);
			}
		}
	}
	
	private void potion_speed_of_squid(LivingUpdateEvent e, EntityLivingBase living, PotionEffect potion, PotionEffectPhase phase)
	{
		if (phase == PotionEffectPhase.TICK)
		{
			if (!living.isInWater())
			{
				potion_speed_of_squid(e, e.entityLiving, potion, PotionEffectPhase.PRE_END);
				e.entityLiving.removePotionEffect(ModPotions.speed_of_squid.id);
				potion_speed_of_squid(e, e.entityLiving, potion, PotionEffectPhase.POST_END);
				return;
			}
			if (living instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)living;
				if (!player.capabilities.isCreativeMode)
				{
					player.capabilities.isFlying = true;
				}
			}
		}
		else if (phase == PotionEffectPhase.POST_END)
		{
			if (living instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer)living;
				if (!player.capabilities.isCreativeMode)
				{
					player.capabilities.isFlying = false;
					if (player instanceof EntityPlayerMP)
					{
						PacketHandler.instance().net.sendTo(new PacketFly.FlyMessage().setFlying(false), ((EntityPlayerMP)player));
					}
				}
			}
		}
	}
	
	enum PotionEffectPhase
	{
		/**
		 * Calls every tick on EntityLivingBase if potion effect is applied
		 */
		TICK,
		/**
		 * Calls before removing potion effect from entity
		 */
		PRE_END,
		/**
		 * Calls after removing potion effect from entity
		 */
		POST_END;
	}
}