package entity.projectiles;

import entity.Entity;
import entity.Player;
import graphics.Screen;
import graphics.Sprite;

public abstract class Projectile extends Entity {
	
	public int mana;
	
	private Sprite sprite;
	
	public Projectile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public void load(int x, int y, double[] forceVector){
		super.x = x;
		super.y = y;
		super.forceVector = forceVector;
		xStart = x;
		yStart = y;
		time = System.currentTimeMillis();
	}

	@Override
	public void tick() {
		if (collision(x, y)) {
			crater(x, y, 30);
			damage(x, y, 30);
			level.generateCollisionMask();
			level.removeEntity(this);
			return;
		}
		deltaTime = System.currentTimeMillis() - time;
		x = calculatePosition()[0];
		y = calculatePosition()[1];
		if(isOutOfBounds()){
			outOfBounds();
		}
	}

	public void crater(int x, int y, int radius) {
		double equationY;
		x -= radius;
		for (int i = 0; i < radius * 2; i++) {
			equationY = Math.sqrt(-Math.pow(i - radius, 2) + Math.pow(radius, 2));
			for (int j = 0; j < equationY * 2; j++) {
				try {
					level.foreground.pixels[(x + i) + (y + j - (int) equationY) * level.foreground.getWidth()] = 0xFFFF00FF;
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
	}

	public void damage(int x, int y, int maxDamage) {
		for (Player p : level.getPlayers()) {
			p.hp -= 3 * maxDamage / (int) (Math.sqrt(Math.pow((x - p.x), 2) + Math.pow((y - p.y), 2) + 1));
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, sprite, true);
	}

	@Override
	public void outOfBounds() {
		level.removeEntity(this);
	}

}
