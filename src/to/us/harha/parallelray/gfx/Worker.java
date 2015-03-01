package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.shader.Shader;
import to.us.harha.parallelray.util.Config;
import to.us.harha.parallelray.util.math.Intersection;
import to.us.harha.parallelray.util.math.MathUtils;
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
			int width = Config.g_window_width;
			int height = Config.g_window_height;
			Ray ray_primary = new Ray();
			for (int y = m_yoffset; y < m_yoffset + m_height; y++)
			{
				for (int x = m_xoffset; x < m_xoffset + m_width; x++)
				{
					ray_primary = Ray.calcCameraRay(m_tracer.getCamera(), width, height, m_display.getAR(), x, y);
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
		// Break out from the method if max recursion depth is hit
		if (n > Config.g_recursion_max)
			return Shader.COLOR_NULL;

		// Initialize the required intersection data
		Intersection xInit = null;
		Intersection xFinal = null;
		TracerObject xObject = null;
		float tInit = Float.MAX_VALUE;

		// Find the nearest intersection point
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

		// Return a blank color if the ray didn't hit anything
		if (xFinal == null)
			return Shader.COLOR_NULL;

		// Initialize the main color which will be calculated and returned
		Vec3f cFinal = new Vec3f();

		// Shade the surface point against all lights in the scene
		for (Light l : s.getLights())
		{
			cFinal.set(cFinal.add(Shader.main(r, xFinal, l, xObject.getMaterial())));

			Ray ray_shadow = null;

			if (xObject.getMaterial().getReflectivity() != 1.0f)
			{
				Vec3f L_Vector = l.getPos().sub(xFinal.getPos());
				float L_length = L_Vector.length();
				if (l.getLightType() == Config.g_light_types.DIRECTIONAL)
				{
					ray_shadow = new Ray(xFinal.getPos(), l.getDir().negate());
					L_length = Float.MAX_VALUE;
				} else if (l.getLightType() == Config.g_light_types.POINT)
				{
					ray_shadow = new Ray(xFinal.getPos(), L_Vector);
				}

				if (ray_shadow != null)
					cFinal.set(cFinal.scale(Math.min(traceShadow(ray_shadow, s, xObject, L_length) + xObject.getMaterial().getReflectivity(), 1.0f)));
			}

		}

		if (xObject.getMaterial().getReflectivity() > 0.0f)
		{
			Ray ray_reflected = new Ray(xFinal.getPos(), r.getDir().reflect(xFinal.getNorm()));
			cFinal.set(cFinal.add(traceColor(ray_reflected, s, n + 1).scale(xObject.getMaterial().getReflectivity())));
		}

		if (xObject.getMaterial().getRefractivity() > 0.0f)
		{
			Ray ray_refracted;
			Vec3f N = xFinal.getNorm();
			float NdotI = r.getDir().dot(N), ior, n1, n2, cos_t;

			if (NdotI > 0.0f)
			{
				n1 = r.getIOR();
				n2 = xObject.getMaterial().getIOR();
				N = N.negate();
			} else
			{
				n1 = xObject.getMaterial().getIOR();
				n2 = r.getIOR();
				NdotI = -NdotI;
			}

			ior = n2 / n1;
			cos_t = ior * ior * (1.0f - NdotI * NdotI);

			ray_refracted = new Ray(xFinal.getPos(), r.getDir().refract(N, ior, NdotI, cos_t), 1.0f);
			cFinal.set(cFinal.add(traceColor(ray_refracted, s, n + 1).scale(xObject.getMaterial().getRefractivity())));
		}

		cFinal.set(cFinal.add(xObject.getMaterial().getColorAmbient()));

		return MathUtils.clamp(cFinal, 0.0f, 1.0f);
	}

	public static float traceShadow(Ray r, Scene s, TracerObject thisobj, float L_length)
	{
		Intersection xInit = null;
		Intersection xFinal = null;
		TracerObject xObject = null;
		float tInit = Float.MAX_VALUE;
		float weight = 1.0f;

		for (TracerObject o : s.getObjects())
		{
			if (o.equals(thisobj))
			{
				continue;
			}

			for (Primitive p : o.getPrimitives())
			{
				xInit = p.intersect(r);
				if (xInit != null && xInit.getT() < tInit && xInit.getT() < L_length)
				{
					xFinal = xInit;
					tInit = xFinal.getT();
					xObject = o;
				}
			}
		}

		if (xFinal == null)
			return 1.0f;

		if (xObject.getMaterial().getReflectivity() > 0.0f)
			weight -= xObject.getMaterial().getReflectivity();

		if (xObject.getMaterial().getRefractivity() > 0.0f)
			weight *= xObject.getMaterial().getRefractivity();

		return weight;
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
