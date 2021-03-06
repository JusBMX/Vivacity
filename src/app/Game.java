package app;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

import graphics.Screen;
import graphics.Sprite;
import input.Keyboard;
import input.Mouse;

public class Game extends Canvas implements Runnable {

	private JFrame frame;
	private Thread thread;
	private boolean running;
	private GameController gc;

	public static Screen screen;
	public static Keyboard keys;
	public static Mouse mouse;

	public static final int SCALE = 2;
	private static final long serialVersionUID = 1L;
	private static final int RES_X = 640, RES_Y = 360;

	private BufferedImage image = new BufferedImage(RES_X, RES_Y, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension res = new Dimension(RES_X * SCALE, RES_Y * SCALE);
		screen = new Screen(RES_X, RES_Y);
		keys = new Keyboard();
		mouse = new Mouse();
		gc = new GameController();
		frame = new JFrame("Game");
		setPreferredSize(res);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		addKeyListener(keys);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double nsPerTick = 1000000000D / 60D;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long nowTime = System.nanoTime();
			delta += (nowTime - lastTime) / nsPerTick;
			lastTime = nowTime;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("Ticks: " + updates + ", FPS: " + frames + ", Pre 0.0.2");
				frames = 0;
				updates = 0;
			}
		}
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		keys.tick();
		gc.tick();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		gc.render(screen);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

}
