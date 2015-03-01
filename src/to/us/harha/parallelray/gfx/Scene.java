package to.us.harha.parallelray.gfx;

import java.util.ArrayList;

import to.us.harha.parallelray.util.math.Vec3f;

public class Scene
{

	private ArrayList<TracerObject> m_objects;
	private ArrayList<Light>        m_lights;

	public Scene()
	{
		m_objects = new ArrayList<TracerObject>();
		m_lights = new ArrayList<Light>();

		Material m_diffuse_white = new Material(new Vec3f(0.01f), new Vec3f(1.0f), new Vec3f(1.0f), 0.375f, 0.5f, 0.9f, 0.0f, 0, 0);
		Material m_diffuse_red = new Material(new Vec3f(0.01f), new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.375f, 0.5f, 0.9f, 0.0f, 0, 0);
		Material m_diffuse_green = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 0.375f, 0.5f, 0.9f, 0.0f, 0, 0);
		Material m_diffuse_blue = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 0.375f, 0.5f, 0.9f, 0.0f, 0, 0);
		Material m_reflective_red = new Material(new Vec3f(0.01f), new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.10f, 1.0f, 0.5f, 0.25f, 0, 0);
		Material m_reflective_green = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 0.05f, 1.0f, 0.5f, 0.50f, 0, 0);
		Material m_reflective_blue = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 0.20f, 1.0f, 0.75f, 0.375f, 0, 0);
		Material m_reflective_metal = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.10f, 1.0f, 0.5f, 1.0f, 0, 0);
		Material m_refractive_glass = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.10f, 1.0f, 0.5f, 0.0f, 1.0f, 1.52f);
		
		m_objects.add(new TracerObject(new Vec3f(0.0f, 0.0f, 0.0f), new Vec3f(0.0f, 1.0f, 0.0f), m_diffuse_white));
		m_objects.add(new TracerObject(new Vec3f(0.0f, 5.0f, 0.0f), new Vec3f(0.0f, -1.0f, 0.0f), m_diffuse_white));
		m_objects.add(new TracerObject(new Vec3f(0.0f, 0.0f, -10.0f), new Vec3f(0.0f, 0.0f, 1.0f), m_diffuse_white));
		m_objects.add(new TracerObject(new Vec3f(4.0f, 0.0f, 0.0f), new Vec3f(-1.0f, 0.0f, 0.0f), m_diffuse_red));
		m_objects.add(new TracerObject(new Vec3f(-4.0f, 0.0f, 0.0f), new Vec3f(1.0f, 0.0f, 0.0f), m_diffuse_blue));
		m_objects.add(new TracerObject(new Vec3f(1.0f, 0.75f, -4.0f), 0.75f, m_reflective_metal));
		m_objects.add(new TracerObject(new Vec3f(-1.0f, 0.75f, -5.0f), 0.75f, m_refractive_glass));
		
		m_lights.add(new Light(new Vec3f(0.0f, 4.0f, -5.0f), new Vec3f(1.0f), 1.0f, 0.0f, 0.0f, 0.1f));
	}

	public void update(float dt)
	{

	}

	public ArrayList<TracerObject> getObjects()
	{
		return m_objects;
	}

	public ArrayList<Light> getLights()
	{
		return m_lights;
	}

}
