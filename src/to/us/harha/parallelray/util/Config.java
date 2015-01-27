package to.us.harha.parallelray.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config
{

	private static final Logger LOG      = new Logger(Config.class.getName());

	public static final String  RES_ROOT = "./res/";

	public static enum g_material_types
	{
		PHONG, COOKTORRANCE
	}

	public static enum g_light_types
	{
		DIRECTIONAL, POINT
	}

	public static int     g_window_width;
	public static int     g_window_height;
	public static int     g_display_scale;
	public static int     g_recursion_max;
	public static int     g_thread_amount;
	public static float   g_epsilon;
	public static boolean g_debug;

	private Config()
	{

	}

	public static void init()
	{
		File f = new File(RES_ROOT + "config.cfg");
		if (f.exists() && !f.isDirectory())
		{
			load();
		} else
		{
			create();
			init();
		}
	}

	public static void create()
	{
		Properties p = new Properties();
		OutputStream o = null;
		try
		{
			o = new FileOutputStream(RES_ROOT + "config.cfg");

			// Set each variable
			p.setProperty("g_window_width", "256");
			p.setProperty("g_window_height", "256");
			p.setProperty("g_display_scale", "2");
			p.setProperty("g_recursion_max", "3");
			p.setProperty("g_thread_amount", "-1");
			p.setProperty("g_epsilon", "1e-3");
			p.setProperty("g_debug", "false");

			// Store the variables
			p.store(o, null);

			// Close the outputstream object
			o.close();

			LOG.printMsg(RES_ROOT + "config.cfg" + " Created succesfully!");
		} catch (IOException e)
		{
			LOG.printErr("Couldn't create the main configuration file, closing program...");
			System.exit(1);
		}
	}

	public static void load()
	{
		Properties p = new Properties();
		InputStream i = null;
		try
		{
			i = new FileInputStream(RES_ROOT + "config.cfg");

			// Load the file
			p.load(i);

			// Get the properties and set the config variables
			g_window_width = Integer.valueOf(p.getProperty("g_window_width"));
			g_window_height = Integer.valueOf(p.getProperty("g_window_height"));
			g_display_scale = Integer.valueOf(p.getProperty("g_display_scale"));
			g_recursion_max = Integer.valueOf(p.getProperty("g_recursion_max"));
			g_thread_amount = Integer.valueOf(p.getProperty("g_thread_amount"));
			g_epsilon = Float.valueOf(p.getProperty("g_epsilon"));
			g_debug = Boolean.valueOf(p.getProperty("g_debug"));

			// Close the inputstream object
			i.close();

			LOG.printMsg(RES_ROOT + "config.cfg" + " loaded succesfully!");
		} catch (IOException e)
		{
			LOG.printErr("Couldn't load the main configuration file, closing program...");
			System.exit(1);
		}
	}

}
