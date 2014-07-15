package oceany.tile;

import danylibs.libs.ItemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
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
		coreX = -300;
		coreY = -300;
		coreZ = -300;
	}
	
	public void readCoreDataFromNBT(NBTTagCompound tag)
	{
		NBTTagCompound coreData = tag.getCompoundTag("CoreData");
		coreX = coreData.getInteger("x");
		coreY = coreData.getInteger("y");
		coreZ = coreData.getInteger("z");
	}
	
	public void writeCoreDataToNBT(NBTTagCompound tag)
	{
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
		
		return new int[] {-300, -300, -300};
	}
	
	public int[] getFoundCore()
	{
		return new int[] {coreX, coreY, coreZ};
	}
	
	public boolean isCoreValid()
	{
		return !(coreX == -300 || coreY == -300 || coreZ == -300) && worldObj.getTileEntity(coreX, coreY, coreZ) instanceof TileOceanyCore;
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		writeCustomNBT(tag);
		writeCoreDataToNBT(tag);
		
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		super.onDataPacket(net, packet);
		readCoreDataFromNBT(packet.func_148857_g());
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