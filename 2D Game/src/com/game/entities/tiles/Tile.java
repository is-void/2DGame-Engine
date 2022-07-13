package com.game.entities.tiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import com.game.Game;
import com.game.entities.Entity;
import com.game.sprites.Animator;
import com.game.sprites.Sprite;


public class Tile extends Entity
{
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	public static int check = 0;
	public Tile(Animator anim, boolean collison)
	{
		super(anim, 0, 0, collison);
		anim.setOwner(this);
		//isStatic = true;
		
	}
	
	public Tile(Animator anim, boolean collison, int x, int y)
	{
		super(anim, x, y, collison);
		anim.setOwner(this);
		//isStatic = true;
		
	}
	
	public void drawSprite(Graphics g) 
	{
		super.drawSprite(g);
		g.setColor(Color.red);
		/*
		g.drawLine(getLocalX(), getLocalY(), Game.player.getLocalX(), Game.player.getLocalY());
		*/
		if(getHitbox().contains(new Point(Game.player.mouseX, Game.player.mouseY)))
			drawHitbox(g);
		isStatic = true;
		
	}
	
	public void update() {
		super.update();
		
	}
	public EntityType getType()
	{
		return EntityType.TILE;
	}
	
	public Tile clone()
	{
		return new Tile(new Animator(getAnimator().getSprite()), isStatic);
	}
	
}
