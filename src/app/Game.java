package app;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

import entity.Player;
import graphics.Screen;
import input.Keyboard;
import input.Mouse;
import level.Level;
import ui.Lobby;
import ui.Menu;

public class Game extends Canvas implements Runnable {

	private JFrame frame;
	private Thread thread;
	private Level level;
	private GameTimer timer;
	private boolean running;
	public static Screen screen;
	public static State state;
	public static Keyboard keys;
	public static Mouse mouse;

	private static final long serialVersionUID = 1L;
	private static final int SCALE = 2;
	public static final int RES_X = 640, RES_Y = 360;

	private BufferedImage image = new BufferedImage(RES_X, RES_Y, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension res = new Dimension(RES_X * SCALE + 8, RES_Y * SCALE + 30);
		screen = new Screen(RES_X, RES_Y);
		keys = new Keyboard();
		mouse = new Mouse();
		state = State.MAINMENU;
		frame = new JFrame("Game");
		frame.setPreferredSize(res);
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
				frame.setTitle("Ticks: " + updates + ", FPS: " + frames);
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
		if (state == State.MAINMENU) {
			Menu.main.tick();
		} else if (state == State.LOBBY) {
			Menu.lobby.tick();
		} else if (state == State.GAME) {
			if (level == null) {
				level = Lobby.getlevel();
				timer = new GameTimer(Lobby.getRoundTime() * 1000);
				for (int i = 0; i < Lobby.getNumberOfPlayers(); i++) {
					Player player = new Player(120 + i * 60, 120);
					if (i == 0) {
						player.active = true;
					}
					level.addEntity(player);
					player.intit(level);
				}

			} else {
				if (timer.isTime()) {
					for (Player p : level.getPlayers()) {
						if (p.active) {
							p.active = false;
							System.out.println();
							level.getPlayers().get((level.getPlayers().indexOf(p) + 1) % level.getPlayers().size()).active = true;
							break;
						}
					}
				}
				level.tick();
				screen.setOffset(level.getActivePlayer().x - RES_X / SCALE, level.getActivePlayer().y - RES_Y / SCALE);
			}
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.clear();

		if (state == State.MAINMENU) {
			Menu.main.render(screen);
		} else if (state == State.LOBBY) {
			Menu.lobby.render(screen);
		} else if (state == State.GAME) {
			if (level != null) {
				level.render(screen);
				screen.renderText("Time left: " + Integer.toString(timer.timeLeft()), 300, 16, false);
			}
		}

		// screen.renderText("X:" + mouse.screenToWorld(screen)[0] + " Y:" +
		// mouse.screenToWorld(screen)[1], 10, 10, false);

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
