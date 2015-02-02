package to.us.harha.parallelray.util.math;

public class Vec3f
{

	public float x;
	public float y;
	public float z;

	public Vec3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3f(float f)
	{
		x = f;
		y = f;
		z = f;
	}

	public Vec3f()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}

	@Override
	public String toString()
	{
		return String.format("Vec3f[%.5f, %.5f, %.5f]", x, y, z);
	}

	public boolean equals(Vec3f v)
	{
		if (x == v.x && y == v.y && z == v.z)
			return true;
		else
			return false;
	}

	public Vec3f set(Vec3f v)
	{
		x = v.x;
		y = v.y;
		z = v.z;
		return this;
	}

	public Vec3f set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vec3f add(Vec3f v)
	{
		return new Vec3f(x + v.x, y + v.y, z + v.z);
	}

	public Vec3f add(float f)
	{
		return new Vec3f(x + f, y + f, z + f);
	}

	public Vec3f sub(Vec3f v)
	{
		return new Vec3f(x - v.x, y - v.y, z - v.z);
	}

	public Vec3f sub(float f)
	{
		return new Vec3f(x - f, y - f, z - f);
	}

	public Vec3f scale(Vec3f v)
	{
		return new Vec3f(x * v.x, y * v.y, z * v.z);
	}

	public Vec3f scale(float f)
	{
		return new Vec3f(x * f, y * f, z * f);
	}

	public Vec3f divide(Vec3f v)
	{
		return new Vec3f(x / v.x, y / v.y, z / v.z);
	}

	public Vec3f divide(float f)
	{
		return new Vec3f(x / f, y / f, z / f);
	}

	public Vec3f cross(Vec3f v)
	{
		return new Vec3f(y * v.z - v.y * z, z * v.x - v.z * x, x * v.y - v.x * y);
	}

	public float dot(Vec3f v)
	{
		return x * v.x + y * v.y + z * v.z;
	}

	public Vec3f mul(Quaternion q)
	{
		Quaternion q_inv = q.conjugate();

		Quaternion w = q.mul(this).mul(q_inv);

		return new Vec3f(w.x, w.y, w.z);
	}

	public float length()
	{
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public Vec3f normalize()
	{
		float length = length();
		return new Vec3f(x / length, y / length, z / length);
	}

	public Vec3f negate()
	{
		return new Vec3f(-x, -y, -z);
	}

	public Vec3f reflect(Vec3f N)
	{
		return this.sub(N.scale(N.dot(this)).scale(2.0f));
	}

	public float getComponent(int i, float w)
	{
		if (i == 0)
			return this.x;
		else if (i == 1)
			return this.y;
		else if (i == 2)
			return this.z;
		else if (i == 3)
			return w;
		else
			return 0.0f;
	}

	public void setComponent(int i, float value)
	{
		if (i == 0)
			this.x = value;
		else if (i == 1)
			this.y = value;
		else if (i == 2)
			this.z = value;
	}

}