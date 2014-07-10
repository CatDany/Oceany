package oceany.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import oceany.command.CommandGC;
import oceany.network.packet.PacketFly.FlyMessage;
import oceany.network.packet.PacketGC.GCMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketGC implements IMessageHandler<GCMessage, IMessage>
{
	@Override
	public IMessage onMessage(GCMessage message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			CommandGC.gc(Minecraft.getMinecraft().thePlayer);
		}
		return null;
	}
	
	public static class GCMessage implements IMessage
	{
		@Override
		public void fromBytes(ByteBuf buf) {}
		
		@Override
		public void toBytes(ByteBuf buf) {}
	}
}
