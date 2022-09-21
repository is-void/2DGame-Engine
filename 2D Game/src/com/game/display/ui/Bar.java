package com.game.display.ui;

import java.awt.Color;
import java.awt.Graphics;

import com.game.Game;
import com.game.entities.creatures.Creature;

public class Bar implements UIElement
{
	private float x, y;
	private int width, height;
	private Creature target;
	private double progress;
	private boolean canUpdate;
	private boolean isStatic;
	private Game game;
	public static enum BarType
	{
		HEALTH,
		ABILITY,
		LOADING
	}
	BarType type;
	public Bar(Game game, int x, int y, int wid, int hei, BarType t)
	{
		if(t == BarType.LOADING)
		{
			this.game = game;
			canUpdate = true;
			this.x = Game.WIDTH/2 - wid/2;
			this.y = Game.HEIGHT/2 + 100;
			width = wid;
			height = hei;
			type = t;
			isStatic = true;
			init();
		}
	}

	public Bar(Game game, int wid, int hei, Creature tar, BarType t)
	{
		this.game = game;
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
			break;

		case LOADING :
			progress = game.loadingProgress;
			break;
		default:
			break;

		}
	}

	@Override
	public void update()
	{
		if(canUpdate)
		{

			switch(type)
			{
				case HEALTH :
					if(target.isRendered())
					{
						progress = target.getHealth()/target.getMaxHealth();
						if(progress > 1)
						progress = 1.0;
					}
					if(!isStatic)
					{
						x = target.getLocalX()-(width/2) + (target.getAnimator().getWidth()/2);
						y = target.getLocalY()-50;
					}
					break;
				case ABILITY:
					break;
				case LOADING:
					progress = game.loadingProgress;
					break;
				default:
					break;
			}
		}



	}


	public void setProgress(double p)
	{
		progress = p;
	}

	public double getProgress()
	{
		return progress;
	}

	@Override
	public void render(Graphics g)
	{
		if(canUpdate)
		{
			switch(type)
			{
			case HEALTH :
				g.setColor(Color.BLACK);
				g.drawRect((int) Math.floor(x-1), (int)Math.floor(y-1), width+1, height+1);
				g.setColor(Color.RED);
				g.fillRect((int)Math.floor(x), (int) Math.floor(y), width, height);
				g.setColor(Color.GREEN);
				g.fillRect((int)Math.floor(x), (int) Math.floor(y), (int) (width*progress), height);
				break;
			case ABILITY:
				break;
			case LOADING:
				g.setColor(Color.BLACK);
				g.drawRect((int)Math.floor(x-1), (int) Math.floor(y-1), width+1, height+1);
				g.setColor(Color.RED);
				g.fillRect((int)Math.floor(x), (int) Math.floor(y), width, height);
				g.setColor(Color.GREEN);
				g.fillRect((int)Math.floor(x), (int) Math.floor(y), (int) (width*progress), height);

				break;
			default:
				break;
			}
		}
	}
}
