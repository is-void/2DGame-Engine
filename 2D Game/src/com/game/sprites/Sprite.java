package com.game.sprites;

import java.awt.image.BufferedImage;

public class Sprite implements Cloneable
{
	int currentFrame;
	BufferedImage[] sprite;
	public String name;
	private int width =32;
	private int height = 32;



	public Sprite(BufferedImage[] sprite, String name, int w, int h)
	{
		this.sprite = sprite;
		currentFrame = 0;
		this.name = name;
		setWidth(w);
		setHeight(h);
	}
	/*
	public Sprite(BufferedImage[] sprite, int w, int h)
	{
		this.sprite = sprite;
		currentFrame = 0;
		this.name = "static tile";
		width = w;
		height = h;
	}
	*/

	public Sprite()
	{
		sprite = new BufferedImage[1];
		sprite[0] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		currentFrame = 0;
		name = "invisible";

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

	@Override
	public Sprite clone() throws CloneNotSupportedException
	{
		return (Sprite) super.clone();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
