package oceany.blocks;

import oceany.world.WorldGenData;
import net.minecraft.block.material.Material;

public class BlockTentaclite extends ModBlockWorldGen
{
	public BlockTentaclite()
	{
		super(Material.rock,
			new WorldGenData(6, 5, 50, 14, "world.enableGeneration.tentaclite_ore"));
		setBlockName("tentaclite_ore");
		setHardness(3.0F);
		setHarvestLevel("pickaxe", 1);
		setResistance(15.0F);
		setStepSound(soundTypeStone);
	}
}