package com.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.game.display.Camera;
import com.game.display.Window;
import com.game.entities.EntityManager;
import com.game.entities.creatures.Player;
import com.game.input.KeyInput;
import com.game.sprites.Animator;

public class Game extends Canvas implements Runnable {
	public static final int HEIGHT = 1000;
	public static final int WIDTH = 1000;
	Dimension dimensions = new Dimension(WIDTH, HEIGHT);
	public static boolean IS_RUNNING = false;
	public Thread gameThread;
	public static EntityManager entityManager;
	public Camera gameCamera;
	public static Player player;
	/**
	 * 
	 */
	private static final long serialVersionUID = -20503614128161992L;

	public Game() {
		// setup
		this.setPreferredSize(dimensions);
		this.setMinimumSize(dimensions);
		this.setMaximumSize(dimensions);

		new Window("2D Game", this);

		this.addKeyListener(new KeyInput(player));
		this.setFocusable(true);

	}

	public static void main(String[] args) {
		new Game();
	}

	public void start() {
		gameThread = new Thread(this);
		entityManager = new EntityManager();
		
		try {
			Assets.initialize();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		gameCamera = new Camera(player);
		gameCamera.changeFocus(player);
		

		
		IS_RUNNING = true;
		gameThread.start();
	}

	@Override
	public void run() {
		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (IS_RUNNING) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			if (IS_RUNNING)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}
	
	public void stop() {
		try {
			gameThread.join();
			IS_RUNNING = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void render() {
		BufferStrategy buffer = this.getBufferStrategy();

		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();
		
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		entityManager.renderEntities(g);
		g.setColor(Color.green);
		g.fillOval(WIDTH/2, HEIGHT/2, 4, 4);
		g.dispose();
		buffer.show();

	}
	
	private void update() {
		Animator.updateFrames();
		entityManager.updateEntities();
		gameCamera.updateCamera();

	}
	public static boolean inBetween(double val, double low, double high, boolean inclusive) {
		if (inclusive) {
			if (val >= low && val <= high)
				return true;
			else
				return false;
		} else if (val > low && val < high)
			return true;
		return false;
	}

	public static double clamp(double num, double max) {
		if (num < max)
			return num;
		return max;
	}

	public static double clampAbs(double num, double max) {
		if (num >= 0)
			if (num >= max)
				return max;
		if (Math.abs(num) > Math.abs(max))
			return Math.abs(max) * -1;
		return num;
	}
	
	public static double dist(int x, int y, int x2, int y2)
	{
		return Math.sqrt(Math.pow(x-x2, 2) + Math.pow(y-y2, 2));
		
	}
	
	public static double dist(Dimension d1, Dimension d2)
	{
		return Math.sqrt(Math.pow(d1.width-d2.width, 2) + Math.pow(d1.height-d2.height, 2));
		
	}
}
