package oceany.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import oceany.network.packet.PacketInfuserEjectChange.InfuserEjectMessage;
import oceany.tile.TileOceanyInfuser;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketInfuserEjectChange implements IMessageHandler<InfuserEjectMessage, IMessage>
{
	@Override
	public IMessage onMessage(InfuserEjectMessage message, MessageContext ctx)
	{
		if (ctx.side.isServer())
		{
			// FIXME NullPointerException on line 22
			TileOceanyInfuser tile = (TileOceanyInfuser)ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
			tile.isEjecting = message.value;
			tile.markDirty();
		}
		return null;
	}
	
	public static class InfuserEjectMessage implements IMessage
	{
		private int x;
		private int y;
		private int z;
		private boolean value;
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			this.x = buf.readInt();
			this.y = buf.readInt();
			this.z = buf.readInt();
			this.value = buf.readBoolean();
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeInt(x);
			buf.writeInt(y);
			buf.writeInt(z);
			buf.writeBoolean(value);
		}
		
		public InfuserEjectMessage set(TileOceanyInfuser tile, boolean value)
		{
			this.x = tile.xCoord;
			this.y = tile.yCoord;
			this.z = tile.zCoord;
			this.value = value;
			return this;
		}
	}
}
