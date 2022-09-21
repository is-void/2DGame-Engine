package com.game.display;

import java.awt.geom.Point2D;

import com.game.entities.Entity;

public class Camera
{
	public float X, Y;
	private Point2D.Float COORDINATES;
	public float zoom = 3;

	public boolean zoomIn;
	public boolean zoomOut;
	private float zoomAmount = 0.05f;

	private Entity focus;

	public Camera(Entity e)
	{
		focus = e;
	}
	public void updateCamera()
	{
		checkZoom();
		COORDINATES = focus.getOrigin();
		
		X = (float) (COORDINATES.getX());
		Y = (float) (COORDINATES.getY());
		

		/*
		System.out.println("Camera X = " + X +"/nCamera Y = " + Y );
		System.out.println("Player X = " + focus.getX() +"/nPlayer Y = " + focus.getY() );
		*/
	}

	public void changeFocus(Entity f)
	{
		focus = f;
	}
	
	public Entity getFocus() {
		return focus;
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
