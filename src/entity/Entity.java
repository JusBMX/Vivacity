package entity;

import graphics.Screen;
import level.Level;

public abstract class Entity {
	public int x, y;
	protected double xStart, yStart;
	protected Level level;
	protected int gravity = -330; // Earths gravity
	protected double[] forceVector = new double[2];
	protected long time, deltaTime;

	public abstract void tick();

	public abstract void render(Screen screen);
	
	public abstract void outOfBounds();
	
	public boolean isOutOfBounds(){
		if(y > level.getHeigth()){
			return true;
		}
		return false;
	}
	
	public boolean collision(int xAbs, int yAbs) {
		try {
			return level.getCollisionMask()[xAbs][yAbs];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public int[] calculatePosition(){
		int[] position = new int[2];
		position[0] = (int) (xStart + forceVector[0] * deltaTime / 1000D);
		position[1] = (int) (yStart + (forceVector[1] * deltaTime / 1000D) - (.5D) * gravity * Math.pow(deltaTime / 1000D, 2));
		return position;
	}

	public void intit(Level level) {
		this.level = level;
	}
}
