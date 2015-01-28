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

		Material m_mirror_black = new Material(new Vec3f(0.01f), new Vec3f(0.0f), new Vec3f(1.0f), 0.0f, 1.0f);
		Material m_mirror_orange = new Material(new Vec3f(0.1f, 0.05f, 0.01f), new Vec3f(1.0f, 0.5f, 0.1f), new Vec3f(1.0f), 128.0f, 1.0f);
		Material m_diffuse_white = new Material(new Vec3f(0.01f), new Vec3f(1.0f, 1.0f, 1.0f), new Vec3f(1.0f), 0.0f, 0.0f);
		Material m_diffuse_red = new Material(new Vec3f(0.01f), new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 128.0f, 0.0f);
		Material m_diffuse_green = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 64.0f, 0.0f);
		Material m_diffuse_blue = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 64.0f, 0.0f);
		Material m_ct_purple = new Material(new Vec3f(0.05f, 0.01f, 0.1f), new Vec3f(0.5f, 0.1f, 1.0f), new Vec3f(1.0f), 0.05f, 1.0f, 0.50f, 0.25f);
		Material m_ct_cyan = new Material(new Vec3f(0.01f, 0.05f, 0.1f), new Vec3f(0.1f, 0.5f, 1.0f), new Vec3f(1.0f), 0.1f, 0.375f, 0.90f, 0.5f);
		Material m_ct_lime = new Material(new Vec3f(0.05f, 0.1f, 0.01f), new Vec3f(0.25f, 1.0f, 0.1f), new Vec3f(1.0f), 0.16f, 1.0f, 0.50f, 0.25f);

		m_objects.add(new TracerObject(new Vec3f(0.0f, -1.0f, 0.0f), new Vec3f(0.0f, 1.0f, 0.0f), m_diffuse_white)); // Cyan cook-torrance plane

		m_objects.add(new TracerObject(new Vec3f(0.0f, 0.0f, -5.0f), 1.0f, m_diffuse_white)); // White diffuse sphere
		m_objects.add(new TracerObject(new Vec3f(-0.75f, 0.75f, -4.0f), 0.25f, m_diffuse_red)); // Red diffuse sphere
		m_objects.add(new TracerObject(new Vec3f(2.0f, 1.0f, -7.5f), 1.25f, m_mirror_orange)); // Orange mirror sphere
		m_objects.add(new TracerObject(new Vec3f(-0.375f, -0.5f, -2.5f), 0.5f, m_ct_purple)); // Purple cook-torrance sphere
		m_objects.add(new TracerObject(new Vec3f(1.0f, -0.5f, -1.5f), 0.5f, m_ct_lime)); // Lime cook-torrance sphere
		m_lights.add(new Light(new Vec3f(0.33f, -0.33f, 0.33f), new Vec3f(1.0f, 1.0f, 1.0f), 0.05f)); // White directional light
		m_lights.add(new Light(new Vec3f(-3.75f, 1.0f, -5.0f), new Vec3f(1.0f), 2.5f, 0.0f, 0.0f, 0.25f));
		m_lights.add(new Light(new Vec3f(3.75f, 1.0f, -5.0f), new Vec3f(0.1f, 0.375f, 1.0f), 5.0f, 0.0f, 0.0f, 0.5f));
	}

	float time = 0.0f;

	public void update(float dt)
	{
		// m_lights.get(0).getDir().x = (float) Math.cos(Math.toRadians(time * 0.5));
		// m_lights.get(0).getDir().z = (float) Math.cos(Math.toRadians(time * 0.25));

		time += 0.025f;
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
