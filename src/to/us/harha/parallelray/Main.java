package to.us.harha.parallelray;

import java.util.Locale;

import to.us.harha.parallelray.gfx.Display;
import to.us.harha.parallelray.gfx.Tracer;
import to.us.harha.parallelray.util.Config;

public class Main
{

	private static Display g_display;
	private static Tracer  g_tracer;
	private static Engine  g_engine;

	public static void main(String[] args)
	{
		Locale.setDefault(Locale.UK);
		Config.init();
		g_display = new Display(Config.g_window_width, Config.g_window_height, Config.g_display_scale, "Parallel Ray tracer");
		g_display.create();
		g_tracer = new Tracer();
		g_engine = new Engine(g_display, g_tracer);
		g_engine.start();
	}

}
