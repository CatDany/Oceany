package oceany.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import oceany.Refs;
import oceany.items.ModItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import danylibs.libs.ItemUtils;
import danylibs.libs.LocalizationHelper;
import danylibs.libs.PlayerUtils;

public class EventNotifications
{
	public static final String NOTE_UNLOCALIZED_BRAINORIGINAL = "info.notification.brain_original";
	public static final String NOTE_UNLOCALIZED_BRAINORIGINAL_BROADCAST = "info.notification.brain_original_broadcast";
	public static final String NOTE_UNLOCALIZED_BRAINFAKE = "info.notification.brain_fake";
	
	@SubscribeEvent
	public void pickup(ItemPickupEvent e)
	{
		ItemStack stack = e.pickedUp.getEntityItem();
		Item item = stack.getItem();
		int meta = stack.getItemDamage();
		int amount = stack.stackSize;
		NBTTagCompound data = stack.getTagCompound() == null ? new NBTTagCompound() : stack.getTagCompound();
		
		if (shouldBeNotified(NOTE_UNLOCALIZED_BRAINORIGINAL, e.player) && compare(ModItems.danys_brain, 0, stack) && data.hasKey("Original") && data.getBoolean("Original"))
		{
			PlayerUtils.print(e.player, LocalizationHelper.get(NOTE_UNLOCALIZED_BRAINORIGINAL));
			PlayerUtils.broadcast(LocalizationHelper.get(NOTE_UNLOCALIZED_BRAINORIGINAL_BROADCAST), e.player);
		}
		if (shouldBeNotified(NOTE_UNLOCALIZED_BRAINFAKE, e.player) && compare(ModItems.danys_brain, 0, stack) && (!data.hasKey("Original") || !data.getBoolean("Original")))
		{
			PlayerUtils.print(e.player, LocalizationHelper.get(NOTE_UNLOCALIZED_BRAINFAKE));
		}
	}
	
	/**
	 * If a given player wasn't notified yet, marks him as notified
	 * @return if a given player wasn't notified yet
	 */
	private boolean shouldBeNotified(String notification, EntityPlayer player)
	{
		NBTTagCompound tag = player.getEntityData();
		if (!tag.hasKey(Refs.MOD_ID + "_Notifications"))
		{
			tag.setTag(Refs.MOD_ID + "_Notifications", new NBTTagCompound());
		}
		NBTTagCompound notifications = tag.getCompoundTag(Refs.MOD_ID + "_Notifications");
		if (notifications.hasKey(notification))
		{
			return false;
		}
		else
		{
			notifications.setBoolean(notification, true);
			return true;
		}
	}
	
	/**
	 * 
	 * @param item
	 * @param meta metadata doesn't count if it's less than 0 (e.g. -1, -322, Integer.MIN_VALUE)
	 * @param stack
	 * @return
	 */
	private boolean compare(Item item, int meta, ItemStack stack)
	{
		if (meta < 1)
		{
			return ItemUtils.compare(stack.getItem(), item);
		}
		else
		{
			return ItemUtils.compare(stack.getItem(), item) && stack.getItemDamage() == meta;
		}
	}
}