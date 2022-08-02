package com.game.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

import com.game.Game;
import com.game.entities.creatures.Player;

public class MouseInput extends MouseAdapter 
{
	
	private Player player;
	
	public MouseInput(Game game)
	{
		player = game.player;
	}
	
	
	    
	public void mousePressed(MouseEvent m)
	{
		
		if(SwingUtilities.isLeftMouseButton(m))
			player.leftMouse = true;
		else if(SwingUtilities.isRightMouseButton(m))
			player.rightMouse = true;
		  
	}
	
	public void mouseReleased(MouseEvent m)
	{
		if(SwingUtilities.isLeftMouseButton(m))
			player.leftMouse = false;
		else if(SwingUtilities.isRightMouseButton(m))
			player.rightMouse = false;
	}
	
}
