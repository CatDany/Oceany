package oceany.tile;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class ModTileBase extends TileEntity
{
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		readCustomNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		writeCustomNBT(tag);
	}
	
	public abstract void readCustomNBT(NBTTagCompound tag);
	
	public abstract void writeCustomNBT(NBTTagCompound tag);
	
	public abstract boolean canUpdate();
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		writeCustomNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -999, tag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		super.readFromNBT(packet.func_148857_g());
		readCustomNBT(packet.func_148857_g());
		onDataPacketCustom(net, packet);
	}
	
	public abstract void onDataPacketCustom(NetworkManager net, S35PacketUpdateTileEntity packet);
	
	@Override
	public void markDirty()
	{
		super.markDirty();
		if (!worldObj.isRemote)
		{
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - 16 * 5, 0, zCoord - 16 * 5, xCoord + 16 * 5, 1024, zCoord + 16 * 5); 
			List<EntityPlayer> list = worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
			for (EntityPlayer i : list)
			{
				EntityPlayerMP mp = (EntityPlayerMP)i;
				mp.playerNetServerHandler.sendPacket(getDescriptionPacket());
			}
		}
	}
}
