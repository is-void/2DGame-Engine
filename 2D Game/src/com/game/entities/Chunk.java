package com.game.entities;
import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import com.game.Game;
import com.game.Assets;
import com.game.entities.Entity.EntityType;
import com.game.entities.creatures.Creature;
import com.game.entities.tiles.Tile;

public class Chunk 
{
	//public ArrayList<Entity> entities;
	public ArrayList<Creature> creatures;
	//static ArrayList<Entity> projectiles;
	public ArrayList<Tile> tilesOld;
	public Tile[] tiles = new Tile[64];
	
	public static boolean showHitbox = false;
	public Point origin;
	private File chunkData;
	public static final int WIDTH = 256;
	public static final int HEIGHT = 256;
	
	ArrayList<Tile> frontTiles;
	ArrayList<Tile> backTiles;
	
	Entity player;
	public Chunk(int x, int y, String path) throws FileNotFoundException
	{
		//entities = new ArrayList<Entity>();
		creatures = new ArrayList<Creature>();
		tilesOld = new ArrayList<Tile>();
		frontTiles = new ArrayList<Tile>();
		backTiles = new ArrayList<Tile>();
		chunkData = new File(path);
		
		origin = new Point(x+WIDTH/2, y+HEIGHT/2);
		
		loadFile();
		System.out.print("Created new chunk at " + x + ", " + y + " at filepath " + path);
		
	}
	
	public void loadFile() throws FileNotFoundException
	{
		Scanner s;
		s = new Scanner(chunkData);
		s.nextLine();

		for(int lineIndex = 0; lineIndex <= 7; lineIndex++)
		{
			String line = s.next();
			for(int index = 0; index < line.length(); index++)
			{
				System.out.println("------ tiles [" + (index + lineIndex*8) + "] is being set. Char = " + (char)(line.charAt(index)) );

				switch(line.charAt(index))
				{
					case 'G' :
						tiles[index + lineIndex * 8] = Assets.tileID.get("grass_1").clone();
						
						break;
					case 'P' :
						tiles[index + lineIndex * 8] = Assets.tileID.get("path_1").clone();
						
						break;
					case 'B' :
						tiles[index + lineIndex * 8] = Assets.tileID.get("brick_1").clone();
						tiles[index + lineIndex * 8].doCollision(true);
						break;
					case 'W' :
						tiles[index + lineIndex * 8] = Assets.tileID.get("water_1").clone();
						tiles[index + lineIndex * 8].doCollision(true);
						break;
					case 'S' :
						tiles[index + lineIndex * 8] = Assets.tileID.get("grasspath_left_1").clone();
						break;
					default :
						break;
						
				}
				
				tiles[index + lineIndex * 8].setLocation(new Point(this.origin.x + Tile.WIDTH * index, this.origin.y + Tile.HEIGHT * lineIndex));
				
			}
		}
		
		
		s.close();
		
		
		for(Tile t : tiles)
		{
			if(t.canCollide())
			{
				frontTiles.add(t);
			} else
			{
				backTiles.add(t);
			}

		}
		
		
		
	}
	
	public boolean isNearPlayer()
	{
		if(Game.dist(origin, Game.player.getOrigin()) < (Math.sqrt(Math.pow(Game.WIDTH, 2) + Math.pow(Game.HEIGHT, 2) )))
			return true;
		return false;
			
	}
	/*
	public void sortEntities()
	{
		for(Entity e : entities)
		{
			switch(e.getType())
			{
			case PLAYER:
				entities.add(0, e);
;			case TILE :
				entities.add(tilesOld.size()+1, e);
				break;
				
			case CREATURE :
				entities.add(e);
			default:
				break;
			}
		
		}
	}
	*/
	
	public void longUpdate()
	{
		for(Creature c : creatures)
		{
			c.longUpdate();
		}
	}
	
	public void renderFrontTiles(Graphics g)
	{
		for(Tile t : frontTiles)
		{
			if(t == null)
				System.out.print("Missing tile");
			if(t.nearCreature(Game.player))
				t.drawSprite(g);
		}
	}
	
	public void renderBackTiles(Graphics g)
	{
		for(Tile t : backTiles)
		{
			if(t.nearCreature(Game.player))
				t.drawSprite(g);
		}
	}
	
	public void renderTiles(Graphics g)
	{
		for(Tile t : tiles  )
		{
			t.drawSprite(g);
		}
	}
	
	public void addCreature(Entity e)
	{
		
		switch(e.getType())
		{
		case PLAYER:
			break;
		case CREATURE :
			creatures.add((Creature) e);
		
			break;
		default :
			break;
			
		}
	}
	
	public void removeFirstCreature(Entity e)
	{
		if(creatures.size() > 1)
			creatures.remove(e);
	}
	
	public void clear()
	{
		creatures.clear();
		
	}
	
	
	public void renderCreatures(Graphics g)
	{
		
		for(Creature c : creatures)
		{
			c.checkLoaded();
			if(c.isRendered())
			{
				
				c.drawSprite(g);
				if(showHitbox)
					c.drawHitbox(g);
				if(c.getHitbox().contains(new Point(Game.player.mouseX, Game.player.mouseY)))
					c.drawHitbox(g);
			}
		}
	}
	
	public void renderTiles()
	{
		
	}
	
	
	public void updateTiles()
	{
		for(Tile t : tiles)
		{
			t.checkLoaded();
			if(t.isRendered())
			{
				t.update();
				//count++;
			}
		}
	}
	
	public void updateCreatures()
	{
		for(Creature c : creatures)
		{
			c.checkLoaded();
			if(c.isRendered())
			{
				c.update();
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
