package graphics;

public class Screen {
	public int width, height;
	public int xOffset, yOffset;
	public int[] pixels;

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:-";

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xFF0000FF;
		}
	}

	public void renderText(String msg, int x, int y, boolean movable) {
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			if (charIndex >= 0) {
				renderSprite(x + (i * 8), y, new Sprite(8, charIndex % 16, charIndex / 16, SpriteSheet.font), movable);
			}
		}
	}

	public void renderSprite(int xPos, int yPos, Sprite sprite, boolean movable) {
		if (movable) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yAbs = yPos + y;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xAbs = xPos + x;
				if (xAbs < 0 || xAbs >= width || yAbs < 0 || yAbs >= height)
					continue;
				if (sprite.pixels[x + y * sprite.getWidth()] != 0xFFFF00FF)
					pixels[xAbs + yAbs * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}
	
	public void renderPoint(int xPos, int yPos, int color, boolean movable){
		if (movable) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		if (xPos < 0 || xPos >= width || yPos < 0 || yPos >= height){
			return;
		}
		pixels[xPos + yPos * width] = color;
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
