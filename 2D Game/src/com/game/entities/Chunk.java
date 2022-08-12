package com.game.entities;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.game.Game;
import com.game.Assets;
import com.game.entities.creatures.Creature;
import com.game.entities.creatures.Player;
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
	public static final int WIDTH = Tile.WIDTH * 8;
	public static final int HEIGHT = Tile.HEIGHT * 8;
	private Game game;
	private Chunk chunkAbove, chunkBelow, chunkLeft, chunkRight;
	
	
	ArrayList<Tile> frontTiles;
	ArrayList<Tile> backTiles;
	
	Entity player;
	public Chunk(Game game, int x, int y, String path) throws FileNotFoundException
	{
		this.game = game;
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
			String line = s.nextLine();
			
			int spot = 0;
			for(int tileIndex = 0; tileIndex < 8; tileIndex++)
			{
				if(tileIndex == 7)
				{
					int id = Integer.parseInt(line.substring(spot));
					tiles[tileIndex + lineIndex * 8] = Assets.tileID.get(id).clone();
					
				} else
				{
					int id = -1;
					int space = line.indexOf(" ", spot);
					id = Integer.parseInt(line.substring(spot, space));
					tiles[tileIndex + lineIndex * 8] = Assets.tileID.get(id).clone();
					spot = space+1;
				}
				System.out.print("for line " + lineIndex + "at index " + tileIndex);
				tiles[tileIndex + lineIndex * 8].setLocation(new Point2D.Float(this.origin.x + Tile.WIDTH * tileIndex, this.origin.y + Tile.HEIGHT * lineIndex));
				System.out.print(tiles[tileIndex + lineIndex * 8].x + ", " + tiles[tileIndex + lineIndex * 8].y );
				tiles[tileIndex + lineIndex * 8].chunkIndex = tileIndex+lineIndex*8;
				tiles[tileIndex + lineIndex * 8].setChunk(this);
				

			}
		}
		
		
		s.close();
		
		
		
		
		
		
	}
	
	public void checkTileDependent() throws CloneNotSupportedException
	{
		
		for(Tile t : tiles)
		{
			t.check();
		}
		for(Tile t : tiles)
		{
			t.check();
		}
		
		
		
	}
	
	public boolean isNearPlayer(Player player)
	{
		if(Game.dist(origin, player.getOrigin()) < (Math.sqrt(Math.pow(Game.WIDTH, 2) + Math.pow(Game.HEIGHT, 2) )))
			return true;
		return false;
			
	}
	
	public void chunkUpdate() throws CloneNotSupportedException
	{
		checkTileDependent();
	}
	
	public Chunk chunkAbove()
	{
		if(chunkAbove != null)
			return chunkAbove;
		Point p = new Point(origin.x, origin.y - Chunk.HEIGHT);
		for(Chunk c : game.chunkManager.Chunks)
		{
			if(c.origin.equals(p))
			{
				chunkAbove = c;
				return c;
			}
		}
		return null;
	}
	
	public Chunk chunkBelow()
	{
		if(chunkBelow != null)
			return chunkBelow;
		Point p = new Point(origin.x, origin.y + Chunk.HEIGHT);
		for(Chunk c : game.chunkManager.Chunks)
		{
			if(c.origin.equals(p))
			{
				chunkBelow = c;
				return c;
			}
		}
		return null;
	}
	
	public Chunk chunkRight()
	{
		if(chunkRight != null)
			return chunkRight;
		Point p = new Point(origin.x + Chunk.WIDTH, origin.y);
		for(Chunk c : game.chunkManager.Chunks)
		{
			if(c.origin.equals(p))
			{
				chunkRight = c;
				return c;
			}
		}
		return null;
	}
	
	public Chunk chunkLeft()
	{
		if(chunkLeft != null)
			return chunkLeft;
		Point p = new Point(origin.x - Chunk.WIDTH, origin.y);
		for(Chunk c : game.chunkManager.Chunks)
		{
			if(c.origin.equals(p))
			{
				chunkLeft = c;
				return c;
			}
		}
		return null;
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
	
	public void longUpdate() throws CloneNotSupportedException
	{
		
		for(Creature c : creatures)
		{
			c.longUpdate();
		}
		
	}
	
	public void renderFrontTiles(Graphics g, Player player)
	{
		for(Tile t : frontTiles)
		{
			if(t == null)
				System.out.print("Missing tile");
			if(t.nearCreature(player))
				t.drawSprite(g);
		}
	}
	
	public void renderBackTiles(Graphics g, Player player)
	{
		for(Tile t : backTiles)
		{
			if(t.nearCreature(player))
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
	
	
	public void renderCreatures(Graphics g, Player player)
	{
		
		for(Creature c : creatures)
		{
			
			c.checkLoaded();
			if(c.isRendered())
			{
				
				c.drawSprite(g);
				c.drawHealthBar(g);
				if(showHitbox)
					c.drawHitbox(g);
				if(c.getHitbox().contains(new Point2D.Double(player.mouseX, player.mouseY)))
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
