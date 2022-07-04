package com.game.entities.tiles;

import java.awt.Graphics;

import com.game.entities.Entity;
import com.game.sprites.Animator;
import com.game.sprites.Sprite;


public class Tile extends Entity
{
	
	public Tile(Animator anim, int x, int y)
	{
		super(anim, x, y);
	}
	public void drawSprite(Graphics g) 
	{
		super.drawSprite(g);
		
	}
	
	public EntityType getType()
	{
		return EntityType.Tile;
	}

}
