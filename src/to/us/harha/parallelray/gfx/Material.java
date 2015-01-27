package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.util.Config;
import to.us.harha.parallelray.util.math.Vec3f;

public class Material
{

	private Enum<Config.g_material_types> m_material_type;
	private Vec3f                         m_color_ambient;
	private Vec3f                         m_color_diffuse;
	private Vec3f                         m_color_specular;
	private float						  m_reflectivity;

	public Material(Vec3f color_ambient, Vec3f color_diffuse, Vec3f color_specular, float reflectivity)
	{
		m_material_type = Config.g_material_types.PHONG;
		m_color_ambient = color_ambient;
		m_color_diffuse = color_diffuse;
		m_color_specular = color_specular;
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
	
	public float getReflectivity()
	{
		return m_reflectivity;
	}

}
