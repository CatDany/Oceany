package oceany.damage;

import oceany.tile.TileOceanySquidoGen;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntityDamageSquidoGen extends ModDamageSource
{
	private World world;
	private int x;
	private int y;
	private int z;
	
	public EntityDamageSquidoGen(World world, int x, int y, int z)
	{
		super("squidogen");
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public Entity getEntity()
	{
		return null;
	}
	
	@Override
	public Entity getSourceOfDamage()
	{
		return null;
	}
	
	public TileOceanySquidoGen getSquidoGen()
	{
		if (world.getTileEntity(x, y, z) instanceof TileOceanySquidoGen)
		{
			TileOceanySquidoGen tile = (TileOceanySquidoGen)world.getTileEntity(x, y, z);
			if (tile.isAdvanced())
			{
				return tile;
			}
		}
		return null;
	}
}