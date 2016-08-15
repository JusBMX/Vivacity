package ui;

import graphics.Screen;

public abstract class UI {

	public static UI main = new Main();
	public static Lobby lobby = new Lobby();

	protected Button[] buttons;

	public abstract void tick();

	public abstract void render(Screen screen);

}
