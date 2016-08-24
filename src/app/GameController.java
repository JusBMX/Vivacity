package app;

import entity.Player;
import graphics.Screen;
import level.Level;
import ui.UI;

public class GameController {
	public static State state = State.MAINMENU;
	private Level level;
	private GameTimer timer;
	private int yOffset;

	private Player getWinner() {
		if (level.getPlayers().size() == 1) {
			return level.getPlayers().get(0);
		}
		return null;
	}
	
	public void tick() {
		if (state == State.MAINMENU) {
			UI.main.tick();
		} else if (state == State.LOBBY) {
			UI.lobby.tick();
		} else if (state == State.GAME) {
			if (Game.keys.escape) {
				state = State.LOBBY;
				level = null;
				return;
			}
			if (level == null) {
				level = UI.lobby.getlevel();
				level.loadLevel();
				level.loadPlayers();
				timer = new GameTimer(UI.lobby.getRoundTime() * 1000);
			} else {
				UI.match.tick();
				if (timer.isTime()) {
					level.switchplayers();
				}
				level.tick();
			}
		}
	}

	public void render(Screen screen) {
		if (state == State.MAINMENU) {
			UI.main.render(screen);
		} else if (state == State.LOBBY) {
			UI.lobby.render(screen);
		} else if (state == State.GAME) {

			if (level != null) {
				if(level.getActivePlayer().y - screen.getHeight() / 2 < 220){
					yOffset = level.getActivePlayer().y - screen.getHeight() / 2;
				}else{
					yOffset = 220;
				}
				screen.setOffset(level.getActivePlayer().x - screen.getWidth() / 2, yOffset);
				level.render(screen);
				screen.renderText("Time left: " + Integer.toString(timer.timeLeft() /1000), 250, 16, false);
				if (getWinner() != null) {
					screen.renderText("You Win! Press ESC to reset", 200, 250, false);
				}
				UI.match.render(screen);
			}
			
		}
	}

}
