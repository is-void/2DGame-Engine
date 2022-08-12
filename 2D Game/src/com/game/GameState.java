package com.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.game.display.ui.UIWidget;
import com.game.sprites.Animator;

public class GameState extends State
{
	Game game;
	
	public enum GAMESTATE
	{
		RUNNING,
		LOADING,
		PAUSED,
		START
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
				break;
				
				
			case RUNNING :
				g.setFont(new Font("Tahoma", Font.BOLD, 11)); 
				renderProccess(g);
				
				drawDebugInfo(g);
				
				break;
				
			case PAUSED :
				
				renderProccess(g);
				
				
				drawDebugInfo(g);
				break;
				
			case START :
				break;
		}
		
		game.uiManager.render(g);
		 
		
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
			{
				if(game.loadingProgress == 1)
				{
					setState(GameState.GAMESTATE.START);
				}
			}
				break;
				
			case START :
			{
				break;
			}
				
			case RUNNING :
		
				Animator.updateFrames();
				game.chunkManager.updateChunks();
				
				game.player.update();
				game.gameCamera.updateCamera();
				break;
		
			case PAUSED : 
				break;
		default:
			break;
		}
		if(game.uiManager != null && game.uiManager.isLoaded)
		{
			
			game.uiManager.checkState();
			game.uiManager.update();
		}

	}


	public void setState(GAMESTATE st)
	{
		game.state = st;
	}
	
	public void setStateToRunning()
	{
		game.chunkManager.creatures.add(game.player);
		game.load = false;
		game.gameCamera.changeFocus(game.player);
		game.chunkManager.initialize();
		
		try {
			game.chunkManager.checkLoadedChunks(game.player);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		game.state = GAMESTATE.RUNNING;
		
	}
	
	public static void setStateToRunning(UIWidget widget)
	{
		Game g = widget.game;
		g.chunkManager.creatures.add(g.player);
		g.load = false;
		g.gameCamera.changeFocus(g.player);
		g.chunkManager.initialize();
		
		try {
			g.chunkManager.checkLoadedChunks(g.player);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		g.state = GAMESTATE.RUNNING;
		
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
		g.setFont(new Font("Tahoma", Font.BOLD, 11));
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
