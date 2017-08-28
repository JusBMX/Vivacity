package ui;

import app.Game;
import entity.projectiles.Bomb;
import entity.projectiles.FireBomb;
import entity.projectiles.Projectile;
import graphics.Screen;

public class Match extends UI{
	
	public Projectile projectile = new Bomb();
	
	public Match(){
		buttons = new Button[] {Button.bomb, Button.fireBomb};
	}
	
	@Override
	public void tick() {

	}

	@Override
	public void render(Screen screen) {
		Button.fireBomb.render(0, screen.getHeight() - Button.fireBomb.getHieght(), screen);
		Button.bomb.render(16, screen.getHeight() - Button.bomb.getHieght(), screen);
	}

}
