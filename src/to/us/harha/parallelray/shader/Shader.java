package to.us.harha.parallelray.shader;

import to.us.harha.parallelray.gfx.Light;
import to.us.harha.parallelray.gfx.Material;
import to.us.harha.parallelray.util.Config;
import to.us.harha.parallelray.util.math.Intersection;
import to.us.harha.parallelray.util.math.Ray;
import to.us.harha.parallelray.util.math.Vec3f;

public class Shader
{

	public static Vec3f COLOR_NULL = new Vec3f(0);

	private Shader()
	{

	}

	public static Vec3f main(Ray r, Intersection x, Light l, Material m)
	{
		Vec3f C, N = x.getNorm(), L, V, H;
		float NdotL, NdotH, L_length, V_length;

		if (l.getLightType() == Config.g_light_types.DIRECTIONAL)
		{
			L = l.getDir().negate().normalize();
			L_length = L.length();
			//V = r.getPos().sub(x.getPos());
			//V_length = V.length();
		} else
		{
			return COLOR_NULL;
		}

		if (m.getMaterialType() == Config.g_material_types.PHONG)
		{
			C = new Vec3f();

			NdotL = N.dot(L);
			if (NdotL < Config.g_epsilon)
				return m.getColorAmbient();

			C.set(m.getColorDiffuse().scale(l.getColor().scale(NdotL)).scale(l.getIntensity())); // Diffuse

			//H = L.add(V).divide(L_length + V_length).normalize();
			//NdotH = H.dot(N);
			//float intensity = (float) Math.max(Math.pow(NdotH, 16), 0.0f);
			//C.set(C.add(m.getColorSpecular().scale(intensity))); // Specular

			C.set(C.add(m.getColorAmbient())); // Ambient
		} else
		{
			return COLOR_NULL;
		}

		return C;
	}

}
