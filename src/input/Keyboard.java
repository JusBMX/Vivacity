package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	private boolean[] keys = new boolean[255];

	public boolean left, right, up, down, enter, escape, jump, use, debug, one, two, three;

	public void tick() {
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		enter = keys[KeyEvent.VK_ENTER];
		escape = keys[KeyEvent.VK_ESCAPE];
		jump = keys[KeyEvent.VK_SPACE];
		use = keys[KeyEvent.VK_E];
		debug = keys[KeyEvent.VK_BACK_QUOTE];
		one = keys[KeyEvent.VK_1];
		two = keys[KeyEvent.VK_2];
		three = keys[KeyEvent.VK_3];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

}
