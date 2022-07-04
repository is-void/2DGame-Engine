package com.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.entities.EntityManager;
import com.game.entities.creatures.Player;


public class KeyInput extends KeyAdapter
{
	private Player player;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	public KeyInput(Player p)
	{
		player = p;
	}
	
	public void keyPressed(KeyEvent e)
	{
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP)
		{
			if(!player.getCanMoveUp())
				player.setCanMoveUp(true);
			else
				player.setCanMoveUp(false);
		}
		//UP pressed
		if(key == KeyEvent.VK_W)
		{
			up = true;
		}
		
		//DOWN pressed	
		if(key == KeyEvent.VK_S)
		{
			down = true;	
		}
		
		//RIGHT pressed	
		if(key == KeyEvent.VK_D)
		{
			right = true;	
		}
			
		//LEFT pressed	
		if(key == KeyEvent.VK_A)
		{
			left = true;
		}
		
		if(up && down)
			player.slowDownY();
		else
			if(up || down)
			{
				if(up)
					player.moveUp(); 
				if(down)
					player.moveDown();
			} else
				player.slowDownY();
		
		if(left && right)
			player.slowDownX();
		else
			if(left || right)
			{
				if(left)
					player.moveLeft();
				else if(right)
					player.moveRight();
			} else
				player.slowDownX();
		
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
		//UP released
		if(key == KeyEvent.VK_W)
		{
			up = false;
		}
		
		//Down released
		if (key ==  KeyEvent.VK_S)
		{
			down = false;
		}
		
		//LEFT released	
		if (key == KeyEvent.VK_A)
		{
			left = false;
		}
		
		//RIGHT released
		if (key == KeyEvent.VK_D)
		{
			right = false;
		}
		
		if(!up && !down)
			player.slowDownY(); 
		
		if(!left && !right)
			player.slowDownX();  
			
	}
}
