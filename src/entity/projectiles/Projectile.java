package entity;

import graphics.Screen;
import graphics.Sprite;

public class Projectile extends Entity {
	private double[] forceVector = new double[2];
	private long time, deltaTime;
	private final double gravity = -90;
	private double xStart, yStart;

	public Projectile(int x, int y, double[] forceVector, int scalar) {
		this.x = x;
		this.y = y;
		xStart = x;
		yStart = y;
		for (int i = 0; i < forceVector.length; i++) {
			this.forceVector[i] = forceVector[i] * scalar;
		}
		time = System.currentTimeMillis();
	}

	@Override
	public void tick() {
		if (collision(x, y)) {
			crater(x, y, 30);
			level.generateCollisionMask();
			damage(x, y, 30);
			level.removeEntity(this);
			return;
		}
		deltaTime = System.currentTimeMillis() - time;
		y = (int) (yStart + (forceVector[1] * deltaTime / 1000D) - (.5D) * gravity * Math.pow(deltaTime / 1000D, 2));
		x = (int) (xStart + forceVector[0] * deltaTime / 1000D);
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
			p.hp -= 3 * maxDamage / (int) Math.sqrt(Math.pow((x - p.x), 2) + Math.pow((y - p.y), 2));
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x - Sprite.bomb.SIZE / 2, y - Sprite.bomb.SIZE / 2, Sprite.bomb, true);
	}

}
