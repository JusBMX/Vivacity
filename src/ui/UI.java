package ui;

import app.Game;
import graphics.Screen;

public abstract class UI {

	public static UI main = new Main();
	public static Lobby lobby = new Lobby();

	protected Button[] buttons;

	public abstract void tick();

	public abstract void render(Screen screen);

	public Button getClickedButton() {
		int[] coords = Game.mouse.screenToWorld(false);
		Button clickedButton = null;
		for (Button b : buttons) {
			clickedButton = b.onButton(coords);
			if (clickedButton != null) {
				break;
			}
		}
		return clickedButton;
	}

}
