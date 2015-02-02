package to.us.harha.parallelray;

import to.us.harha.parallelray.gfx.Display;
import to.us.harha.parallelray.gfx.Tracer;
import to.us.harha.parallelray.util.Logger;
import to.us.harha.parallelray.util.TimeUtils;

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
		while (m_running && m_display != null)
		{
			m_display.setTitle(String.format("Workers: %02d DeltaTime: %2.2f FPS: %2.2f/s Eye: %s", m_tracer.getWorkers().size(), TimeUtils.getDelta(), TimeUtils.getFPS(), m_tracer.getCamera().getRot().toString()));
			//m_display.setTitle();
			update((float) TimeUtils.getDelta());
			render();
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
