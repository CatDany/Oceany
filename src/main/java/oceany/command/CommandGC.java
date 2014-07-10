package oceany.command;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import oceany.Oceany;
import oceany.network.PacketHandler;
import oceany.network.packet.PacketGC.GCMessage;
import cpw.mods.fml.relauncher.Side;
import danylibs.libs.PlayerUtils;

public class CommandGC extends ModCommandBase
{
	public CommandGC()
	{
		super("gc", CommandPermissionLevel.OPERATOR);
	}
	
	@Override
	public void addAliasesToList(List aliases)
	{
		aliases.add("garbage-collector");
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length < 1)
		{
			throw new WrongUsageException(getCommandUsage(sender));
		}
		
		if (args[1].equals("client") && args.length == 1)
		{
			if (sender instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP)sender;
				PacketHandler.instance().net.sendTo(new GCMessage(), player);
			}
			else
			{
				sender.addChatMessage(new ChatComponentText("You are not a player! Use /gc client <username> instead."));
			}
		}
		else if (args[1].equals("client") && args.length == 2)
		{
			EntityPlayer foundPlayer = PlayerUtils.getPlayer(args[2]);
			if (foundPlayer != null && foundPlayer instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP)foundPlayer;
				PacketHandler.instance().net.sendTo(new GCMessage(), player);
			}
			else
			{
				sender.addChatMessage(new ChatComponentText("That player not found."));
			}
		}
		else if (args[1].equals("server"))
		{
			Oceany.logger.info("GC started by " + sender.getCommandSenderName());
			gc(sender);
			Oceany.logger.info("GC finished.");
		}
	}
	
	public static void gc(ICommandSender sender)
	{
		Side side = Oceany.proxy.getSide();
		sender.addChatMessage(new ChatComponentText("Starting GC on " + (side.isClient() ? "CLIENT" : "SERVER") + "!"));
		sender.addChatMessage(new ChatComponentText("Memory BEFORE: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576 + " MB / " + Runtime.getRuntime().totalMemory() / 1048576 + " MB"));
		Runtime.getRuntime().gc();
		sender.addChatMessage(new ChatComponentText("Memory AFTER: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576 + " MB / " + Runtime.getRuntime().totalMemory() / 1048576 + " MB"));
		sender.addChatMessage(new ChatComponentText("Deleted: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576 + " MB"));
	}
}