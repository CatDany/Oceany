package oceany.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;
import oceany.Refs;
import cpw.mods.fml.common.FMLLog;

public class ModPotions
{
	public static Potion speed_of_squid;
	
	public static void initPotionEffects()
	{
		speed_of_squid = new ModPotionBase(40, false, 0xffffff, "speed_of_squid", 0, 0);
	}
	
	public static void patchPotionSystem()
	{
		Potion[] potionTypes = null;
		
		for (Field f : Potion.class.getDeclaredFields())
		{
			f.setAccessible(true);
			try
			{
				if ("potionTypes".equals(f.getName())
				 || "field_76425_a".equals(f.getName()))
				{
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Throwable t)
			{
				FMLLog.severe("Failed to expand potion effects array by %s. Report to the mod author!", Refs.MOD_ID);
				t.printStackTrace();
			}
		}
	}
}