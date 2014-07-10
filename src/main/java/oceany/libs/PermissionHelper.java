package oceany.libs;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import oceany.command.ModCommandBase.CommandPermissionLevel;

public class PermissionHelper
{
	public static CommandPermissionLevel getPermissionLevel(ICommandSender sender)
	{
		if (sender instanceof MinecraftServer)
		{
			return CommandPermissionLevel.CONSOLE;
		}
		else if (sender instanceof EntityPlayer)
		{
			boolean isOP = MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer)sender).getGameProfile());
			int level = isOP ? MinecraftServer.getServer().getOpPermissionLevel() : 0;
			return getEnumFromPermissionLevel(level);
		}
		else if (sender instanceof CommandBlockLogic)
		{
			return CommandPermissionLevel.COMMAND_BLOCK;
		}
		else
		{
			return CommandPermissionLevel.USER;
		}
	}
	
	public static CommandPermissionLevel getEnumFromPermissionLevel(int permissionLevel)
	{
		switch (permissionLevel)
		{
		case 0:
			return CommandPermissionLevel.USER;
		case 1:
			return CommandPermissionLevel.USER;
		case 2:
			return CommandPermissionLevel.COMMAND_BLOCK;
		case 3:
			return CommandPermissionLevel.OPERATOR;
		case 4:
			return CommandPermissionLevel.CONSOLE;
		}
		return CommandPermissionLevel.USER;
	}
}