package com.game.entities;
import java.awt.Graphics;
import java.util.ArrayList;

import com.game.entities.creatures.Creature;
import com.game.entities.tiles.Tile;

public class EntityManager 
{
	public static ArrayList<Entity> entities;
	public static ArrayList<Creature> creatures;
	//static ArrayList<Entity> projectiles;
	public static ArrayList<Tile> tiles;
	public static boolean showHitbox = false;
	Entity player;
	public EntityManager()
	{
		entities = new ArrayList<Entity>();
		creatures = new ArrayList<Creature>();
		tiles = new ArrayList<Tile>();

	}
	public void addEntity(Entity e)
	{
		entities.add(e);
		switch(e.getType())
		{
		case Player:
		case Creature :
			creatures.add((Creature) e);
		
			break;
		
		case Tile :
			tiles.add((Tile) e);
		default :
			break;
			
		}
	}
	
	public void removeFirstEntity(Entity e)
	{
		if(entities.size() > 1)
			entities.remove(e);
	}
	
	public void removeAllEntities()
	{
		entities.clear();
	}
	
	public void renderEntities(Graphics g)
	{
		for(Entity e : entities)
		{
			e.checkLoaded();
			if(e.isRendered())
			{
				
				e.drawSprite(g);
				if(showHitbox)
					e.drawHitbox(g);
			}
		}
	}
	
	
	public void updateEntities()
	{
		for(Entity e : entities)
		{
			e.checkLoaded();
			if(e.isRendered())
			{
				e.update();
				//count++;
			}
		}
	}
	
	public void updateHealthBars()
	{
		for(Creature c : creatures)
		{
			c.updateHealthBar();
		}
	}
	
	public void renderHealthBars(Graphics g)
	{
		for(Creature c : creatures)
		{
			c.drawHealthBar(g);
		}
	}
}
