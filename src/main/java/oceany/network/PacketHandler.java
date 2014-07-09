package oceany.network;

import oceany.Refs;
import oceany.network.packet.PacketFly;
import oceany.network.packet.PacketFly.FlyMessage;
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
		net.registerMessage(PacketFly.class, PacketFly.FlyMessage.class, 0, Side.CLIENT);
		net.registerMessage(PacketFly.class, PacketFly.FlyMessage.class, 0, Side.SERVER);
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