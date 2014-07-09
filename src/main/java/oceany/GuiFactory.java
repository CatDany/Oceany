package oceany;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import oceany.Config.ConfigGui;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionCategoryElement;
import cpw.mods.fml.client.IModGuiFactory.RuntimeOptionGuiHandler;

public class GuiFactory implements IModGuiFactory
{
	@Override
	public void initialize(Minecraft mc) {}
	
	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		ConfigGui.preInitConfigGUI();
		return ConfigGui.class;
	}
	
	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}
	
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}
}