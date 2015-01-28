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
		float NdotL, NdotV, NdotH, VdotH, lambertian, specular, roughness, L_length, A = 1.0f;

		if (l.getLightType() == Config.g_light_types.DIRECTIONAL)
		{
			L = l.getDir().negate().normalize();
			V = r.getDir().negate();
			H = V.add(L).normalize();
		} else if (l.getLightType() == Config.g_light_types.POINT)
		{
			L = l.getPos().sub(x.getPos());
			L_length = L.length();
			L = L.normalize();
			V = r.getDir().negate();
			H = V.add(L).normalize();
			A = l.getConstant() + l.getLinear() * L_length + l.getExponent() * L_length * L_length + Config.g_epsilon;
		} else
		{
			return COLOR_NULL;
		}

		// Calculate the dot products required for shading
		NdotL = Math.min(N.dot(L), 1.0f);
		NdotV = Math.min(N.dot(V), 1.0f);
		NdotH = Math.min(N.dot(H), 1.0f);
		VdotH = Math.min(V.dot(H), 1.0f);

		if (m.getMaterialType() == Config.g_material_types.PHONG)
		{
			C = new Vec3f();

			// Calculate the lambertian term
			lambertian = Math.min(NdotL, 1.0f);

			// Calculate the specular term
			if (m.getShininess() > 0.0f)
				specular = (float) Math.pow(NdotH, m.getShininess()); // Specular
			else
				specular = 0.0f;

			// Add all the terms together to C
			C.set(C.add(l.getColor().scale(m.getColorDiffuse()).scale(lambertian).scale(l.getIntensity())));
			C.set(C.add(m.getColorSpecular().scale(specular).scale(l.getIntensity())));
		} else if (m.getMaterialType() == Config.g_material_types.COOKTORRANCE)
		{
			C = new Vec3f();

			// Return NULL color if the surface normal and light direction are facing opposite directions
			if (NdotL < Config.g_epsilon)
				return COLOR_NULL;

			// Get the surface properties
			float R = m.getRoughness();
			float F = m.getFresnel();
			float K = m.getDensity();

			// Evaluate the geometric term
			float geo_numerator = 2.0f * NdotH;
			float geo_denominator = VdotH;
			float geo_a = (geo_numerator * NdotV) / geo_denominator;
			float geo_b = (geo_numerator * NdotL) / geo_denominator;
			lambertian = Math.min(1.0f, Math.min(geo_a, geo_b));

			// Evaluate the roughness term
			float roughness_a = 1.0f / (4.0f * R * R * (float) Math.pow(NdotH, 4));
			float roughness_b = NdotH * NdotH - 1.0f;
			float roughness_c = R * R * NdotH * NdotH;
			roughness = roughness_a * (float) Math.exp(roughness_b / roughness_c);

			// Evaluate the fresnel term
			specular = (float) Math.pow(1.0f - VdotH, 5);
			specular *= 1.0f - F;
			specular += F;

			// Put all the terms together
			float Rs_numerator = lambertian * roughness * specular;
			float Rs_denominator = Math.max(NdotV * NdotL, Config.g_epsilon);
			float Rs = Rs_numerator / Rs_denominator;

			// Add all the terms to C
			Vec3f final_a = l.getColor().scale(NdotL).scale(l.getIntensity());
			Vec3f final_b = m.getColorDiffuse().scale(K).add(m.getColorSpecular().scale(Rs * (1.0f - K)));
			C.set(final_a.scale(final_b));
		} else
		{
			return COLOR_NULL;
		}

		return C.divide(A);
	}

}
