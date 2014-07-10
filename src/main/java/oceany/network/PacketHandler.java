package oceany.network;

import oceany.Refs;
import oceany.network.packet.PacketFly;
import oceany.network.packet.PacketGC;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	private static PacketHandler instance = new PacketHandler();
	public SimpleNetworkWrapper net;
	
	public void init()
	{
		net = NetworkRegistry.INSTANCE.newSimpleChannel(Refs.MOD_ID.toUpperCase());
		this.registerMessage(0, PacketFly.class, PacketFly.FlyMessage.class);
		this.registerMessage(1, PacketGC.class, PacketGC.GCMessage.class);
	}
	
	public void registerMessage(int index, Class packet, Class message)
	{
		registerSidedMessage(index, packet, message, Side.CLIENT);
		registerSidedMessage(index, packet, message, Side.SERVER);
	}
	
	public void registerSidedMessage(int index, Class packet, Class message, Side side)
	{
		net.registerMessage(packet, message, index, side);
	}
	
	public static PacketHandler instance()
	{
		return instance;
	}
	
	public static PacketHandler newInstance()
	{
		instance = new PacketHandler();
		return instance;
	}
}