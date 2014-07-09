package oceany.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import oceany.Refs;
import oceany.gui.server.ContainerOceanyInfuser;
import oceany.libs.LocalizationHelper;
import oceany.libs.Paragraph;
import oceany.tile.TileOceanyInfuser;

public class GuiOceanyInfuser extends GuiContainer
{
	private static final ResourceLocation texture = new ResourceLocation(Refs.RESOURCE_LOCATION, "textures/gui/oceany_infuser.png");
	public final TileOceanyInfuser tile;
	
	public GuiOceanyInfuser(IInventory player, TileOceanyInfuser tile)
	{
		super(new ContainerOceanyInfuser(player, tile));
		this.tile = tile;
		
		xSize = 176;
		ySize = 222;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		String strCoreStatus = tile.isCoreValid() ? LocalizationHelper.get("info.oceany_infuser.connected_to_core") : LocalizationHelper.get("info.oceany_infuser.core_not_found");
		String strProcess = (int)(tile.process) + "%";
		drawCenteredString(mc.fontRenderer, strProcess, guiLeft + xSize / 2, guiTop + 45, 0xffffff);
		drawCenteredString(mc.fontRenderer, strCoreStatus, guiLeft + xSize / 2, guiTop + 25, tile.isCoreValid() ? 0xffffff : 0xff3333);
		if (tile.inventory[0] != null)
		{
			String strCost = LocalizationHelper.get("info.oceany_infuser.cost", (int)(tile.getEnergyCostFromInput(tile.inventory[0]) - (tile.process / 100 * tile.getEnergyCostFromInput(tile.inventory[0]))));
			drawCenteredString(mc.fontRenderer, strCost, guiLeft + xSize / 2, guiTop + 65, 0xffffff);
		}
	}
}