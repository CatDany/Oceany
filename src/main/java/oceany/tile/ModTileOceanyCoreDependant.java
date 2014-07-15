package oceany.tile;

import danylibs.libs.ItemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import oceany.blocks.ModBlocks;

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
		coreX = (int)(Integer.MAX_VALUE * 2);
		coreY = (int)(Integer.MAX_VALUE * 2);
		coreZ = (int)(Integer.MAX_VALUE * 2);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagCompound coreData = tag.getCompoundTag("CoreData");
		coreX = coreData.getInteger("x");
		coreY = coreData.getInteger("y");
		coreZ = coreData.getInteger("z");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		NBTTagCompound coreData = new NBTTagCompound();
		coreData.setInteger("x", coreX);
		coreData.setInteger("y", coreY);
		coreData.setInteger("z", coreZ);
		tag.setTag("CoreData", coreData);
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
		
		return new int[] {(int)(Integer.MAX_VALUE * 2), (int)(Integer.MAX_VALUE * 2), (int)(Integer.MAX_VALUE * 2)};
	}
	
	public int[] getFoundCore()
	{
		return new int[] {coreX, coreY, coreZ};
	}
	
	public boolean isCoreValid()
	{
		return !(coreX == Integer.MAX_VALUE * 2 || coreY == Integer.MAX_VALUE * 2 || coreZ == Integer.MAX_VALUE * 2) && worldObj.getTileEntity(coreX, coreY, coreZ) instanceof TileOceanyCore;
	}
	
	enum TETickType
	{
		/**
		 * Calls if core is valid
		 */
		CORE_DEPENDANT,
		/**
		 * Always calls before CORE_DEPENDANT
		 */
		BEFORE_DEPENDANT,
		/**
		 * Always calls after CORE_DEPENDANT anyway
		 */
		AFTER_DEPENDANT;
	}
}