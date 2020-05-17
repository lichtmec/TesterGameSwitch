package tgswitch.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.GameRules;
import net.minecraftforge.client.ClientCommandHandler;

import java.util.ArrayList;
import java.util.List;

public class Keyboard
{
	public static final String KEYBIND_CATEGORY = "Tester's Game Switch";
	
	private static List<KeyBindingEx> exKeyBindingList = new ArrayList<>();
	public static KeyBindingEx keySwitchGamemode;
	public static KeyBindingEx keySwitchDaylightAndWeather;
	
	public static void load ()
	{
		keySwitchGamemode = new KeyBindingEx("tgswitch.gamemode_toggle", org.lwjgl.input.Keyboard.KEY_INSERT, KEYBIND_CATEGORY,
				() -> {
					final EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
					String command;
					if (player.capabilities.isCreativeMode)
					{
						command = "/gamemode 0";
					}
					else
					{
						command = "/gamemode 1";
					}
					
					if (ClientCommandHandler.instance.executeCommand(player, command) == 0)
					{
						player.addChatComponentMessage(new ChatComponentText(command));
						player.sendChatMessage(command);
					}
				});
		ClientRegistry.registerKeyBinding(keySwitchGamemode);
		exKeyBindingList.add(keySwitchGamemode);
		
		keySwitchDaylightAndWeather = new KeyBindingEx("tgswitch.dwrule_toggle", org.lwjgl.input.Keyboard.KEY_DELETE, KEYBIND_CATEGORY,
				() -> {
					final EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
					final GameRules rule = Minecraft.getMinecraft().theWorld.getGameRules();
					final boolean hasWeatherRule = rule.hasRule("doWeatherCycle");
					
					String command0;
					String command1;
					boolean locked = false;
					
					if (rule.getGameRuleBooleanValue("doDaylightCycle") &&
							(!hasWeatherRule || rule.getGameRuleBooleanValue("doWeatherCycle")))
					{
						command0 = "/gamerule doDaylightCycle false";
						command1 = "/gamerule doWeatherCycle false";
					}
					else
					{
						command0 = "/gamerule doDaylightCycle true";
						command1 = "/gamerule doWeatherCycle true";
						locked = !player.isSneaking();
					}
					
					if (!locked)
					{
						if (ClientCommandHandler.instance.executeCommand(player, command0) == 0)
						{
							player.addChatComponentMessage(new ChatComponentText(command0));
							player.sendChatMessage(command0);
						}
						if (hasWeatherRule && ClientCommandHandler.instance.executeCommand(player, command1) == 0)
						{
							player.addChatComponentMessage(new ChatComponentText(command1));
							player.sendChatMessage(command1);
						}
					}
					else
					{
						player.addChatComponentMessage(new ChatComponentText(String.format(
								StatCollector.translateToLocal("tgswitch.message.lock_dwrule_toggle"),
								keySwitchDaylightAndWeather.getKeyName())));
					}
				});
		ClientRegistry.registerKeyBinding(keySwitchDaylightAndWeather);
		exKeyBindingList.add(keySwitchDaylightAndWeather);
		
		FMLCommonHandler.instance().bus().register(new Keyboard());
	}
	
	@SubscribeEvent
	public void onKeyInput (InputEvent.KeyInputEvent event)
	{
		exKeyBindingList.forEach(KeyBindingEx::onKeyInputEvent);
	}
}