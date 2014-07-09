package oceany.libs;

import net.minecraft.util.StatCollector;

public class LocalizationHelper
{
	public static String get(String string)
	{
		// u0026 = &
		return StatCollector.translateToLocal(string);
	}
	
	public static String get(String string, Object... format)
	{
		// u0026 = & 
		return StatCollector.translateToLocalFormatted(string, format);
	}
}