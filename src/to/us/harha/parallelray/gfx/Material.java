package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.util.Config;
import to.us.harha.parallelray.util.math.Vec3f;

public class Material
{

	// Material type
	private Enum<Config.g_material_types> m_material_type;

	// General shading variables
	private Vec3f                         m_color_ambient;
	private Vec3f                         m_color_diffuse;
	private Vec3f                         m_color_specular;
	private float                         m_reflectivity;

	// Phong shading model variables
	private float                         m_shininess;

	// Cook-torrance shading model variables
	private float                         m_roughness;
	private float                         m_fresnel;
	private float                         m_density;

	// Phong material constructor
	public Material(Vec3f color_ambient, Vec3f color_diffuse, Vec3f color_specular, float shininess, float reflectivity)
	{
		m_material_type = Config.g_material_types.PHONG;
		m_color_ambient = color_ambient;
		m_color_diffuse = color_diffuse;
		m_color_specular = color_specular;
		m_shininess = shininess;
		m_reflectivity = reflectivity;
	}

	// Cook-torrance material constructor
	public Material(Vec3f color_ambient, Vec3f color_diffuse, Vec3f color_specular, float roughness, float fresnel, float density, float reflectivity)
	{
		m_material_type = Config.g_material_types.COOKTORRANCE;
		m_color_ambient = color_ambient;
		m_color_diffuse = color_diffuse;
		m_color_specular = color_specular;
		m_roughness = roughness;
		m_fresnel = fresnel;
		m_density = density;
		m_reflectivity = reflectivity;
	}

	public Enum<Config.g_material_types> getMaterialType()
	{
		return m_material_type;
	}

	public Vec3f getColorAmbient()
	{
		return m_color_ambient;
	}

	public Vec3f getColorDiffuse()
	{
		return m_color_diffuse;
	}

	public Vec3f getColorSpecular()
	{
		return m_color_specular;
	}

	public float getShininess()
	{
		return m_shininess;
	}

	public float getRoughness()
	{
		return m_roughness;
	}

	public float getFresnel()
	{
		return m_fresnel;
	}

	public float getDensity()
	{
		return m_density;
	}

	public float getReflectivity()
	{
		return m_reflectivity;
	}

}
