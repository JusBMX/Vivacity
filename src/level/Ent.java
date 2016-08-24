package level;

import java.util.ArrayList;

import entity.Entity;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Ent extends Level {
	public Ent() {
		super(Sprite.entBg, Sprite.ent);
		name = "Ent";
	}
	
	public void loadLevel() {
		foreground = new Sprite(512, 1024, 0, 0, SpriteSheet.ent);
		entities = new ArrayList<Entity>();
		generateCollisionMask();
	}
}
