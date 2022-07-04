package com.game.sprites;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.entities.Entity;
import com.game.gfx.ImageLoader;



public class Animator 
{
	
	private int height, width;
	public Sprite idleAnim;
	private Sprite currentSprite; 
	private int index;
	private Entity owner;
	private static int frameCounter = 0;
	
	
	public Animator(Sprite spr)
	{
		
		height = spr.height;
		width = spr.width;
		currentSprite = spr;
		System.out.print(owner);
	}
	public static void updateFrames()
	{

		if(frameCounter < 60)
		{
			frameCounter++;
		} else
			frameCounter = 0;
		
	}
	public void update()
	{
		if(frameCounter / 30 == 2)
			currentSprite.nextFrame();
	}
	
	public void setOwner(Entity e)
	{
		owner = e;
	}
	public void draw(Graphics g)
	{
		
		g.drawImage(getCurrentSprite().getCurrentFrame(), owner.getLocalX(), owner.getLocalY(), width, height, null);
		
		
		
	}
	public void setSprite(Sprite spr)
	{
		currentSprite = spr;
		System.out.print("set Sprite");
	}
	
	public Sprite getCurrentSprite() {
		return currentSprite;
	}
	
	public int getHeight() 
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
}
