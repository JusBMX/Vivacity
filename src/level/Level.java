package level;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.Sprite;

public class Level {

	public int width;
	protected int height;
	public boolean[] collisionMask;

	private List<Entity> entities = new ArrayList<Entity>();

	public static Level ent = new Level(Sprite.ent_bg, Sprite.ent);

	public Level(Sprite Background, Sprite Foreground) {
		loadLevel();
	}

	protected void loadLevel() {
		generateCollisionMask();
		width = Sprite.ent.getWidth();
		height = Sprite.ent.getHeight();
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
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

	public void generateCollisionMask() {
		collisionMask = new boolean[Sprite.ent.pixels.length];
		for (int i = 0; i < Sprite.ent.pixels.length; i++) {
			if (Sprite.ent.pixels[i] == 0xFFFF00FF) {
				collisionMask[i] = false;
			} else {
				collisionMask[i] = true;
			}
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(0, 0, Sprite.ent_bg, false);
		screen.renderSprite(0, 0, Sprite.ent, true);

		for (Entity e : entities) {
			e.render(screen);
		}
	}

}
