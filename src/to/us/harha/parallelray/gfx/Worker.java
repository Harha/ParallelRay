package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.shader.Shader;
import to.us.harha.parallelray.util.Config;
import to.us.harha.parallelray.util.math.Intersection;
import to.us.harha.parallelray.util.math.Primitive;
import to.us.harha.parallelray.util.math.Ray;
import to.us.harha.parallelray.util.math.Vec3f;

public class Worker implements Runnable
{

	private int     m_width;
	private int     m_height;
	private int     m_xoffset;
	private int     m_yoffset;
	private int     m_id;
	private boolean m_finished;

	private Tracer  m_tracer;
	private Display m_display;

	public Worker(int width, int height, int xoffset, int yoffset, int id, Tracer tracer)
	{
		m_width = width;
		m_height = height;
		m_xoffset = xoffset;
		m_yoffset = yoffset;
		m_id = id;
		m_finished = true;
		m_tracer = tracer;
	}

	@Override
	public void run()
	{
		m_finished = false;
		if (m_tracer != null && m_display != null)
		{
			float width = Config.g_window_width;
			float height = Config.g_window_height;
			Ray ray_primary = new Ray(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.0f, 0.0f, -1.0f));
			for (int y = m_yoffset; y < m_yoffset + m_height; y++)
			{
				for (int x = m_xoffset; x < m_xoffset + m_width; x++)
				{
					float x_norm = (x - width * 0.5f) / width * m_display.getAR();
					float y_norm = (height * 0.5f - y) / height;
					ray_primary.setDir(new Vec3f(x_norm, y_norm, -1.0f).normalize());
					m_display.drawPixelVec3f(x, y, traceColor(ray_primary, m_tracer.getScene(), 0));
					if (Config.g_debug && x == m_xoffset + 1 || Config.g_debug && y == m_yoffset)
						m_display.drawPixelInt(x, y, 0xFFFF00FF);
				}
			}
		}
		m_finished = true;
	}

	public static Vec3f traceColor(Ray r, Scene s, int n)
	{
		if (n > Config.g_recursion_max)
			return Shader.COLOR_NULL;

		Intersection xInit = null;
		Intersection xFinal = null;
		TracerObject xObject = null;
		float tInit = Float.MAX_VALUE;

		for (TracerObject o : s.getObjects())
		{
			for (Primitive p : o.getPrimitives())
			{
				xInit = p.intersect(r);
				if (xInit != null && xInit.getT() < tInit)
				{
					xFinal = xInit;
					tInit = xFinal.getT();
					xObject = o;
				}
			}
		}

		if (xFinal == null)
			return Shader.COLOR_NULL;

		Vec3f cFinal = new Vec3f();

		for (Light l : s.getLights())
		{
			cFinal.set(cFinal.add(Shader.main(r, xFinal, l, xObject.getMaterial())));

			Ray ray_shadow = null;

			if (xObject.getMaterial().getReflectivity() == 0.0f)
			{
				if (l.getLightType() == Config.g_light_types.DIRECTIONAL)
				{
					ray_shadow = new Ray(xFinal.getPos(), l.getDir().negate());
				}

				if (ray_shadow != null)
					cFinal.set(cFinal.scale(traceShadow(ray_shadow, s, xObject)));
			}

		}

		if (xObject.getMaterial().getReflectivity() > 0.0f)
		{
			Ray ray_reflected = new Ray(xFinal.getPos(), r.getDir().reflect(xFinal.getNorm()));
			cFinal.set(cFinal.add(traceColor(ray_reflected, s, n + 1)));
		}

		cFinal.set(cFinal.add(xObject.getMaterial().getColorAmbient()));

		return cFinal;
	}

	public static float traceShadow(Ray r, Scene s, TracerObject thisobj)
	{
		Intersection xInit = null;
		Intersection xFinal = null;
		TracerObject xObject = null;
		float tInit = Float.MAX_VALUE;

		for (TracerObject o : s.getObjects())
		{
			if (o.equals(thisobj))
			{
				continue;
			}

			for (Primitive p : o.getPrimitives())
			{
				xInit = p.intersect(r);
				if (xInit != null && xInit.getT() < tInit)
				{
					xFinal = xInit;
					tInit = xFinal.getT();
					xObject = o;
				}
			}
		}

		if (xFinal == null)
			return 1.0f;

		return 0.0f;
	}

	public int getWidth()
	{
		return m_width;
	}

	public int getHeight()
	{
		return m_height;
	}

	public int getXOffset()
	{
		return m_xoffset;
	}

	public int getYOffset()
	{
		return m_yoffset;
	}

	public int getId()
	{
		return m_id;
	}

	public boolean isFinished()
	{
		return m_finished;
	}

	public void setDisplay(Display display)
	{
		m_display = display;
	}

}
