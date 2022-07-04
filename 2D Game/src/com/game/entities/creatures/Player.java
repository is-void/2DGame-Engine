package com.game.entities.creatures;


import java.awt.Graphics;

import com.game.sprites.Animator;

public class Player extends Creature
{
	
	public Player(Animator anim, int x, int y) 
	{
		super(anim, 50, x, y);
		setXMagAcc(2);
		setYMagAcc(2);
		setMaxXVel(10);
		setMaxYVel(10);
		setFriction(2);
		
		
	}
	
	@Override
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		

	}
	
	
	
	
	public EntityType getType()
	{
		return EntityType.Player;
	}
}
