package com.game.entities.creatures;


import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.PointerInfo;

import com.game.sprites.Animator;

public class Player extends Creature
{
	
	public boolean leftMouse, rightMouse;
	public int mouseX, mouseY;
	
	
	public Player(Animator anim, int x, int y) 
	{
		
		super(anim, 50, x, y);
		leftMouse = rightMouse = false;
		setXMagAcc(2);
		setYMagAcc(2);
		setMaxXVel(10);
		setMaxYVel(10);
		setFriction(1.5);
		
		
	}
	
	@Override
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		
		

	}
	@Override
	public void update() 
	{
		/*
		System.out.print(chunk);
		System.out.println(mouseX + "," + mouseY);
		*/
		super.update();
	}
	
	
	
	public EntityType getType()
	{
		return EntityType.PLAYER;
	}
	
	public int getMouseX()
	{
		return mouseX;
	}
	
	public int getMouseY()
	{
		return mouseY;
	}
}
