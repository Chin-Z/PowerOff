package com.lovewuchin.app.poweroff.utils;
import java.io.*;
import android.util.*;

public class CMDsupport
{
private static final String LOG_TAG="CMDsupport";
private Boolean if_su_support;
public SHELL su;
public SHELL sh;

public CMDsupport()
{
su =new SHELL("su");
sh =new SHELL("sh");
}

	public SHELL suOrSH() {
		return canSU() ? su : sh;
	}

	public boolean canSU() {
		return canSU(false);
	}
	
public class CommandResult {
public final String stdout;
public final String stderr;
public final Integer exit_value;

CommandResult(final Integer exit_value_in) {
			this(exit_value_in, null, null);
		}

CommandResult(final Integer exit_value_in, final String stdout_in,
					  final String stderr_in) {
			exit_value = exit_value_in;
			stdout = stdout_in;
			stderr = stderr_in;
		}

public boolean success() {
			return exit_value != null && exit_value == 0;
		}
	}
public class SHELL
{
private String SHELL="sh";
public SHELL(final String SHELL_IN)
{
SHELL=SHELL_IN;
}
private String getStreamLines(final InputStream is) {
String out = null;
StringBuffer buffer = null;
final DataInputStream dis = new DataInputStream(is);

try {
	if (dis.available() > 0) {
		buffer = new StringBuffer(dis.readLine());
		while (dis.available() > 0) {
			buffer.append("\n").append(dis.readLine());
			}
		}
		dis.close();
	} catch (final Exception ex) {
	Log.e(LOG_TAG, ex.getMessage());
	}
	if (buffer != null) {
		out = buffer.toString();
	}
	return out;
}
public Process run(final String s)
   {
	Process process=null;
	   try{
		   process = Runtime.getRuntime().exec(SHELL);
		   final DataOutputStream toProcess = new DataOutputStream(
			   process.getOutputStream());
		   toProcess.writeBytes("exec " + s + "\n");
		   toProcess.flush();
	   } catch (final Exception e) {
		   Log.e(LOG_TAG, "Exception while trying to run: '" + s + "' "
				 + e.getMessage());
		   process = null;
	   }
	   return process;
	}
		public CommandResult runWaitFor(final String s) {
			final Process process = run(s);
			Integer exit_value = null;
			String stdout = null;
			String stderr = null;
			if (process != null) {
				try {
					exit_value = process.waitFor();

					stdout = getStreamLines(process.getInputStream());
					stderr = getStreamLines(process.getErrorStream());

				} catch (final InterruptedException e) {
					Log.e(LOG_TAG, "runWaitFor " + e.toString());
				} catch (final NullPointerException e) {
					Log.e(LOG_TAG, "runWaitFor " + e.toString());
				}
			}
			return new CommandResult(exit_value, stdout, stderr);
		}
	}

	public boolean canSU(final boolean force_check) {
		if (if_su_support == null || force_check) {
			final CommandResult r = su.runWaitFor("id");
			final StringBuilder out = new StringBuilder();

			if (r.stdout != null) {
				out.append(r.stdout).append(" ; ");
			}
			if (r.stderr != null) {
				out.append(r.stderr);
			}

			Log.d(LOG_TAG, "canSU() su[" + r.exit_value + "]: " + out);
			if_su_support = r.success();
		}
		return if_su_support;
	}

	public static boolean getMount(final String mount) {
		final CMDsupport cmd = new CMDsupport();
		final String mounts[] = getMounts("/system");
		if (mounts != null
			&& mounts.length >= 3) {
			final String device = mounts[0];
			final String path = mounts[1];
			final String point = mounts[2];
			if (cmd.su.runWaitFor("mount -o " + mount + ",remount -t "
								  + point + " " + device + " " + path).success()) {
				return true;
			}
		}
		return ( cmd.su.runWaitFor("busybox mount -o remount," + mount + " /system").success() );
	}


	public static String[] getMounts(final String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("/proc/mounts"), 256);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains(path)) {
					return line.split(" ");
				}
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			Log.d(LOG_TAG, "/proc/mounts does not exist");
		}
		catch (IOException e) {
			Log.d(LOG_TAG, "Error reading /proc/mounts");
		}
		return null;
	}

	public static void restartSystemUI() {
		new CMDsupport().su.run("pkill -TERM -f com.android.systemui");
	}


}
