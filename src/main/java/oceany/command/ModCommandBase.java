package oceany.command;

import java.util.ArrayList;
import java.util.List;

import oceany.libs.PermissionHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public abstract class ModCommandBase extends CommandBase
{
	private final String name;
	private final List aliases = new ArrayList<String>();
	private final CommandPermissionLevel permissionLevel;
	
	public ModCommandBase(String name, CommandPermissionLevel permissionLevel)
	{
		this.name = name;
		this.permissionLevel = permissionLevel;
		addAliasesToList(this.aliases);
	}
	
	public abstract void addAliasesToList(List aliases);
	
	@Override
	public String getCommandName()
	{
		return name;
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "info.command." + name + ".usage";
	}
	
	@Override
	public List getCommandAliases()
	{
		return aliases;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		CommandPermissionLevel level = PermissionHelper.getPermissionLevel(sender);
		return level.value() >= permissionLevel.value();
	}
	
	@Override
	public abstract void processCommand(ICommandSender sender, String[] args);
	
	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		return null;
	}
	
	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}
	
	public static enum CommandPermissionLevel
	{
		USER(0),			// 'help', 'me', 'tell', etc.
		COMMAND_BLOCK(2),	// 'gamemode', 'give', 'summon', 'clear', 'difficulty', etc.
		OPERATOR(3),		// 'ban', 'kick', 'op', etc.
		CONSOLE(4);			// 'stop'
		
		private CommandPermissionLevel(int permissionLevel)
		{
			this.permissionLevel = permissionLevel;
		}
		
		private final int permissionLevel;
		
		public int value()
		{
			return permissionLevel;
		}
	}
}