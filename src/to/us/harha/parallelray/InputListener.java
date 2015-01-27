package to.us.harha.parallelray;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputListener implements KeyListener, MouseListener, MouseMotionListener
{

	private static boolean[] g_keyboard_keys   = new boolean[255];
	private static boolean[] g_mouse_buttons   = new boolean[8];
	private static int       g_mouse_x         = 0;
	private static int       g_mouse_y         = 0;
	private static int       g_mouse_dragged_x = 0;
	private static int       g_mouse_dragged_y = 0;
	private static int       g_mouse_moved_x   = 0;
	private static int       g_mouse_moved_y   = 0;

	public static final int  KEY_UP            = 38;
	public static final int  KEY_DOWN          = 40;
	public static final int  KEY_LEFT          = 37;
	public static final int  KEY_RIGHT         = 39;
	public static final int  KEY_SPACE         = 32;
	public static final int  KEY_W             = 87;
	public static final int  KEY_S             = 83;
	public static final int  KEY_A             = 65;
	public static final int  KEY_D             = 68;
	public static final int  KEY_1             = 49;
	public static final int  KEY_2             = 50;
	public static final int  KEY_3             = 51;
	public static final int  KEY_R             = 82;
	public static final int  KEY_F             = 70;
	public static final int  KEY_Q             = 81;
	public static final int  KEY_E             = 69;
	public static final int  KEY_PLUS          = 107;
	public static final int  KEY_MINUS         = 109;

	public InputListener()
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		g_keyboard_keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		g_keyboard_keys[e.getKeyCode()] = false;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		g_mouse_buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		g_mouse_buttons[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		g_mouse_x = e.getX();
		g_mouse_y = e.getY();
		g_mouse_dragged_x = e.getX();
		g_mouse_dragged_y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		g_mouse_x = e.getX();
		g_mouse_y = e.getY();
		g_mouse_moved_x = e.getX();
		g_mouse_moved_y = e.getY();
	}

	public static boolean[] getKeyboardKeys()
	{
		return g_keyboard_keys;
	}

	public boolean getKey(int k)
	{
		return g_keyboard_keys[k];
	}

	public static boolean[] getMouseButtons()
	{
		return g_mouse_buttons;
	}

	public static int getMouseX()
	{
		return g_mouse_x;
	}

	public static int getMouseY()
	{
		return g_mouse_y;
	}

	public static int getMouseDraggedX()
	{
		return g_mouse_dragged_x;
	}

	public static int getMouseDraggedY()
	{
		return g_mouse_dragged_y;
	}

	public static int getMouseMovedX()
	{
		return g_mouse_moved_x;
	}

	public static int getMouseMovedY()
	{
		return g_mouse_moved_y;
	}

}
