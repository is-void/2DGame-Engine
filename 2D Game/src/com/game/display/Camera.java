package com.game.display;

import java.awt.Dimension;
import java.awt.Point;

import com.game.entities.creatures.Creature;

public class Camera 
{
	public static int X, Y;
	public static Point COORDINATES;
	
	private Creature focus;
	
	public Camera(Creature e)
	{
		focus = e;
	}
	public void updateCamera()
	{
		COORDINATES = focus.getOrigin();
		
		X = COORDINATES.x;
		Y = COORDINATES.y;
		/*
		System.out.println("Camera X = " + X +"/nCamera Y = " + Y );
		System.out.println("Player X = " + focus.getX() +"/nPlayer Y = " + focus.getY() );
		*/
	}
	
	public void changeFocus(Creature f)
	{
		focus = f;
	}
}
