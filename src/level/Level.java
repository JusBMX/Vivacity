package level;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.Sprite;
import ui.UI;

public abstract class Level {

	protected int width;
	protected int height;
	private boolean[][] collisionMask;
	protected List<Entity> entities;

	public String name = "";

	public Sprite foreground, background;

	public static Level[] levels = new Level[] { new Ent(), new Foundation() };

	public Level(Sprite background, Sprite foreground) {
		this.foreground = foreground;
		this.background = background;
		width = foreground.getWidth();
		height = foreground.getHeight();
		collisionMask = new boolean[width][height];
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(0, 0, background, false);
		screen.renderSprite(0, 0, foreground, true);
		for (Entity e : entities) {
			e.render(screen);
		}
		for (int i = 0; i < width / Sprite.water.getWidth(); i++) {
			screen.renderSprite(i * Sprite.water.getWidth(), height - 32, Sprite.water, true);
		}
	}

	public abstract void loadLevel();

	public List<List<Integer>> spawnPoints() {
		List<List<Integer>> listOLists  = new ArrayList<List<Integer>>();

		for (int i = 0; i < collisionMask.length; i++) {
			for (int j = 1; j < collisionMask[i].length; j++) {
				if (collisionMask[i][j] && !collisionMask[i][j - 1]) {
					List<Integer> xy = new ArrayList<Integer>();
					xy.add(i);
					xy.add(j);
					listOLists.add(xy);
				}
			}
		}
		return listOLists;
	}

	public void loadPlayers() {
		for (int i = 0; i < UI.lobby.getNumberOfPlayers(); i++) {
			Player player = new Player();
			if (i == 0) {
				player.active = true;
			}
			player.intit(this);
			addEntity(player);
			player.spawn();
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Player> getPlayers() {
		List<Player> players = new ArrayList<Player>();
		for (Entity e : entities) {
			if (e.getClass().equals(Player.class)) {
				players.add((Player) e);
			}
		}
		return players;
	}

	public Player getActivePlayer() {
		for (Player p : getPlayers()) {
			if (p.active) {
				return p;
			}
		}
		return null;
	}

	public void switchplayers() {
		for (Player p : getPlayers()) {
			if (p.active) {
				p.active = false;
				p.mana = 100;
				getPlayers().get((getPlayers().indexOf(p) + 1) % getPlayers().size()).active = true;
				break;
			}
		}
	}

	public int getHeigth() {
		return height;
	}

	public boolean[][] getCollisionMask() {
		return collisionMask;
	}

	public void generateCollisionMask() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (foreground.pixels[i + j * foreground.getWidth()] == 0xFFFF00FF) {
					collisionMask[i][j] = false;
				} else {
					collisionMask[i][j] = true;
				}
			}
		}
	}

	public void generatePlayerCollisionMask() {// TODO Change to use a different
												// collisionMask
		for (Player p : getPlayers()) {
			if (p == getActivePlayer()) {
				continue;
			}
			for (int i = 0; i < Sprite.player.getWidth(); i++) {
				for (int j = 0; j < Sprite.player.getHeight(); j++) {
					if (Sprite.player.pixels[i + j * Sprite.player.getWidth()] == 0xFFFF00FF) {
						collisionMask[i + p.x - Sprite.player.getWidth() / 2][j + p.y - Sprite.player.getHeight() / 2] = false;
					} else {
						collisionMask[i + p.x - Sprite.player.getWidth() / 2][j + p.y - Sprite.player.getHeight() / 2] = true;
					}
				}
			}
		}
	}

}
