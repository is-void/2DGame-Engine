package com.game.entities.creatures;


import java.awt.Graphics;

import com.game.sprites.Animator;
import com.game.sprites.Sprite;

public class Player extends Creature
{
	
	public Player(Animator anim, int x, int y) 
	{
		super(anim, x, y);
		setXMagAcc(1);
		setYMagAcc(1);
		setMaxXVel(10);
		setMaxYVel(10);
	}
	
	@Override
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		
		

	}
	
	
	
	
	public String getType()
	{
		return "Player";
	}
}
