package graphics;

public class Sprite {
	
	private int x, y;
	public final int[] pixels;
	private int width, height;
	private SpriteSheet sheet;
	private final int SIZE;

	public static Sprite ent = new Sprite(512, 1024, 0, 0, SpriteSheet.ent);
	public static Sprite entBg = new Sprite(512, 1024, 0, 1, SpriteSheet.ent);
	public static Sprite foundation = new Sprite(512, 1024, 0, 0, SpriteSheet.foundation);
	public static Sprite foundationBg = new Sprite(512, 1024, 0, 1, SpriteSheet.foundation);
	public static Sprite player = new Sprite(64, 0, 0, SpriteSheet.main);
	public static Sprite bomb = new Sprite(16, 4, 0, SpriteSheet.main);
	public static Sprite firebomb = new Sprite(16, 5, 0, SpriteSheet.main);
	public static Sprite button = new Sprite(16, 64, 1, 2, SpriteSheet.main);
	public static Sprite water = new Sprite(64, 2, 0, SpriteSheet.main);
	
	public static Sprite bombUI = new Sprite(16, 4, 1, SpriteSheet.main);
	public static Sprite fireBombUI = new Sprite(16, 5, 1, SpriteSheet.main);

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.width = size;
		this.height = size;
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}

	public Sprite(int height, int width, int x, int y, SpriteSheet sheet) {
		SIZE = -1;
		pixels = new int[height * width];
		this.width = width;
		this.height = height;
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		load();
	}

	public void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getSize()];
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
