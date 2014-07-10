package oceany.network;

import oceany.Refs;
import oceany.network.packet.PacketFly;
import oceany.network.packet.PacketGC;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import danylibs.libs.NetworkHelper;

public class PacketHandler
{
	private static PacketHandler instance = new PacketHandler();
	public SimpleNetworkWrapper net;
	
	private PacketHandler() {}
	
	public void init()
	{
		NetworkHelper helper = NetworkHelper.addNetHandler(Refs.MOD_ID.toUpperCase());
		net = helper.net;
		helper.registerMessage(0, PacketFly.class, PacketFly.FlyMessage.class);
		helper.registerMessage(1, PacketGC.class, PacketGC.GCMessage.class);
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