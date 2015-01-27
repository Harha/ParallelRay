package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.util.Config;
import to.us.harha.parallelray.util.math.Vec3f;

public class Light
{

	private Enum<Config.g_light_types> m_light_type;
	private Vec3f                      m_pos;
	private Vec3f                      m_dir;
	private Vec3f                      m_color;
	private float                      m_intensity;
	private float                      m_constant;
	private float                      m_linear;
	private float                      m_exponent;

	public Light(Vec3f dir, Vec3f color, float intensity)
	{
		m_light_type = Config.g_light_types.DIRECTIONAL;
		m_dir = dir.normalize();
		m_color = color;
		m_intensity = intensity;
	}

	public Enum<Config.g_light_types> getLightType()
	{
		return m_light_type;
	}

	public Vec3f getPos()
	{
		return m_pos;
	}

	public Vec3f getDir()
	{
		return m_dir;
	}

	public Vec3f getColor()
	{
		return m_color;
	}

	public float getIntensity()
	{
		return m_intensity;
	}

	public float getConstant()
	{
		return m_constant;
	}

	public float getLinear()
	{
		return m_linear;
	}

	public float getExponent()
	{
		return m_exponent;
	}

}
