package com.game.sprites;


import java.awt.Graphics;
import java.util.ArrayList;

import com.game.display.Camera;
import com.game.entities.Entity;



public class Animator 
{
	
	private int height, width;
	public Sprite idleAnim;
	public Sprite sprite; 
	private int index;
	private Entity owner;
	private static int frameCounter = 0;
	private boolean inAttackAnim;
	public int frameRate = 10;
	public ArrayList<Sprite> overlays = new ArrayList<Sprite>();
	public int maxOverlayAmount = 4;
	
	public Animator(Sprite spr)
	{
		
		height = spr.height;
		width = spr.width;
		sprite = spr;
	}
	public Animator(Sprite spr, Entity owner)
	{
		
		height = spr.height;
		width = spr.width;
		sprite = spr;
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
		if(sprite.sprite.length > 1)
			if(frameCounter / frameRate == 60 / frameRate)
			{
				sprite.nextFrame();
			}
	}
	
	public void setOwner(Entity e)
	{
		owner = e;
	}
	public void draw(Graphics g)
	{
		
		
		g.drawImage(getSprite().getCurrentFrame(), owner.getLocalX(), (int) owner.getLocalY(), (int) Math.ceil(width * Camera.zoom),   (int) (Math.ceil(height * Camera.zoom)), null);
		for(int s = overlays.size()-1; s > -1; s--)
		{
			g.drawImage(overlays.get(s).getCurrentFrame(), owner.getLocalX(), (int) owner.getLocalY(), (int) Math.ceil(width * Camera.zoom),   (int) (Math.ceil(height * Camera.zoom)), null);
		}
		
		
	}
	
	public void addOverlay(Sprite spr)
	{
		if(maxOverlayAmount > overlays.size())
		{
			overlays.add(spr);
		}
	}
	
	public void setSprite(Sprite spr)
	{
		sprite = spr;
		
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getHeight() 
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public Animator clone(Entity owner)
	{
		return new Animator(sprite, owner);
		
	}
}
