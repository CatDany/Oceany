package oceany.gui.client;

import danylibs.libs.LocalizationHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import oceany.Refs;
import oceany.gui.server.ContainerOceanyInfuser;
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
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		String strProcess = (int)(tile.process) + "%";
		String strCoreStatus = tile.isCoreValid() ? LocalizationHelper.get("info.oceany_infuser.connected_to_core") : LocalizationHelper.get("info.oceany_infuser.core_not_found");
		drawCenteredString(mc.fontRenderer, strProcess, guiLeft + xSize / 2, guiTop + 45, 0xffffff);

		drawCenteredString(mc.fontRenderer, strCoreStatus, guiLeft + xSize / 2, guiTop + 25, tile.isCoreValid() ? 0xffffff : 0xff3333);
		if (tile.inventory[0] != null)
		{
			String strCost = LocalizationHelper.get("info.oceany_infuser.cost", (int)(tile.getEnergyCostFromInput(tile.inventory[0]) - (tile.process / 100 * tile.getEnergyCostFromInput(tile.inventory[0]))) < 0 ? 0 : (int)(tile.getEnergyCostFromInput(tile.inventory[0]) - (tile.process / 100 * tile.getEnergyCostFromInput(tile.inventory[0]))));
			drawCenteredString(mc.fontRenderer, strCost, guiLeft + xSize / 2, guiTop + 65, 0xffffff);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
	
	@Override
	protected void actionPerformed(GuiButton button) {}
}