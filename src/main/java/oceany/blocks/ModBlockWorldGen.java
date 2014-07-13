package oceany.blocks;

import net.minecraft.block.material.Material;
import oceany.world.GenRegistry;
import oceany.world.WorldGenData;

public class ModBlockWorldGen extends ModBlockBase
{
	public ModBlockWorldGen(Material material, WorldGenData data)
	{
		super(material);
		GenRegistry.instance().ores.put(this, data);
	}
	
	public WorldGenData getWorldGenData()
	{
		return GenRegistry.instance().ores.get(this);
	}
}