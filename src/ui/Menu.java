package ui;

import graphics.Screen;

public abstract class Menu {

	public static Menu main = new Main();
	public static Menu lobby = new Lobby();
	
	protected Button[] buttons;

	public abstract void tick();

	public abstract void render(Screen screen);

}
