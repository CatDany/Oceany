package oceany;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import oceany.blocks.ModBlocks;
import oceany.items.ModItems;

public class ModAchievementPage extends AchievementPage
{
	protected ModAchievementPage()
	{
		super(Refs.MOD_NAME, new Achievement[]{
			tentacle, tentacle_bar, tentacle_block,
			oceany_core, oceany_upgrade, oceany_infuser,
			oceanic_cutlass, tentaclite, oceany_squidogen,
			oceany_squidogen_adv
			});
	}
	
	public static Achievement tentacle = new Achievement(Refs.MOD_ID + "1", "tentacle", -3, -2, ModItems.squid_tentacle, (Achievement)null).initIndependentStat().registerStat();
	public static Achievement tentacle_bar = new Achievement(Refs.MOD_ID + "2", "tentacle_bar", -3, 0, ModItems.tentacle_bar, tentacle).registerStat();
	public static Achievement tentacle_block = new Achievement(Refs.MOD_ID + "3", "tentacle_block", -3, 2, ModBlocks.tentacle_block, tentacle_bar).registerStat();
	public static Achievement oceany_core = new Achievement(Refs.MOD_ID + "4", "oceany_core", -1, 0, ModBlocks.oceany_core, tentacle_bar).registerStat();
	public static Achievement oceany_upgrade = new Achievement(Refs.MOD_ID + "5", "oceany_upgrade", -1, 2, ModBlocks.oceany_upgrade, oceany_core).registerStat();
	public static Achievement oceany_infuser = new Achievement(Refs.MOD_ID + "6", "oceany_infuser", -1, -2, ModBlocks.oceany_infuser, oceany_core).registerStat();
	public static Achievement oceanic_cutlass = new Achievement(Refs.MOD_ID + "7", "oceanic_cutlass", -1, -4, ModItems.oceanic_cutlass, oceany_infuser).registerStat();
	public static Achievement tentaclite = new Achievement(Refs.MOD_ID + "8", "tentaclite", -3, -4, ModBlocks.tentaclite_ore, (Achievement)null).initIndependentStat().registerStat();
	public static Achievement oceany_squidogen = new Achievement(Refs.MOD_ID + "9", "oceany_squidogen", 1, 0, ModBlocks.oceany_squidogen, oceany_core).registerStat();
	public static Achievement oceany_squidogen_adv = new Achievement(Refs.MOD_ID + "10", "oceany_squidogen_adv", 1, -2, ModBlocks.advanced_oceany_squidogen, oceany_squidogen).registerStat();
}