package to.us.harha.parallelray.util.math;

import to.us.harha.parallelray.gfx.Camera;

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

	public static Ray calcCameraRay(Camera c, int w, int h, float ar, int x, int y)
	{
		float x_norm = (x - w * 0.5f) / w * ar;
		float y_norm = (h * 0.5f - y) / h;
		
		Vec3f forward = c.getForward();
		Vec3f up = c.getUp();
		Vec3f right = c.getRight();

		Vec3f image_point = right.scale(x_norm).add(up.scale(y_norm)).add(c.getPos().add(forward));
		Vec3f ray_direction = image_point.sub(c.getPos());

		return new Ray(c.getPos(), ray_direction);
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
