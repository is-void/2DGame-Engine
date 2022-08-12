package com.game.sprites;


import java.awt.Graphics;
import java.util.ArrayList;

import com.game.display.Camera;
import com.game.display.ui.UIElement;
import com.game.display.ui.UIWidget;
import com.game.entities.Entity;



public class Animator 
{
	
	private int height, width;
	public Sprite idleAnim;
	public Sprite sprite; 
	private int index;
	private Object owner;
	private static int frameCounter = 0;
	private boolean inAttackAnim;
	public int frameRate = 10;
	public ArrayList<Sprite> overlays = new ArrayList<Sprite>();
	public int maxOverlayAmount = 4;
	public int scale = 1;
	
	enum ObjectType
	{
		ENTITY,
		UIWIDGET,
		OTHER
	}
	
	ObjectType objTyp;
	
	public Animator(Sprite spr)
	{
		
		height = spr.getHeight();
		width = spr.getWidth();
		sprite = spr;
	}
	
	public Animator(Sprite spr, Object owner)
	{
		height = spr.getHeight();
		width = spr.getWidth();
		sprite = spr;
		setOwner(owner);
		
		
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
	
	public void setOwner(Object e)
	{
		owner = e;
		switch(e.getClass().getSimpleName())
		{
			case "ConnecterTile":
			case "Tile" :
			case "Entity":
			case "Creature":
			case "Player":
				objTyp = ObjectType.ENTITY;
				break;
			
			case "UIWidget" :
			case "Button" :
				objTyp = ObjectType.UIWIDGET;
				break;
			default :
				break;
		}
	}
	
	public void draw(Graphics g)
	{
		
		switch(objTyp)
		{
		case ENTITY:
			g.drawImage(getSprite().getCurrentFrame(), ((Entity) owner).getLocalX(), (int) ((Entity) owner).getLocalY(), (int) Math.ceil(width * scale * Camera.zoom),   (int) (Math.ceil(height * scale * Camera.zoom)), null);
			for(int s = overlays.size()-1; s > -1; s--)
			{
				g.drawImage(overlays.get(s).getCurrentFrame(), ((Entity) owner).getLocalX(), (int) ((Entity) owner).getLocalY(), (int) Math.ceil(width * scale * Camera.zoom),   (int) (Math.ceil(height * scale * Camera.zoom)), null);
			}
			break;
		case OTHER:
			break;
		case UIWIDGET:
			
			g.drawImage(getSprite().getCurrentFrame(), ((UIWidget) owner).x, ((UIWidget) owner).y, width  * scale, height  * scale, null);
			for(int s = overlays.size()-1; s > -1; s--)
			{
				g.drawImage(overlays.get(s).getCurrentFrame(), ((UIWidget) owner).x, ((UIWidget) owner).y, width * scale, height * scale, null);
			}
			break;
		default:
			break;
		
		}
		
		
		
	}
	
	public void addOverlay(Sprite spr)
	{
		if(maxOverlayAmount > overlays.size())
		{
			overlays.add(spr);
		}
	}
	
	public void clearOverlays(Sprite spr)
	{
		overlays.clear();
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
