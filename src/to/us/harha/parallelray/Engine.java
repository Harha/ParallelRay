package to.us.harha.parallelray;

import to.us.harha.parallelray.gfx.Display;
import to.us.harha.parallelray.gfx.Tracer;
import to.us.harha.parallelray.util.Logger;

public class Engine implements Runnable
{

	private boolean       m_running;

	private Display       m_display;
	private Tracer        m_tracer;
	private InputListener m_ilistener;
	private Logger        m_log;

	public Engine(Display display, Tracer tracer)
	{
		m_display = display;
		m_ilistener = new InputListener();
		m_display.addKeyListener(m_ilistener);
		m_display.addMouseListener(m_ilistener);
		m_display.addMouseMotionListener(m_ilistener);

		m_tracer = tracer;

		m_log = new Logger(this.getClass().getName());

		m_running = false;
	}

	public synchronized void start()
	{
		if (!m_running)
		{
			m_running = true;
			run();
		}
	}

	public synchronized void stop()
	{
		if (m_running)
		{
			m_running = false;
			System.exit(0);
		}
	}

	@Override
	public void run()
	{
		long currentTime;
		long lastTime = System.nanoTime();
		while (m_running && m_display != null)
		{
			currentTime = System.nanoTime();
			m_display.setTitle(String.format(" | Workers: %02d DeltaTime: %04dms FPS: %3.2f/s", m_tracer.getWorkers().size(), m_tracer.getDeltaTime() / 1000000L, m_tracer.getFPS()));

			update((float) (m_tracer.getDeltaTime() / 10000000L));
			render();

			lastTime = System.nanoTime();
		}

		stop();
	}

	public void update(float dt)
	{
		m_tracer.update(dt);
	}

	public void render()
	{
		m_tracer.render(m_display);
		m_display.render();
	}

}
