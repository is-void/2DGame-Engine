package com.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

import com.game.GameState.GAMESTATE;
import com.game.data.Settings;
import com.game.display.Camera;
import com.game.display.Window;
import com.game.display.ui.UIManager;
import com.game.entities.creatures.Player;
import com.game.entities.tiles.Tile;
import com.game.gfx.ImageLoader;
import com.game.input.KeyInput;
import com.game.input.MouseInput;
import com.game.input.MouseMovement;


public class Game extends Canvas implements Runnable {
	public static int HEIGHT;
	public static int WIDTH;
	public int fps = 0;
	Dimension dimensions;
	public static boolean IS_RUNNING = false;
	public Thread gameThread;
	public Camera gameCamera;
	public Player player;
	public BufferedImage cursor = ImageLoader.loadImage(Game.HierarichalFile("/res/textures/Cursor.png"));
	public ChunkManager chunkManager;
	public Tile highlightedTile;
	public GameState gameState;
	public boolean load = false;
	public double loadingProgress;
	public GAMESTATE state = GAMESTATE.RUNNING;
	public UIManager uiManager;
	public boolean capFps = true;
	public Settings settings;
	public File jarPath = new File(Game.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	public String gamePath = Game.HierarichalFile(jarPath.getParent());

	public static int slowUpdate = 0;





	private static final long serialVersionUID = -20503614128161992L;


	public Game() {
		// setup

		try {
			settings = new Settings(this, Game.HierarichalFile("/res/data/settings.dat"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dimensions = new Dimension(WIDTH, HEIGHT);

		this.setPreferredSize(dimensions);
		this.setMinimumSize(dimensions);
		this.setMaximumSize(dimensions);

		new Window("2D Game", this);

		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput(this));
		this.addMouseMotionListener(new MouseMovement(this));
		this.setFocusable(true);

	}



	public void start() throws CloneNotSupportedException {
		try
		{
		
		state = GameState.GAMESTATE.LOADING;

		gameThread = new Thread(this);
		gameState = new GameState(this);

		uiManager = new UIManager(this);
		
		IS_RUNNING = true;

		ShowMessageBox(gamePath);
		
		

		//chunkManager.initialize();


		Assets.initialize(this);
		} catch(Throwable t)
		{
			ShowErrorMessageBox(t);
		}

		gameThread.start();

		//state = GameState.GAMESTATE.RUNNING;

		//player.setLocation(new Point(ChunkManager.Chunks.get(0).origin.x + 10, ChunkManager.Chunks.get(0).origin.y + 10));



	}

	@Override
	public void run() {
		this.requestFocus();
		
		long lastime = System.nanoTime();
		double AmountOfTicks = 60;
		double AmountOfRenders = 75;
		double rs = 1000000000 / AmountOfRenders;
		double ns = 1000000000 / AmountOfTicks;
		double delta = 0;
		double delta2 = 0;
		int frames = 0;
		int updates = 0;
		double time = System.currentTimeMillis();
		double time2 = System.currentTimeMillis();
		
		while(IS_RUNNING == true) {
			long now = System.nanoTime();
			delta += (now - lastime) / ns;
			delta2 += (now - lastime) / rs;
			lastime = now;
			
			
			if(delta >= 1) {
				try {
					update();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				updates++;
				delta--;
				if(System.currentTimeMillis() - time >= 1000) {
					System.out.println("updates:" + updates);
					time += 1000;
					updates = 0;
				}
			}
			if(!capFps || delta2 >= 1) {
				render();
				
				frames++;
				delta2--;
				if(System.currentTimeMillis() - time2 >= 1000) {
					System.out.println("fps:" + frames);
					fps = frames;
					time2 += 1000;
					frames = 0;
				}
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

		gameState.render(g);


		drawCursor(g);
		g.dispose();
		buffer.show();


	}

	private void update() throws CloneNotSupportedException
	{
		gameState.update();
		if(gameCamera != null)
			gameCamera.updateCamera();
		

	}
	private void drawCursor(Graphics g)
	{
		if(player != null)
			g.drawImage(cursor, (int) player.localMouseX-cursor.getWidth()/2, (int) player.localMouseY-cursor.getHeight()/2, null);
	}

	public void drawTileInfo(Graphics g)
	{
		if(highlightedTile != null)
		{
			highlightedTile.drawHitbox(g);
			g.setColor(Color.BLUE);
			g.fillRect(Game.WIDTH - 500, Game.HEIGHT - 20, 500, 20);
			g.setColor(Color.GREEN);
			String tileAboveType = new String();
			if(highlightedTile.tileAbove != null)
			{
				tileAboveType = tileAboveType + highlightedTile.tileAbove.type;
			} else
			{
				tileAboveType = ", tileAbove is null";
			}
			g.drawString(highlightedTile.getAnimator().sprite.name + "Type = " + highlightedTile.type + " " + tileAboveType + "Tile index = " + highlightedTile.chunkIndex, WIDTH-500, HEIGHT - 5);
		}
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

	public static boolean inBetween(Point2D val, Point2D low, Point2D high)
	{

		Rectangle2D.Double rect = new Rectangle2D.Double(low.getX(), low.getY(), high.getX(), high.getY());
		if(rect.contains(val))
		{
			return true;
		}
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

	public static double dist(double x, double y, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x-x2, 2) + Math.pow(y-y2, 2));

	}


	public static double dist(Point2D d1, Point2D d2)
	{
		return d1.distance(d2);

	}

	public static String HierarichalFile(String s)
	{

		return s.replaceAll("//", File.separator);

	}
	
	public static void ShowMessageBox(String s)
	{
		 JOptionPane.showMessageDialog(null, s);
	}
	
	public static void ShowErrorMessageBox(Throwable t)
	{
		Game.ShowMessageBox(
		        "Error! " + t.getStackTrace() + "\n " + t.getClass() + "\n " + t.getLocalizedMessage() 
		        + "\n " + t.getCause() + "\n In " + t.getStackTrace()[0].getClassName() + "At line " + t.getStackTrace()[0].getLineNumber() +
		        "\nIn method " + t.getStackTrace()[0].getMethodName() + "\n ");
	}
}
