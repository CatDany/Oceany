package oceany.potion;

import net.minecraft.potion.Potion;

public class ModPotionBase extends Potion
{
	public ModPotionBase(int id, boolean negative, int particleColor, String name, int iconX, int iconY)
	{
		super(id, negative, particleColor);
		setIconIndex(iconX, iconY);
		setPotionName("potion." + name);
	}
}