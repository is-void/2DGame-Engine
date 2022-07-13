package com.game.display.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.game.entities.creatures.Creature;

public class Bar 
{
	private int x, y;
	private int width, height;
	private Creature target;
	private double progress;
	private boolean canUpdate;
	private boolean isStatic;
	public static enum BarType
	{
		HEALTH,
		ABILITY,
		LOADING
	}
	BarType type;
	
	public Bar(int wid, int hei, Creature tar, BarType t)
	{
		canUpdate = true;
		width = wid;
		height = hei;
		target = tar;
		type = t;
		isStatic = false;
		init();
		
	}
	
	private void init() 
	{
		
		switch(type)
		{
		case HEALTH :
			if(target != null)
			{
				
				progress  = target.getHealth()/target.getMaxHealth();
				x = target.getLocalX();
				y = target.getLocalY();
			}
		default:
			break;
			
		}
	}
	
	public void update()
	{
		if(canUpdate)
		{
			if(target.isRendered())
			{
				
				switch(type)
				{
				case HEALTH :
					progress  = target.getHealth()/target.getMaxHealth();
					if(progress > 1)
						progress = 1.0;
					break;
				case ABILITY:
					break;
				case LOADING:
					break;
				default:
					break;
				}
				if(!isStatic)
				{
					x = target.getLocalX()-(width/2) + (target.getAnimator().getWidth()/2);
					y = target.getLocalY()-50;
				}
				
			} else
			{
				
			}
		}
	}
	
	public void render(Graphics g)
	{
		if(canUpdate && target.isRendered())
		{
			switch(type)
			{
			case HEALTH :
				g.setColor(Color.BLACK);
				g.drawRect(x-1, y-1, width+1, height+1);
				g.setColor(Color.RED);
				g.fillRect(x, y, width, height);
				g.setColor(Color.GREEN);
				g.fillRect(x, y, (int) (width*progress), height);
				break;
			case ABILITY:
				break;
			case LOADING:
				break;
			default:
				break;
			}
		}
	}
}
