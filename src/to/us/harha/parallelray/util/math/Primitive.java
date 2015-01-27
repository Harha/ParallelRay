package to.us.harha.parallelray.util.math;

public abstract class Primitive
{

	protected Vec3f[] m_vertices;

	protected Primitive(Vec3f[] vertices)
	{
		m_vertices = vertices;
	}

	protected Primitive(Vec3f pos)
	{
		m_vertices = new Vec3f[1];
		m_vertices[0] = pos;
	}

	public abstract Intersection intersect(Ray r);

	public Vec3f[] getVertices()
	{
		return m_vertices;
	}

}
