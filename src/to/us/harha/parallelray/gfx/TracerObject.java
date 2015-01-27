package to.us.harha.parallelray.gfx;

import java.util.ArrayList;

import to.us.harha.parallelray.util.math.Plane;
import to.us.harha.parallelray.util.math.Primitive;
import to.us.harha.parallelray.util.math.Sphere;
import to.us.harha.parallelray.util.math.Vec3f;

public class TracerObject
{

	private ArrayList<Primitive> m_primitives;
	private Material             m_material;

	public TracerObject(ArrayList<Primitive> primitives, Material material)
	{
		m_primitives = primitives;
		m_material = material;
	}

	public TracerObject(Vec3f pos, float radius, Material material)
	{
		m_primitives = new ArrayList<Primitive>();
		m_primitives.add(new Sphere(pos, radius));
		m_material = material;
	}

	public TracerObject(Vec3f pos, Vec3f normal, Material material)
	{
		m_primitives = new ArrayList<Primitive>();
		m_primitives.add(new Plane(pos, normal));
		m_material = material;
	}

	public ArrayList<Primitive> getPrimitives()
	{
		return m_primitives;
	}

	public Material getMaterial()
	{
		return m_material;
	}

}
