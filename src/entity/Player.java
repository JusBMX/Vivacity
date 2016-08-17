package entity;

import app.Game;
import graphics.Screen;
import graphics.Sprite;

public class Player extends Entity {

	public boolean active;
	public int hp = 100;

	private int force = 0;

	private int yForceVector = 0, gravity = -90, yStart, yTemp;
	private long time, deltaTime;

	private boolean fire, addToForce;

	public boolean[] collisionMask;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		time = System.currentTimeMillis();
		yStart = y;
	}

	public void tick() {
		deltaTime = System.currentTimeMillis() - time;
		yTemp = (int) (yStart + (yForceVector * deltaTime / 1000D) - (.5D) * gravity * Math.pow(deltaTime / 1000D, 2));
		
		if (!collision(x, yTemp + 32)) {
			y = yTemp;
		} else {
			yForceVector = 0;
			yStart = y;
			time = System.currentTimeMillis();
		}
		
		if (active) {
			if (Game.mouse.getButton() == 1) {
				fire = true;
				calculateForce();
			}
			if (Game.mouse.getButton() == -1 && fire) {
				level.generatePlayerCollisionMask();
				fireProjectile();
				fire = false;
				force = 0;
			}
			if (Game.keys.left) {
				moveLeft();
			}
			if (Game.keys.right) {
				moveRight();
			}
			if (Game.keys.jump && collision(x, y + 33)) {
				time = System.currentTimeMillis();
				yStart = y;
				yForceVector = -100;
			}
		}
	}

	private void fireProjectile() {
		Projectile bomb = new Projectile(x, y, Game.mouse.directionVector(x, y), force);
		bomb.intit(level);
		level.addEntity(bomb);
	}

	private void calculateForce() {
		if (force <= 0) {
			addToForce = true;
		}
		if (force >= 350) {
			addToForce = false;
		}
		if (addToForce) {
			force += 5;
		} else {
			force -= 5;
		}
	}

	private void moveLeft() {
		for (int i = 0; i < 5; i++) {
			if (!collision(x - 1, y + 32 - i)) {
				x--;
				y -= i;
				break;
			}
		}
	}

	private void moveRight() {
		for (int i = 0; i < 5; i++) {
			if (!collision(x + 1, y + 32 - i)) {
				x++;
				y -= i;
				break;
			}
		}
	}

	private void jump() {
		// TODO write better physics for player.
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x - Sprite.player.getWidth() / 2, y - Sprite.player.getHeight() / 2, Sprite.player, true);
		screen.renderText(Integer.toString(hp), x, y - 20, true);
	}

}
