package oceany.event;

import oceany.ModAchievementPage;
import oceany.Refs;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import danylibs.libs.ItemUtils;


public class EventAchievements
{
	@SubscribeEvent
	public void craft(ItemCraftedEvent e)
	{
		if (e.crafting == null || e.crafting.getItem() == null)
			return;
		String fullResult = ItemUtils.getFullUniqueItemName(e.crafting.getItem());
		boolean oceany = ItemUtils.getUniqueModName(e.crafting.getItem()).equals(Refs.MOD_ID);
		String result = ItemUtils.getUniqueItemName(e.crafting.getItem());
		if ("tentacle_bar".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.tentacle_bar, 1);
		else if ("tentacle_block".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.tentacle_block, 1);
		else if ("oceany_core".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.oceany_core, 1);
		else if ("oceany_upgrade".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.oceany_upgrade, 1);
		else if ("oceany_infuser".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.oceany_infuser, 1);
		else if ("oceanic_cutlass".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.oceanic_cutlass, 1);
	}
	
	@SubscribeEvent
	public void pickup(ItemPickupEvent e)
	{
		if (e.pickedUp.getEntityItem() == null || e.pickedUp.getEntityItem().getItem() == null)
			return;
		String fullResult = ItemUtils.getFullUniqueItemName(e.pickedUp.getEntityItem().getItem());
		boolean oceany = ItemUtils.getUniqueModName(e.pickedUp.getEntityItem().getItem()).equals(Refs.MOD_ID);
		String result = ItemUtils.getUniqueItemName(e.pickedUp.getEntityItem().getItem());
		
		if ("squid_tentacle".equals(result) && oceany)
			e.player.addStat(ModAchievementPage.tentacle, 1);
	}
}