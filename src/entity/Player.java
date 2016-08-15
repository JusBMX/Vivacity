package entity;

import app.Game;
import graphics.Screen;
import graphics.Sprite;

public class Player extends Entity {

	public boolean active;
	public int hp = 100;

	private int force = 0;
	private boolean switcher, fire;

	public boolean[] collisionMask;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void tick() {
		if (active) {
			if (Game.mouse.getButton() == 1) {
				fire = true;
				if(force <= 0 ){
					switcher = true;
				}
				if (force >= 350){
					switcher = false;
				}
				if(switcher){
					force += 5;
				}else{
					force -= 5;
				}
			}
			if(Game.mouse.getButton() == -1 && fire){
				level.generatePlayerCollisionMask();
				Projectile bobmb = new Projectile(x, y, Game.mouse.directionVector(x, y), force);
				bobmb.intit(level);
				level.addEntity(bobmb);
				fire = false;
				force = 0;
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
		screen.renderText("Force: " + Integer.toString(force), 10, 16, false);
	}

}
