package app;

import entity.Player;
import graphics.Screen;
import level.Level;
import ui.UI;

public class GameController {
	public static State state = State.MAINMENU;
	private Level level;
	private GameTimer timer;
	
	private void loadPlayers(){
		for (int i = 0; i < UI.lobby.getNumberOfPlayers(); i++) {
			Player player = new Player(100 + i * 100, 200);
			if (i == 0) {
				player.active = true;
			}
			level.addEntity(player);
			player.intit(level);
		}
	}
	
	private void switchplayers(){
		for (Player p : level.getPlayers()) {
			if (p.active) {
				p.active = false;
				level.getPlayers().get((level.getPlayers().indexOf(p) + 1) % level.getPlayers().size()).active = true;
				break;
			}
		}
	}

	public void tick() {
		if (state == State.MAINMENU) {
			UI.main.tick();
		} else if (state == State.LOBBY) {
			UI.lobby.tick();
			level = null;
		} else if (state == State.GAME) {
			if(Game.keys.escape){
				state = State.LOBBY;//remove
			}
			if (level == null) {
				level = UI.lobby.getlevel();
				level.loadLevel();
				timer = new GameTimer(UI.lobby.getRoundTime() * 1000);
				loadPlayers();
			} else {
				if (timer.isTime()) {
					switchplayers();
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
				screen.setOffset(level.getActivePlayer().x - screen.width / 2, level.getActivePlayer().y - screen.height / 2);
				level.render(screen);
				screen.renderText("Time left: " + Integer.toString(timer.timeLeft()), 250, 16, false);
			}
		}
	}

}
