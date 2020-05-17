package tgswitch.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class KeyBindingEx extends KeyBinding
{
	private final Runnable keybindAction;
	private boolean flagPressed;
	
	public KeyBindingEx (String keyDescription, int keyCodeDefault, String keyCategory, Runnable keybindAction)
	{
		super(keyDescription, keyCodeDefault, keyCategory);
		
		if (keybindAction == null)
		{
			throw new NullPointerException();
		}
		
		this.keybindAction = keybindAction;
		this.flagPressed = false;
	}
	
	public void onKeyInputEvent ()
	{
		if (!this.flagPressed)
		{
			if (this.isPressed())
			{
				this.flagPressed = true;
				this.keybindAction.run();
			}
		}
		else
		{
			if (!this.isPressed())
			{
				this.flagPressed = false;
			}
		}
	}
	
	public String getKeyName ()
	{
		return org.lwjgl.input.Keyboard.getKeyName(this.getKeyCode());
	}
}