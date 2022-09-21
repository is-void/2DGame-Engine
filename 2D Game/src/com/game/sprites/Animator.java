package com.game.sprites;


import java.awt.Graphics;
import java.util.ArrayList;

import com.game.Game;
import com.game.display.Camera;
import com.game.display.ui.UIWidget;
import com.game.entities.Entity;



public class Animator
{

	private int height, width;
	public Sprite idleAnim;
	public Sprite sprite;
	private Object owner;
	private static int frameCounter = 0;
	public int frameRate = 10;
	public ArrayList<Sprite> overlays = new ArrayList<>();
	public int maxOverlayAmount = 4;
	public int scale = 1;
	private Game game;

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
				game = ((Entity) e).game;
				break;

			case "UIWidget" :
			case "Button" :
				objTyp = ObjectType.UIWIDGET;
				game = ((UIWidget) e).game;
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
			g.drawImage(getSprite().getCurrentFrame(), (int) Math.floor(((Entity) owner).getLocalX()), (int) Math.floor(((Entity) owner).getLocalY()), (int) Math.floor(width * scale * game.gameCamera.zoom),   (int) Math.floor((height * scale * game.gameCamera.zoom)), null);
			for(int s = overlays.size()-1; s > -1; s--)
			{
				g.drawImage(overlays.get(s).getCurrentFrame(), (int) Math.floor(((Entity) owner).getLocalX()) , (int) Math.floor(((Entity) owner).getLocalY() ), (int) (width * scale * game.gameCamera.zoom),   (int) ((height * scale * game.gameCamera.zoom)), null);
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
