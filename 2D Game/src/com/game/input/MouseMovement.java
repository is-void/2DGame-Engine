package com.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.game.entities.creatures.Player;

public class MouseMovement extends MouseMotionAdapter
{
	Player player;
	
	public MouseMovement(Player p)
	{
		player = p;
	}
	
	public void mouseDragged(MouseEvent e)
	{
		player.mouseX = e.getX();
		player.mouseY = e.getY();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		player.mouseX = e.getX();
		player.mouseY = e.getY();
	}
}
