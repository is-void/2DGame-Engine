package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.game.sprites.Animator;

public class GameState extends State
{
	Game game;
	
	public enum GAMESTATE
	{
		RUNNING,
		LOADING,
		PAUSED
	}
	
	
	public GameState(Game game)
	{
		this.game = game;
	}
	

	public void render(Graphics g) 
	{
		switch(game.state)
		{
			case LOADING :
				g.setColor(Color.black);
				g.drawString("Loading" , Game.WIDTH/2 - 30, Game.HEIGHT/2 -20);
			case RUNNING :
				g.setFont(new Font("Tahoma", Font.BOLD, 11)); 
				renderProccess(g);				
				break;
				
			case PAUSED :
				
				renderProccess(g);
				
				g.setColor(new Color(0, 0, 0, 100));
				g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
				g.setFont(new Font("TimesRoman", Font.BOLD, 50)); 
				g.setColor(Color.black);
				g.drawString("PAUSED" , Game.WIDTH/2 - 100, Game.HEIGHT/2 -200);
				break;
		}
		g.setFont(new Font("Tahoma", Font.BOLD, 11)); 
		drawDebugInfo(g);
	}
	
	public void update() throws CloneNotSupportedException 
	{
		
		
		Game.slowUpdate++;
		
		if(Game.slowUpdate == 60)
		{
			
			game.chunkManager.longUpdateChunks(game);
			game.player.longUpdate();
			Game.slowUpdate = 0;
		}
		
		switch(game.state)
		{
			case LOADING :
				break;
		
			case RUNNING :
		
				Animator.updateFrames();
				game.chunkManager.updateChunks();
				
				game.player.update();
				game.gameCamera.updateCamera();
				break;
		
			case PAUSED : 
				break;
		}

	}


	public void setState(GAMESTATE st)
	{
		game.state = st;
	}
	
	public boolean isState(GAMESTATE state)
	{
		if(game.state == state)
		{
			return true;
		}
		return false;
	}
	private void renderProccess(Graphics g)
	{
		game.chunkManager.renderTiles(g);
		renderPlayer(g);
		game.chunkManager.renderCreatures(g, game);
		game.chunkManager.renderGUI(g);
		
	}
	
	private void renderPlayer(Graphics g)
	{
		game.player.drawSprite(g);
		if(game.player.displayHitbox)
			game.player.drawHitbox(g);
		if(game.player.getHitbox().contains(new Point2D.Float(game.player.localMouseX, game.player.localMouseY)))
			game.player.drawHitbox(g);
		/*
		for(Tile t : game.player.nearbyTiles)
		{
			if(t.canCollide())
			{
				t.drawHitbox(g);
			}
		}
		*/
	}
	
	private void drawDebugInfo(Graphics g)
	{
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 70, 20);
		g.setColor(Color.GREEN);
		g.drawString("FPS : " + game.fps, 0, 10);
		game.drawTileInfo(g);
		
		g.setColor(Color.BLUE);
		g.fillRect(Game.WIDTH - 100, 0, 200, 50);
		g.setColor(Color.GREEN);
		g.drawString("" + (int)(game.player.getX()) + "," + (int)(game.player.getY()) , Game.WIDTH - 100, 50);
	}
}
