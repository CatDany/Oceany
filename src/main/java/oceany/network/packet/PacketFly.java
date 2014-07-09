package oceany.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import oceany.libs.PlayerUtils;
import oceany.network.packet.PacketFly.FlyMessage;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFly implements IMessageHandler<FlyMessage, IMessage>
{
	@Override
	public IMessage onMessage(FlyMessage message, MessageContext ctx)
	{
		if (ctx.side.isClient())
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			player.capabilities.isFlying = message.isFlying;
		}
		return null;
	}
	
	public static class FlyMessage implements IMessage
	{
		private boolean isFlying;
		
		@Override
		public void fromBytes(ByteBuf buf)
		{
			byte fly = buf.readByte();
			isFlying = fly == (byte)1;
		}
		
		@Override
		public void toBytes(ByteBuf buf)
		{
			byte fly = isFlying ? (byte)1 : (byte)0;
			buf.writeByte(fly);
		}
		
		public FlyMessage setFlying(boolean fly)
		{
			this.isFlying = fly;
			return this;
		}
	}
}
