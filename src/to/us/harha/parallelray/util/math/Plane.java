package to.us.harha.parallelray.util.math;

import to.us.harha.parallelray.util.Config;

public class Plane extends Primitive
{

	private Vec3f m_normal;

	public Plane(Vec3f pos, Vec3f normal)
	{
		super(pos);
		m_normal = normal;
	}

	@Override
	public Intersection intersect(Ray r)
	{
		Vec3f P;
		float d, t;

		P = m_vertices[0].sub(r.getPos());
		d = m_normal.dot(r.getDir());

		if (d > 0.0f)
			return null;

		t = P.dot(m_normal) / d;

		if (t < Config.g_epsilon)
			return null;

		Intersection x = new Intersection();
		x.setPos(r.getPos().add(r.getDir().scale(t)));
		x.setNorm(m_normal.normalize());
		x.setT(t);

		return x;
	}

}
