package to.us.harha.parallelray.gfx;

import java.util.ArrayList;

import to.us.harha.parallelray.util.Config;

public class Tracer
{

	private long              m_lastTime;
	private long              m_deltaTime;

	private Scene             m_scene;
	private ArrayList<Worker> m_workers;

	public Tracer()
	{
		m_lastTime = System.nanoTime();
		m_deltaTime = 0L;
		m_scene = new Scene();
		m_workers = new ArrayList<Worker>();
		setWorkerAmount(Config.g_thread_amount);
	}

	public void setWorkerAmount(int n)
	{
		if (n <= 0)
			n = Runtime.getRuntime().availableProcessors();

		m_workers.clear();

		int width = Config.g_window_width;
		int height = Config.g_window_height;

		if (n % 2 == 0 && n > 2)
		{
			width /= (n / 2);
			height /= (n / 2);
			for (int j = 0; j < n / 2; j++)
			{
				for (int i = 0; i < n / 2; i++)
				{
					m_workers.add(new Worker(width, height, i * width, j * height, i + j * width, this));
				}
			}
		} else
		{
			m_workers.add(new Worker(width, height, 0, 0, 0, this));
		}
	}

	public void update(float dt)
	{
		m_scene.update(dt);
	}

	public void render(Display display)
	{
		if (workersFinished())
		{
			long currentTime = System.nanoTime();
			long oldDelta = m_deltaTime;
			m_deltaTime = currentTime - m_lastTime;
			for (Worker w : m_workers)
			{
				w.setDisplay(display);
				Thread worker = new Thread(w, "Worker: " + w.getId());
				worker.start();
			}
			m_lastTime = System.nanoTime();
		}
	}

	public synchronized long getDeltaTime()
	{
		return m_deltaTime;
	}

	public synchronized float getFPS()
	{
		return 1000.0f / (float) (m_deltaTime / 1000000L);
	}

	public Scene getScene()
	{
		return m_scene;
	}

	public ArrayList<Worker> getWorkers()
	{
		return m_workers;
	}

	public boolean workersFinished()
	{
		for (Worker w : m_workers)
			if (!w.isFinished())
				return false;
		return true;
	}

}
