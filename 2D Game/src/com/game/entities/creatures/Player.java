package com.game.entities.creatures;


import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.game.Game;
import com.game.sprites.Animator;

public class Player extends Creature
{

	public boolean leftMouse, rightMouse;
	public float localMouseX;
	public float localMouseY;
	public float mouseX, mouseY;


	public Player(Game game, Animator anim, float x, float y)
	{

		super(game, anim, 50, x, y);
		leftMouse = rightMouse = false;
		setXMagAcc(0.8f);
		setYMagAcc(0.8f);
		setMaxXVel(3);
		setMaxYVel(3);
		setFriction(1.5f);


	}

	@Override
	public void drawSprite(Graphics g)
	{
		super.drawSprite(g);
		super.drawHitbox(g);


	}
	@Override
	public void update()
	{

		Point2D.Float p = game.gameCamera.localToGamePoint(new Point2D.Float(localMouseX, localMouseY));

		mouseX = p.x;
		mouseY = p.y;

		super.update();
	}



	@Override
	public EntityType getType()
	{
		return EntityType.PLAYER;
	}

	public float getMouseX()
	{
		return mouseX;
	}

	public float getMouseY()
	{
		return mouseY;

	}
	public float getLocalMouseX()
	{
		return localMouseX;
	}

	public float getLocalMouseY()
	{
		return localMouseY;
	}
}
