package oceany.world;

import java.util.HashMap;

import oceany.blocks.ModBlockWorldGen;

public class GenRegistry
{
	private static final GenRegistry instance = new GenRegistry();
	
	public HashMap<ModBlockWorldGen, WorldGenData> ores = new HashMap<ModBlockWorldGen, WorldGenData>();
	
	public static GenRegistry instance()
	{
		return instance;
	}
}