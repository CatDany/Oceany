package oceany.gui.client;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import oceany.Refs;
import oceany.gui.server.ContainerOceanyCore;
import oceany.libs.LocalizationHelper;
import oceany.tile.TileOceanyCore;

public class GuiOceanyCore extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation(Refs.RESOURCE_LOCATION, "textures/gui/oceany_core.png");
	public final TileOceanyCore tile;
	
	public GuiOceanyCore(TileOceanyCore tile)
	{
		super(new ContainerOceanyCore(tile));
		this.tile = tile;
		
		xSize = 176;
		ySize = 88;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int currentEnergy = tile.energy;
		int maxEnergy = tile.getMaxEnergyFromTier(tile.tier);
		int tier = tile.tier;
		int perTick = tile.getEnergyPerTick();
		String strTier = LocalizationHelper.get("info.oceany_core.tier", String.valueOf(tier)); 
		String strEnergy = LocalizationHelper.get("info.oceany_core.energy", String.valueOf(currentEnergy), String.valueOf(maxEnergy));
		String strPerTick = LocalizationHelper.get("info.oceany_core.perTick", String.valueOf(perTick));
		drawCenteredString(fontRendererObj, strTier, guiLeft + xSize / 2, guiTop + 28, 0xffffff);
		drawCenteredString(fontRendererObj, strEnergy, guiLeft + xSize / 2, guiTop + 38, 0xffffff);
		drawCenteredString(fontRendererObj, strPerTick, guiLeft + xSize / 2, guiTop + 48, 0xffffff);
	}
}