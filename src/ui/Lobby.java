package ui;

import app.Game;
import app.GameController;
import app.State;
import graphics.Screen;
import level.Ent;
import level.Level;

public class Lobby extends UI {

	private int numberOfPlayers = 1, roundTime = 15, level = 1;

	public Lobby() {
		buttons = new Button[] { Button.numberOfPlayers, Button.roundTime, Button.level, Button.start, Button.back };
	}

	@Override
	public void tick() {
		if (Game.mouse.getButton() == 1) {
			Game.mouse.setMouseButton(-1);
			Button clickedButton = getClickedButton();
			if (clickedButton == Button.numberOfPlayers) {
				numberOfPlayers = (numberOfPlayers % 4) + 1;
				Button.numberOfPlayers.text = Integer.toString(numberOfPlayers);
			}
			if (clickedButton == Button.roundTime) {
				roundTime = (roundTime % 60) + 15;
				Button.roundTime.text = Integer.toString(roundTime);
			}
			if (clickedButton == Button.level) {
				level = (level % Level.levels.length) + 1;
				Button.level.text = Level.levels[level - 1].name;
			}
			if (clickedButton == Button.start) {
				GameController.state = State.GAME;
			}
			if (clickedButton == Button.back) {
				GameController.state = State.MAINMENU;
			}
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderText("Number of Players", 16, 16, false);
		Button.numberOfPlayers.render(16, 26, screen);

		screen.renderText("Round time", 16, 48, false);
		Button.roundTime.render(16, 58, screen);

		screen.renderText("Level", 16, 80, false);
		Button.level.render(16, 90, screen);

		Button.start.render(16, 128, screen);

		Button.back.render(256, 128, screen);
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public int getRoundTime() {
		return roundTime;
	}

	public Level getlevel() {
		return Level.levels[level - 1];
	}

}
