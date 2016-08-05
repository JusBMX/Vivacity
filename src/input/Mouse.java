package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import graphics.Screen;

public class Mouse implements MouseListener, MouseMotionListener {

	private static int mouseX = -1;
	private static int mouseY = -1;
	private static int mouseButton = -1;

	public int getX() {
		return mouseX;
	}

	public int getY() {
		return mouseY;
	}

	public int getButton() {
		return mouseButton;
	}

	public void setMouseButton(int button) {
		mouseButton = button;
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		mouseButton = -1;
	}

	public int[] screenToWorld(Screen screen) {
		int[] coords = { screen.xOffset + mouseX / 2, screen.yOffset + mouseY / 2};
		return coords;
	}

	public double[] directionVector(int originX, int originY) {
		double magnitue = Math.sqrt(Math.pow(mouseX - originX, 2) + Math.pow(mouseY - originY, 2));
		double[] vector = new double[2];
		vector[0] = (mouseX - originX) / magnitue;
		vector[1] = (mouseY - originY) / magnitue;
		return vector;
	}

}
