package com.game.sprites;

import java.awt.image.BufferedImage;

public class Sprite implements Cloneable
{
	int currentFrame;
	BufferedImage[] sprite;
	String type;
	int width, height;
	
	public Sprite(BufferedImage[] sprite, String type, int w, int h)
	{
		this.sprite = sprite;
		currentFrame = 0;
		this.type = type;
		width = w;
		height = h;
	}
	
	public void nextFrame()
	{

		if(currentFrame < sprite.length-1)
		{
			currentFrame++;
		} else
		{
			currentFrame = 0;
		}
		
	}
	public int getCurrentIndex()
	{
		return currentFrame;
	}
	public BufferedImage getCurrentFrame()
	{
		return sprite[currentFrame];
	}
	
	public Sprite clone() throws CloneNotSupportedException
	{
		return (Sprite) super.clone();
	}
}
