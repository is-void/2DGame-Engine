package com.game.display;

import java.awt.geom.Point2D;

import com.game.entities.creatures.Creature;

public class Camera
{
	public static double X, Y;
	public static Point2D COORDINATES;
	public static float zoom = 3;

	public boolean zoomIn;
	public boolean zoomOut;
	private float zoomAmount = 0.05f;

	private Creature focus;

	public Camera(Creature e)
	{
		focus = e;
	}
	public void updateCamera()
	{
		COORDINATES = focus.getOrigin();

		X = COORDINATES.getX();
		Y = COORDINATES.getY();
		checkZoom();

		/*
		System.out.println("Camera X = " + X +"/nCamera Y = " + Y );
		System.out.println("Player X = " + focus.getX() +"/nPlayer Y = " + focus.getY() );
		*/
	}

	public void changeFocus(Creature f)
	{
		focus = f;
	}

	public Point2D.Float localToGamePoint(Point2D.Float p)
	{
		Point2D.Float gamePoint = new Point2D.Float(focus.getX() + (p.x - focus.getLocalX())/zoom, focus.getY() + (p.y - focus.getLocalY())/zoom);


		return gamePoint;

	}

	public void checkZoom()
	{
		if(zoomIn && zoom + zoomAmount <=7)
		{
			zoom+=zoomAmount;
		}
		if(zoomOut && zoom - zoomAmount >= 1)
		{
			zoom-=zoomAmount;
		}

	}
}
