package level;

import java.util.ArrayList;
import java.util.List;

import app.Game;
import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.Sprite;

public class Level {

	public int width;
	protected int height;
	public boolean[] collisionMask;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private int numberOfPlayers;

	public static Level spawnlevel = new Level(2, 40, Sprite.ent_bg, Sprite.ent);

	public Level(int numberOfPlayers, int turnTime, Sprite Background, Sprite Foreground) {
		this.numberOfPlayers = numberOfPlayers;
		loadLevel();
	}

	protected void loadLevel() {
		generateCollisionMask();
		width = Sprite.ent.getWidth();
		height = Sprite.ent.getHeight();
		for(int i = 0; i < numberOfPlayers; i++){
			Player player = new Player(120, 120);
			if(i == 0){
				player.active = true;
			}
			addEntity(player);
			player.intit(this);
		}
	}

	public void tick() {
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).tick();
		}
		if(Game.keys.use){
			getActivePlayer().active = false;
			getPlayers().get(1).active = true;
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public List<Entity> getEntities(){
		return entities;
	}
	
	public List<Player> getPlayers(){
		List<Player> players = new ArrayList<Player>();
		for(Entity e : entities){
			if(e.getClass().equals(Player.class)){
				players.add((Player) e);
			}
		}
		return players;
	}

	public Player getActivePlayer(){
		for(Player p : getPlayers()){
			if(p.active){
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
