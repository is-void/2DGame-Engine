package com.game.entities;

import java.awt.Graphics;

import com.game.entities.tiles.Tile;
import com.game.sprites.Animator;


public class TestEntity extends Tile 
{
	public TestEntity(Animator anim, int x, int y) 
	{
		super(anim, x, y);
	}
	
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		
		
	}
	

}
