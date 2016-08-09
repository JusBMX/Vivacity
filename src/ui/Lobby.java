package ui;

import app.Game;
import app.State;
import graphics.Screen;
import level.Level;

public class Lobby extends Menu {

	private static int numberOfPlayers = 1, roundTime = 15;
	private static Level level = Level.ent;

	public Lobby() {
		buttons = new Button[] { Button.numberOfPlayers, Button.roundTime, Button.level, Button.start };
	}

	public static int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public static int getRoundTime() {
		return roundTime;
	}

	public static Level getlevel() {
		return level;
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
				if (clickedButton == Button.roundTime) {
					roundTime = (roundTime % 60) + 15;
					Button.roundTime.text = Integer.toString(roundTime);
				}
				if (clickedButton == Button.level) {

				}
				if (clickedButton == Button.start) {
					Game.state = State.GAME;
				}
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

	}

}
