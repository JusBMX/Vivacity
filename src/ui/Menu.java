package ui;

import graphics.Screen;

public abstract class Menu {

	public static Menu main = new Main();
	public static Lobby lobby = new Lobby(); //Change to a Lobby object and remove static variables
	
	protected Button[] buttons;

	public abstract void tick();

	public abstract void render(Screen screen);

}
