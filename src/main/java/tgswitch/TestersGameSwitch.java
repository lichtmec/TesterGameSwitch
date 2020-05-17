package tgswitch;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import tgswitch.common.CommonProxy;

@Mod(modid = "tgswitch", name = "Tester's Game Switch")
public class TestersGameSwitch
{
	@Mod.Instance("tgswitch")
	public static TestersGameSwitch instance;
	
	@SidedProxy(clientSide="tgswitch.client.ClientProxy", serverSide="tgswitch.common.CommonProxy")
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void init (FMLInitializationEvent event)
	{
		proxy.registerProcessOnInit();
	}
}