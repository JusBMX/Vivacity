package ui;

import graphics.Screen;
import app.Game;
import app.GameController;
import app.State;

public class Main extends UI {

	public Main() {
		buttons = new Button[] { Button.singleplayer, Button.multiplayer, Button.options };
	}

	public void tick() {
		if (Game.mouse.getButton() == 1) {
			Game.mouse.setMouseButton(-1);
			for (Button b : buttons) {
				if (b.onButton(Game.mouse.screenToWorld(false)) == Button.singleplayer) {
					GameController.state = State.LOBBY;
				}
			}
		}
	}

	public void render(Screen screen) {
		int count = 0;
		for (Button b : buttons) {
			b.render(screen.width / 2 - b.getwidth() / 2, screen.height / 2 - b.getHieght() / 2 + (count * (b.getHieght() + 5)), screen);
			count++;
		}
	}
}
