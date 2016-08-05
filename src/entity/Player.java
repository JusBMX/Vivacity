package entity;

import app.Game;
import graphics.Screen;
import graphics.Sprite;

public class Player extends Entity {

	public boolean active;
	public int hp = 10;
	
	public boolean[] collisionMask;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void tick() {
		if (active) {
			if (Game.mouse.getButton() == 1) {
				Game.mouse.setMouseButton(-1);
				Projectile bobmb = new Projectile(x, y, new int[] { (int) (200 * Game.mouse.directionVector(Game.RES_X, Game.RES_Y)[0]), (int) (200 * Game.mouse.directionVector(Game.RES_X, Game.RES_Y)[1]) });
				bobmb.intit(level);
				level.addEntity(bobmb);

			}
			if (Game.keys.left) {
				for (int i = 0; i < 5; i++) {
					if (!collision(x - 1, y + 32 - i)) {
						x--;
						y -= i;
						break;
					}
				}
			}
			if (Game.keys.right) {
				for (int i = 0; i < 5; i++) {
					if (!collision(x + 1, y + 32 - i)) {
						x++;
						y -= i;
						break;
					}
				}
			}
			if (Game.keys.jump) {
				if (!collision(x, y + 1)) {
					y--;
				}
				return;
			}
		}
		if (!collision(x, y + 32 + 1)) {
			y++;
		}
	}
	
	@Override
	public void render(Screen screen) {
		screen.renderSprite(x - Sprite.player.getWidth() / 2, y - Sprite.player.getHeight() / 2, Sprite.player, true);
		screen.renderText(Integer.toString(hp), x, y, true);
	}

}
