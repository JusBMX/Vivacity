package entity;

import java.util.Random;

import app.Game;
import entity.projectiles.*;
import graphics.Screen;
import graphics.Sprite;

public class Player extends Entity {

	public boolean active;
	public int hp = 100, mana = 100;
	public boolean[] collisionMask;



	private int force = 0;

	public boolean fire, addToForce;

	public Player() {
		yStart = y;
		time = System.currentTimeMillis();
	}

	public void tick() {
		deltaTime = System.currentTimeMillis() - time;
		if (!collision(x, calculatePosition()[1] + 32)) {
			y = calculatePosition()[1];
		} else {
			forceVector[1] = 0;
			yStart = y;
			time = System.currentTimeMillis();
		}
		if (active) {
			if (Game.mouse.getButton() == 1) {
				fire = true;
				calculateForce();
			}
			if (Game.mouse.getButton() == -1 && fire) {
				fireProjectile();
			}
			if (Game.keys.left) {
				moveLeft();
			}
			if (Game.keys.right) {
				moveRight();
			}
			if (Game.keys.jump) {
				jump();
			}
		}
		if (isOutOfBounds()) {
			outOfBounds();
		}
		if (!isAlive()) {
			playerDeath();
		}
	}

	public boolean isAlive() {
		if (hp < 1) {
			return false;
		}
		return true;
	}

	public void fireProjectile() {
	    Projectile projectile = new Bomb();
		if (mana - projectile.mana >= 0) {
			mana -= projectile.mana;
			projectile.load(x, y, Game.mouse.directionVector(x, y, force));
			level.generatePlayerCollisionMask();
			projectile.intit(level);
			level.addEntity(projectile);
		}
		fire = false;
		force = 0;
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
		if (collision(x, y + 33)) {
			time = System.currentTimeMillis();
			yStart = y;
			forceVector[1] = -150;
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite(x - Sprite.player.getWidth() / 2, y - Sprite.player.getHeight() / 2 + 1, Sprite.player, true);
		if (active) {
			screen.renderText("Force: " + Integer.toString(force), 10, 16, false);
			screen.renderText("Mana: " + Integer.toString(mana), 10, 32, false);
		}
		screen.renderText(Integer.toString(hp), x, y - 20, true);
	}

	private void playerDeath() {
		if (active) {
			level.switchplayers();
		}
		level.removeEntity(this);
	}
	
	public void spawn(){
		int ran = new Random().nextInt(level.spawnPoints().size());
		x = level.spawnPoints().get(ran).get(0);
		y = level.spawnPoints().get(ran).get(1) - 33;
		time = System.currentTimeMillis();
		yStart = y;
	}

	@Override
	public void outOfBounds() {
		spawn();
		hp -= 10;
	}

}
