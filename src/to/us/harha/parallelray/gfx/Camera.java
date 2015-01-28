package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.InputListener;
import to.us.harha.parallelray.util.math.Quaternion;
import to.us.harha.parallelray.util.math.Vec3f;

public class Camera
{

	private static final Vec3f YAXIS = new Vec3f(0.0f, 1.0f, 0.0f);

	private Vec3f              m_pos;
	private Quaternion         m_lookAt;
	private float              m_speed;
	private float              m_sensitivity;

	public Camera(Vec3f pos, Quaternion lookAt, float speed, float sensitivity)
	{
		m_pos = pos;
		m_lookAt = lookAt;
		m_speed = speed;
		m_sensitivity = sensitivity;
	}

	public void update(float dt)
	{
		// Camera movement
		if (InputListener.getKeyboardKeys()[InputListener.KEY_W])
		{
			move(getForward(), m_speed);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_S])
		{
			move(getForward().negate(), m_speed);
		}
		if (InputListener.getKeyboardKeys()[InputListener.KEY_A])
		{
			move(getRight().negate(), m_speed);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_D])
		{
			move(getRight(), m_speed);
		}
		if (InputListener.getKeyboardKeys()[InputListener.KEY_R])
		{
			move(getUp(), m_speed);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_F])
		{
			move(getUp().negate(), m_speed);
		}

		// Camera rotation
		if (InputListener.getKeyboardKeys()[InputListener.KEY_UP])
		{
			rotate(getRight(), m_sensitivity);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_DOWN])
		{
			rotate(getRight(), -m_sensitivity);
		}
		if (InputListener.getKeyboardKeys()[InputListener.KEY_LEFT])
		{
			rotate(getUp(), m_sensitivity);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_RIGHT])
		{
			rotate(getUp(), -m_sensitivity);
		}
	}

	public void move(Vec3f direction, float amount)
	{
		m_pos.set(m_pos.add(direction.scale(amount)));
	}

	public void rotate(Vec3f axis, float theta)
	{
		Quaternion q = new Quaternion().createFromAxisAngle(axis.x, axis.y, axis.z, theta);
		Quaternion q_inv = Quaternion.conjugate(q);
		m_lookAt = Quaternion.mul(Quaternion.mul(q, m_lookAt), q_inv);
	}

	public Vec3f getPos()
	{
		return m_pos;
	}

	public Vec3f getForward()
	{
		// return m_lookAt.getForwardVector().normalize();
		return new Vec3f(m_lookAt.x, m_lookAt.y, m_lookAt.z).normalize();
	}

	public Vec3f getUp()
	{
		//return m_lookAt.getUpVector().normalize();
		return YAXIS.normalize();
	}

	public Vec3f getRight()
	{
		//return m_lookAt.getRightVector().normalize();
		return new Vec3f(m_lookAt.x, m_lookAt.y, m_lookAt.z).cross(YAXIS).normalize();
	}

}
