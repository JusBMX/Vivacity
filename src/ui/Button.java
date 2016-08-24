package ui;

import graphics.Screen;
import graphics.Sprite;

public class Button {
	
	private int width, height, x, y;
	private Sprite sprite;

	public String text;

	public static Button singleplayer = new Button(Sprite.button, "Singleplayer");
	public static Button multiplayer = new Button(Sprite.button, "Multiplayer");
	public static Button options = new Button(Sprite.button, "Options");
	public static Button numberOfPlayers = new Button(Sprite.button, "1");
	public static Button roundTime = new Button(Sprite.button, "15");
	public static Button level = new Button(Sprite.button, "Ent");
	public static Button start = new Button(Sprite.button, "Start");
	public static Button back = new Button(Sprite.button, "Back");
	
	public static Button bomb = new Button(Sprite.bombUI);
	public static Button fireBomb = new Button(Sprite.fireBombUI);

	public Button(Sprite sprite, String text) {
		this.width = Sprite.button.getWidth();
		this.height = Sprite.button.getHeight();
		this.text = text;
		this.sprite = sprite;
	}
	
	public Button(Sprite sprite) {
		this.width = Sprite.button.getWidth();
		this.height = Sprite.button.getHeight();
		this.text = "";
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
		this.x = x;
		this.y = y;
		screen.renderSprite(x, y, sprite, false);
		screen.renderText(text, (x + width / 2) - text.length() * 4, y + 4, false);
	}

	public Button onButton(int[] coord) {
		if ((coord[0] >= x && coord[0] <= x + width) && (coord[1] >= y && coord[1] <= y + height)) {
			return this;
		}
		return null;
	}

	public int getwidth() {
		return width;
	}

	public int getHieght() {
		return height;
	}

}
