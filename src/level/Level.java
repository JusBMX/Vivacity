package level;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;

public abstract class Level {

	private int width, height;
	public Sprite foreground;
	public boolean[][] collisionMask;

	private List<Entity> entities;

	public static Level ent = new Ent(Sprite.ent_bg, Sprite.ent);

	public Level(Sprite background, Sprite foreground) {
		this.foreground = new Sprite(512, 1024, 0, 0, SpriteSheet.level);
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
		screen.renderSprite(0, 0, Sprite.ent_bg, false);
		screen.renderSprite(0, 0, foreground, true);
		for (Entity e : entities) {
			e.render(screen);
		}
	}
	
	public void loadLevel() {
		foreground = new Sprite(512, 1024, 0, 0, SpriteSheet.level);
		generateCollisionMask();
		entities = new ArrayList<Entity>();
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

	public void generatePlayerCollisionMask() {
		for (Player p : getPlayers()) {
			if (p == getActivePlayer()) {
				continue;
			}
			for (int i = 0; i < Sprite.player.getWidth(); i++) {
				for (int j = 0; j < Sprite.player.getHeight(); j++) {
					if (Sprite.player.pixels[i + j * Sprite.player.getWidth()] == 0xFFFF00FF) {
						collisionMask[i + p.x - Sprite.player.getWidth() / 2][j + p.y
								- Sprite.player.getHeight() / 2] = false;
					} else {
						collisionMask[i + p.x - Sprite.player.getWidth() / 2][j + p.y
								- Sprite.player.getHeight() / 2] = true;
					}
				}
			}
		}
	}
}
