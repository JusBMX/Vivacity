package graphics;

public class Sprite {
	private int x, y;
	private SpriteSheet sheet;

	public final int SIZE;
	public int[] pixels;
	private int width, height;

	public static Sprite ent = new Sprite(512, 1024, 0, 0, SpriteSheet.level);
	public static Sprite ent_bg = new Sprite(512, 1024, 0, 1, SpriteSheet.level);
	public static Sprite player = new Sprite(64, 0, 0, SpriteSheet.main);
	public static Sprite bomb = new Sprite(16, 4, 0, SpriteSheet.main);
	public static Sprite button = new Sprite(16, 64, 1, 2, SpriteSheet.main);


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
		SIZE = width;
		pixels = new int[height * width];
		this.width = width;
		this.height = height;
		this.x = x * width;
		this.y = y * height;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);

	}

	private void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}

	public void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
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
