package oceany.proxy;

import cpw.mods.fml.relauncher.Side;

public class ProxyClient extends ProxyCommon
{
	@Override
	public void initRenders()
	{
		//
	}
	
	@Override
	public Side getSide()
	{
		return Side.CLIENT;
	}
}