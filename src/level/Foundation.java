package level;

import java.util.ArrayList;

import entity.Entity;
import graphics.Sprite;
import graphics.SpriteSheet;

public class Foundation extends Level{

	public Foundation() {
		super(Sprite.foundationBg, new Sprite(512, 1024, 0, 0, SpriteSheet.foundation));
		name = "Foundation";
	}

	@Override
	public void loadLevel() {
		foreground = new Sprite(512, 1024, 0, 0, SpriteSheet.foundation);
		entities = new ArrayList<Entity>();
		generateCollisionMask();
	}

}
