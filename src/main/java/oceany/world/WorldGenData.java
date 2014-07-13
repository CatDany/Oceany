package oceany.world;

import java.util.Random;

public class WorldGenData
{
	public final String configOptionName;
	public final int perVein;
	public final int minY;
	public final int maxY;
	public final int perChunk;
	public final boolean hasCustomRNG;
	public final Random customRNG;
	
	public WorldGenData(int perVein, int minY, int maxY, int perChunk, String configOptionName)
	{
		this(perVein, minY, maxY, perChunk, configOptionName, null);
	}
	
	public WorldGenData(int perVein, int minY, int maxY, int perChunk, String configOptionName, Random customRNG)
	{
		this.perVein = perVein;
		this.minY = minY;
		this.maxY = maxY;
		this.perChunk = perChunk;
		this.configOptionName = configOptionName;
		this.hasCustomRNG = customRNG != null;
		this.customRNG = customRNG;
	}
}
