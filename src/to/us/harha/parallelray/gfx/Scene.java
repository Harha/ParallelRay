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

		Material m_mirror_black = new Material(new Vec3f(0.01f), new Vec3f(0.0f), new Vec3f(1.0f), 1.0f);
		Material m_mirror_cyan = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.5f, 1.0f), new Vec3f(1.0f), 1.0f);
		Material m_diffuse_white = new Material(new Vec3f(0.01f), new Vec3f(1.0f, 1.0f, 1.0f), new Vec3f(1.0f), 0.0f);
		Material m_diffuse_red = new Material(new Vec3f(0.01f), new Vec3f(1.0f, 0.0f, 0.0f), new Vec3f(1.0f), 0.0f);
		Material m_diffuse_green = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 1.0f, 0.0f), new Vec3f(1.0f), 0.0f);
		Material m_diffuse_blue = new Material(new Vec3f(0.01f), new Vec3f(0.0f, 0.0f, 1.0f), new Vec3f(1.0f), 0.0f);
		
		m_objects.add(new TracerObject(new Vec3f(0.0f, -1.0f, 0.0f), new Vec3f(0.0f, 1.0f, 0.0f), m_mirror_cyan)); // White diffuse plane

		m_objects.add(new TracerObject(new Vec3f(0.0f, 0.0f, -5.0f), 1.0f, m_diffuse_white)); // White diffuse sphere
		m_objects.add(new TracerObject(new Vec3f(-0.75f, 0.75f, -4.0f), 0.25f, m_diffuse_red)); // Red diffuse sphere
		m_objects.add(new TracerObject(new Vec3f(2.0f, 1.0f, -7.5f), 1.25f, m_mirror_black)); // Green diffuse sphere
		m_objects.add(new TracerObject(new Vec3f(-0.5f, 0.0f, -2.5f), 0.25f, m_diffuse_blue)); // Blue diffuse sphere
		m_lights.add(new Light(new Vec3f(1.0f, -1.0f, -1.0f), new Vec3f(1.0f, 1.0f, 1.0f), 1.0f)); // White directional light
		//m_lights.add(new Light(new Vec3f(-0.5f, -1.0f, -0.5f), new Vec3f(0.25f, 0.5f, 1.0f), 1.75f)); // Purple directional light
	}

	float time = 0.0f;

	public void update(float dt)
	{
		m_objects.get(1).getPrimitives().get(0).getVertices()[0].x = (float) (2.0 * Math.cos(Math.toRadians(time)));
		m_objects.get(1).getPrimitives().get(0).getVertices()[0].y = (float) (1.5 * Math.cos(Math.toRadians(time * 1.5)));
		m_objects.get(1).getPrimitives().get(0).getVertices()[0].z = -5 + (float) (2.0 * Math.cos(Math.toRadians(time * 2.0)));
		//m_lights.get(0).getDir().z = (float) Math.cos(Math.toRadians(time * 5.0));

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
