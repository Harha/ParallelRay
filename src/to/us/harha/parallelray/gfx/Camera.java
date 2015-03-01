package to.us.harha.parallelray.gfx;

import to.us.harha.parallelray.InputListener;
import to.us.harha.parallelray.util.math.Quaternion;
import to.us.harha.parallelray.util.math.Vec3f;

public class Camera
{

	private Vec3f      m_pos;
	private Quaternion m_rot;
	private float      m_speed;
	private float      m_sensitivity;

	public Camera(Vec3f pos, float speed, float sensitivity)
	{
		m_pos = pos;
		m_rot = new Quaternion(0, 0, 1, 0);
		m_speed = speed;
		m_sensitivity = sensitivity;
	}

	public void update(float dt)
	{
		// Camera movement
		if (InputListener.getKeyboardKeys()[InputListener.KEY_W])
		{
			move(getForward(), m_speed * dt);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_S])
		{
			move(getForward().negate(), m_speed * dt);
		}
		if (InputListener.getKeyboardKeys()[InputListener.KEY_A])
		{
			move(getRight().negate(), m_speed * dt);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_D])
		{
			move(getRight(), m_speed * dt);
		}
		if (InputListener.getKeyboardKeys()[InputListener.KEY_R])
		{
			move(getUp(), m_speed * dt);
		} else if (InputListener.getKeyboardKeys()[InputListener.KEY_F])
		{
			move(getUp().negate(), m_speed * dt);
		}

		// Camera rotation
		if (InputListener.getKeyboardKeys()[InputListener.KEY_RIGHT])
			rotate(getUp(), m_sensitivity * dt);
		if (InputListener.getKeyboardKeys()[InputListener.KEY_LEFT])
			rotate(getUp(), -m_sensitivity * dt);
		if (InputListener.getKeyboardKeys()[InputListener.KEY_UP])
			rotate(getRight(), -m_sensitivity * dt);
		if (InputListener.getKeyboardKeys()[InputListener.KEY_DOWN])
			rotate(getRight(), m_sensitivity * dt);
		if (InputListener.getKeyboardKeys()[InputListener.KEY_E])
			rotate(getForward(), m_sensitivity * dt);
		if (InputListener.getKeyboardKeys()[InputListener.KEY_Q])
			rotate(getForward(), -m_sensitivity * dt);
	}

	public void move(Vec3f direction, float amount)
	{
		m_pos.set(m_pos.add(direction.scale(amount)));
	}

	public void rotate(Vec3f axis, float theta)
	{
		Quaternion rotation = new Quaternion().createFromAxisAngle(axis.x, axis.y, axis.z, theta);
		m_rot = rotation.mul(m_rot).normalize();
	}

	public Vec3f getPos()
	{
		return m_pos;
	}

	public Quaternion getRot()
	{
		return m_rot;
	}

	public Vec3f getForward()
	{
		return m_rot.getForwardVector();
	}

	public Vec3f getRight()
	{
		return m_rot.getRightVector();
	}

	public Vec3f getUp()
	{
		return m_rot.getUpVector();
	}

}
