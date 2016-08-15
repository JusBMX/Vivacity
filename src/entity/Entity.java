package entity;

import graphics.Screen;
import level.Level;

public abstract class Entity {
	public int x, y;
	protected Level level;

	public abstract void tick();

	public abstract void render(Screen screen);

	public boolean collision(int xAbs, int yAbs) {
		try{
			return level.collisionMask[xAbs][yAbs];
		}catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}

	public void intit(Level level) {
		this.level = level;
	}
}
