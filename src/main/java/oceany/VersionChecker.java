package oceany;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import cpw.mods.fml.common.gameevent.TickEvent.Type;
import danylibs.libs.LocalizationHelper;
import danylibs.libs.PlayerUtils;
import danylibs.libs.TickHandler;

public class VersionChecker extends TickHandler
{
	public boolean shown = false;
	public boolean isUpdated = false;
	public boolean isCorrupted = false;
	public String new_version;
	
	public VersionChecker()
	{
		if (Refs.VERSION.equals("%" + "MOD_VERSION" + "%"))
		{
			Oceany.logger.info("Version Checker disabled because you're running development version.");
			return;
		}
		File fileVersion = null;
		try
		{
			String filepath = "%TMP%" + Refs.MOD_ID.toLowerCase() + "_versionchecker_" + new Random().nextInt(1000);
			fileVersion = new File(filepath);
			if (Refs.VERSION_CHECKER_URL.equals("%" + "VERSION_CHECKER_URL" + "%"))
			{
				Oceany.logger.error("Version Checker URL is corrupted. It is possible that you're using unofficial or modified build of " + Refs.MOD_NAME + ".");
				System.exit(0);
			}
			URL website = new URL(Refs.VERSION_CHECKER_URL);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream(filepath);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		}
		catch (Throwable t)
		{
			Oceany.logger.warn("Failed to check the updates.");
		}
		if (fileVersion != null && fileVersion.exists())
		{
			try
			{
				new_version = Files.readFirstLine(fileVersion, Charsets.UTF_8);
				this.isUpdated = !new_version.equals(Refs.VERSION);
			}
			catch (Throwable t)
			{
				Oceany.logger.warn("Failed to read temporary file. It is possible that Minecraft has not enough rights to access %TMP% folder.");
			}
		}
	}
	
	@Override
	public void tickEnd(ITickData tickData)
	{
		if (!shown && isCorrupted)
		{
			EntityPlayer player = ((TickDataPlayer)tickData).getPlayer();
			String corrupted = LocalizationHelper.get("info.runtime.versionchecker.corrupted", Refs.MOD_NAME, Refs.VERSION);
			PlayerUtils.print(player, corrupted);
			shown = true;
		}
		else if (!shown && isUpdated)
		{
			EntityPlayer player = ((TickDataPlayer)tickData).getPlayer();
			String outdated = LocalizationHelper.get("info.runtime.versionchecker.outdated", Refs.VERSION, new_version);
			String clickfor = LocalizationHelper.get("info.runtime.versionchecker.clickfor");
			String website = LocalizationHelper.get("info.runtime.versionchecker.download_page");
			String website_hover = LocalizationHelper.get("info.runtime.versionchecker.download_page_hover");
			PlayerUtils.print(player, outdated);
			PlayerUtils.print(player, "[\"" + clickfor + "[\",{\"text\":\"" + website + "\",\"color\":\"green\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"" + website_hover + "\",\"color\":\"green\"}},\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + Refs.DOWNLOAD_PAGE_URL + "\"}},\"]");
			shown = true;
		}
	}
	
	@Override
	public void tickStart(ITickData tickData) {}
	
	@Override
	public Type getTickType()
	{
		return Type.PLAYER;
	}
}