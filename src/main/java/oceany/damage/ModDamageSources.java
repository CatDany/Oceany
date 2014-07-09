package oceany.damage;

import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ModDamageSources
{
	public static DamageSource squidogen;
	
	public static void initDamageSources()
	{
		squidogen = new ModDamageSource("squidogen").setDamageIsAbsolute();
	}
	
	public static DamageSource getDamageSquidoGen(World world, int x, int y, int z)
	{
		return new EntityDamageSquidoGen(world, x, y, z);
	}
}