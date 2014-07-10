package oceany;

public class Refs
{
	public static final String MOD_ID		= "Oceany";
	public static final String MOD_NAME		= "Oceany Mod";
	public static final String VERSION		= "_%MOD_VERSION%";
	public static final String DEPENDENCIES = ""
			+ "required-after:Forge@[10.13.0.1177,)" + ";"
		 // + "required-after:DanyFriends" + ";"
			+ "required-after:Baubles@[1.0.0.16,)";
	public static final String RESOURCE_LOCATION = MOD_ID.toLowerCase();
	public static final String GUI_FACTORY_PATH = "oceany.GuiFactory";
	public static final String FINGERPRINT = "%FINGERPRINT%";
	//public static final String VERSION_CHECKER_URL = "%VERSION_CHECKER_URL%";
	public static final String VERSION_CHECKER_URL = "http://mods.hoppix.ru/Oceany/version.txt";
	public static final String DOWNLOAD_PAGE_URL = "http://mods.hoppix.ru/Oceany/download.html";
}