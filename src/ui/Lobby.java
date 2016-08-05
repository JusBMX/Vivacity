package ui;

import app.Game;
import app.State;
import graphics.Screen;
import level.Level;

public class Lobby extends Menu {

	private int numberOfPlayers = 1, turnTime = 30;
	private Level level;

	public Lobby() {
		buttons = new Button[] { Button.numberOfPlayers, Button.roundTime, Button.level, Button.start };
	}

	@Override
	public void tick() {
		if (Game.mouse.getButton() == 1) {
			Game.mouse.setMouseButton(-1);
			for (Button b : buttons) {
				Button clickedButton = b.onButton(Game.mouse.screenToWorld(Game.screen));
				if (clickedButton == Button.numberOfPlayers) {
					numberOfPlayers = (numberOfPlayers % 4) + 1;
					Button.numberOfPlayers.text = Integer.toString(numberOfPlayers);
				}
				if (clickedButton == Button.start) {
					Game.state = State.GAME;
				}
			}
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderText("Number of Players", 10, 10, false);
		Button.numberOfPlayers.render(10, 20, screen);
		Button.start.render(10, 40, screen);

	}

}
