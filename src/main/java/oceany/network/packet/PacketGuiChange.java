package oceany.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import oceany.network.packet.PacketGuiChange.GuiChangeMessage;
import oceany.tile.TileOceanyCore;
import oceany.tile.TileOceanyInfuser;
import oceany.tile.TileOceanySquidoGen;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketGuiChange implements IMessageHandler<GuiChangeMessage, IMessage>
{
	@Override
	public IMessage onMessage(GuiChangeMessage message, MessageContext ctx)
	{
		if (ctx.side.isServer())
		{
			if (message.tile instanceof TileOceanyInfuser)
			{
				if (message.element == 0)
				{
					((TileOceanyInfuser)message.tile).isEjecting = message.value == 1;
				}
			}
		}
		return null;
	}
	
	public static class GuiChangeMessage implements IMessage
	{
		public static final Class[] tileIds = new Class[128];
		static
		{
			tileIds[0] = TileOceanyCore.class;
			tileIds[1] = TileOceanyInfuser.class;
			tileIds[2] = TileOceanySquidoGen.class;
		}
		private TileEntity tile;
		private byte element;
		private byte value;
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			byte id = buf.readByte(); 
			int x = buf.readInt();
			int y = buf.readInt();
			int z = buf.readInt();
			int dim = buf.readInt();
			if (tileIds[id].isInstance(MinecraftServer.getServer().worldServers[dim].getTileEntity(x, y, z)))
			{
				this.tile = (TileEntity)tileIds[id].cast(MinecraftServer.getServer().worldServers[dim].getTileEntity(x, y, z));
			}
			this.element = buf.readByte();
			this.value = buf.readByte();
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			buf.writeByte(getTileEntityID(tile));
			buf.writeInt(tile.xCoord);
			buf.writeInt(tile.yCoord);
			buf.writeInt(tile.zCoord);
			buf.writeInt(tile.getWorldObj().provider.dimensionId);
			buf.writeByte(element);
			buf.writeByte(value);
		}
		
		private byte getTileEntityID(TileEntity tile)
		{
			for (byte i = 0; i < tileIds.length; i++)
			{
				if (tileIds[i].isInstance(tile))
				{
					return i;
				}
			}
			return -1;
		}
		
		public GuiChangeMessage set(TileEntity tile, byte element, byte value)
		{
			this.tile = tile;
			this.element = element;
			this.value = value;
			return this;
		}
	}
}
