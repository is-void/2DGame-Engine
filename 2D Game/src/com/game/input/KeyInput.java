package com.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.*;
import com.game.GameState.GAMESTATE;
import com.game.entities.Chunk;


public class KeyInput extends KeyAdapter
{
	private Game game;
	
	
	public KeyInput(Game game)
	{
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e)
	{
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE)
		{
			if(game.state == GAMESTATE.RUNNING)
			{
				game.state = GAMESTATE.PAUSED;
				
			} else if(game.state == GAMESTATE.PAUSED)
			{
				game.state = GAMESTATE.RUNNING;
			}
		}
		
		
		//player.up pressed
		if(key == KeyEvent.VK_W)
		{
			game.player.up = true;
		}
		
		//player.down pressed	
		if(key == KeyEvent.VK_S)
		{
			game.player.down = true;	
		}
		
		//player.right pressed	
		if(key == KeyEvent.VK_D)
		{
			game.player.right = true;	
		}
			
		//player.left pressed	
		if(key == KeyEvent.VK_A)
		{
			game.player.left = true;
		}
		
		if(key == KeyEvent.VK_UP)
			game.gameCamera.zoomIn = true;
		
		
		if(key == KeyEvent.VK_DOWN)
			game.gameCamera.zoomOut = true;
		
		
		
			
	}
	
	public void keyReleased(KeyEvent e)
	{
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_H)
		{
			if(Chunk.showHitbox == false)
				Chunk.showHitbox = true;
			else
				Chunk.showHitbox = false;
		}
		//player.up released
		
		
		if(key == KeyEvent.VK_W)
		{
			game.player.up = false;
		}
		
		//player.down released
		if (key ==  KeyEvent.VK_S)
		{
			game.player.down = false;
		}
		
		//player.left released	
		if (key == KeyEvent.VK_A)
		{
			game.player.left = false;
		}
		
		//player.right released
		if (key == KeyEvent.VK_D)
		{
			game.player.right = false;
		}
		
		if(key == KeyEvent.VK_UP)
			game.gameCamera.zoomIn = false;
		
		
		if(key == KeyEvent.VK_DOWN)
			game.gameCamera.zoomOut = false;
		
		
			
	}
}
