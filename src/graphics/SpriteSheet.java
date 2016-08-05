package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private final String PATH;
	final int SIZE;

	public int[] pixels;
	public static SpriteSheet font = new SpriteSheet("/Font.png", 128);
	public static SpriteSheet level = new SpriteSheet("/level.png", 1024);
	public static SpriteSheet main = new SpriteSheet("/main.png", 1024);

	public SpriteSheet(String path, int size) {
		this.PATH = path;
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		load();
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(PATH));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getSize() {
		return SIZE;
	}

}
