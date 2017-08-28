package entity.projectiles;

import graphics.Sprite;

public class Bomb extends Projectile{
	public Bomb() {
		super(Sprite.bomb);
		mana = 50;
	}
}
