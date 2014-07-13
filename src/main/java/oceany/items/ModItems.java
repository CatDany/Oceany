package oceany.items;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems
{
	public static Item squid_tentacle;
	public static Item tentacle_bar;
	public static Item bottomless_bucket;
	public static Item oceany_chipset;
	
	public static Item oceanic_cutlass;
	
	public static void initItems()
	{
		//EntityChicken
		squid_tentacle = new ItemSquidTentacle();
		GameRegistry.registerItem(squid_tentacle, "squid_tentacle");
		
		tentacle_bar = new ItemTentacleBar();
		GameRegistry.registerItem(tentacle_bar, "tentacle_bar");
		
		bottomless_bucket = new ItemBottomlessBucket();
		GameRegistry.registerItem(bottomless_bucket, "bottomless_bucket");
		
		oceanic_cutlass = new ItemCutlass();
		GameRegistry.registerItem(oceanic_cutlass, "oceanic_cutlass");
		
		oceany_chipset = new ItemOceanyChipset();
		GameRegistry.registerItem(oceany_chipset, "oceany_chipset");
	}
}