package oceany.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import oceany.blocks.ModBlocks;
import oceany.libs.ItemUtils;

public abstract class ModTileOceanyCoreDependant extends ModTileBase
{
	public static final int distanceFromCore = 4;
	
	protected int coreX;
	protected int coreY;
	protected int coreZ;
	protected int cooldown_core_check;
	
	/**
	 * Do not override this. Use tick() instead
	 */
	@Override
	public void updateEntity()
	{
		if (cooldown_core_check > 0)
		{
			cooldown_core_check--;
		}
		else
		{
			int[] coreCoords = getCore();
			coreX = coreCoords[0];
			coreY = coreCoords[1];
			coreZ = coreCoords[2];
			markDirty();
			cooldown_core_check = 80;
		}
		
		tick(TETickType.BEFORE_DEPENDANT);
		if (isCoreValid())
		{
			tick(TETickType.CORE_DEPENDANT);
		}
		tick(TETickType.AFTER_DEPENDANT);
	}
	
	public abstract void tick(TETickType type);
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	public ModTileOceanyCoreDependant()
	{
		coreX = (int)(Long.MAX_VALUE * 2);
		coreY = (int)(Long.MAX_VALUE * 2);
		coreZ = (int)(Long.MAX_VALUE * 2);
	}
	
	public int[] getCore()
	{
		if (ItemUtils.compare(worldObj.getBlock(xCoord - distanceFromCore, yCoord, zCoord), ModBlocks.oceany_core))
			return new int[] {xCoord - distanceFromCore, yCoord, zCoord};
		else if (ItemUtils.compare(worldObj.getBlock(xCoord + distanceFromCore, yCoord, zCoord), ModBlocks.oceany_core))
			return new int[] {xCoord + distanceFromCore, yCoord, zCoord};
		else if (ItemUtils.compare(worldObj.getBlock(xCoord, yCoord, zCoord - distanceFromCore), ModBlocks.oceany_core))
			return new int[] {xCoord, yCoord, zCoord - distanceFromCore};
		else if (ItemUtils.compare(worldObj.getBlock(xCoord, yCoord, zCoord + distanceFromCore), ModBlocks.oceany_core))
			return new int[] {xCoord, yCoord, zCoord + distanceFromCore};
		
		return new int[] {(int)(Long.MAX_VALUE * 2), (int)(Long.MAX_VALUE * 2), (int)(Long.MAX_VALUE * 2)};
	}
	
	public int[] getFoundCore()
	{
		return new int[] {coreX, coreY, coreZ};
	}
	
	public boolean isCoreValid()
	{
		return !(coreX == Long.MAX_VALUE * 2 || coreY == Long.MAX_VALUE * 2 || coreZ == Long.MAX_VALUE * 2) && worldObj.getTileEntity(coreX, coreY, coreZ) instanceof TileOceanyCore;
	}
	
	enum TETickType
	{
		/**
		 * Calls if core is valid
		 */
		CORE_DEPENDANT,
		/**
		 * Calls before CORE_DEPENDANT anyway
		 */
		BEFORE_DEPENDANT,
		/**
		 * Calls after CORE_DEPENDANT anyway
		 */
		AFTER_DEPENDANT;
	}
}