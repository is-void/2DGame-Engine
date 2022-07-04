package com.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.*;
import com.game.entities.EntityManager;
import com.game.entities.creatures.Player;


public class KeyInput extends KeyAdapter
{
	private Player player;
	
	
	public KeyInput(Player p)
	{
		player = p;
	}
	
	public void keyPressed(KeyEvent e)
	{
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_ESCAPE)
		{
			if(Game.state == Game.State.RUNNING)
			{
				
				Game.state = Game.State.PAUSED;
			} else if(Game.state ==  Game.State.PAUSED)
			{
				Game.state = Game.State.RUNNING;
			}
		}
		
		
		//player.up pressed
		if(key == KeyEvent.VK_W)
		{
			player.up = true;
		}
		
		//player.down pressed	
		if(key == KeyEvent.VK_S)
		{
			player.down = true;	
		}
		
		//player.right pressed	
		if(key == KeyEvent.VK_D)
		{
			player.right = true;	
		}
			
		//player.left pressed	
		if(key == KeyEvent.VK_A)
		{
			player.left = true;
		}
		
		
		
			
	}
	
	public void keyReleased(KeyEvent e)
	{
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_H)
		{
			if(EntityManager.showHitbox == false)
				EntityManager.showHitbox = true;
			else
				EntityManager.showHitbox = false;
		}
		//player.up released
		
		
		if(key == KeyEvent.VK_W)
		{
			player.up = false;
		}
		
		//player.down released
		if (key ==  KeyEvent.VK_S)
		{
			player.down = false;
		}
		
		//player.left released	
		if (key == KeyEvent.VK_A)
		{
			player.left = false;
		}
		
		//player.right released
		if (key == KeyEvent.VK_D)
		{
			player.right = false;
		}
		
			
	}
}
