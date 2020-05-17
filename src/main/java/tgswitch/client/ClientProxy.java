package tgswitch.client;

import tgswitch.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	public void registerProcessOnInit ()
	{
		Keyboard.load();
	}
}