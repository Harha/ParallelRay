package to.us.harha.parallelray.util.math;

public class Ray
{

	private Vec3f m_pos;
	private Vec3f m_dir;

	public Ray(Vec3f pos, Vec3f dir)
	{
		m_pos = pos;
		m_dir = dir.normalize();
	}

	public Ray()
	{
		m_pos = new Vec3f();
		m_dir = new Vec3f();
	}

	public Vec3f getPos()
	{
		return m_pos;
	}

	public Vec3f getDir()
	{
		return m_dir;
	}

	public void setPos(Vec3f pos)
	{
		m_pos.set(pos);
	}

	public void setDir(Vec3f dir)
	{
		m_dir.set(dir);
	}

}
