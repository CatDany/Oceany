package oceany.world;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import oceany.Config;
import oceany.blocks.ModBlockWorldGen;
import cpw.mods.fml.common.IWorldGenerator;

public class GenHandler implements IWorldGenerator
{
	public static final HashMap<ModBlockWorldGen, WorldGenerator> oreGens = new HashMap<ModBlockWorldGen, WorldGenerator>(); 
	
	public GenHandler()
	{
		Iterator<ModBlockWorldGen> iore = GenRegistry.instance().ores.keySet().iterator();
		while (iore.hasNext())
		{
			ModBlockWorldGen oreBlock = iore.next();
			WorldGenData oreData = oreBlock.getWorldGenData();
			WorldGenerator oreGen = new WorldGenMinable(oreBlock, oreData.perVein);
			oreGens.put(oreBlock, oreGen);
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		Iterator<ModBlockWorldGen> ioregen = oreGens.keySet().iterator();
		while (ioregen.hasNext())
		{
			ModBlockWorldGen oreBlock = ioregen.next();
			WorldGenData oreData = oreBlock.getWorldGenData();
			WorldGenerator oreGen = oreGens.get(oreBlock);
			if (Config.getBoolean(oreData.configOptionName))
			{
				genOreNormal(oreData, oreGen, oreData.hasCustomRNG ? oreData.customRNG : random, new Chunk(world, chunkX, chunkZ));
			}
		}
	}
	
	private void genOreNormal(WorldGenData data, WorldGenerator ore, Random random, Chunk chunk)
	{
		for (int i = 0; i < data.perVein; i++)
		{
			int x = chunk.xPosition * 16 + random.nextInt(16);
			int y = random.nextInt(data.maxY - data.minY) + data.minY;
			int z = chunk.zPosition * 16 + random.nextInt(16);
			
			ore.generate(chunk.worldObj, random, x, y, z);
		}
	}
}