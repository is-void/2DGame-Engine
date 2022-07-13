package com.game;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import com.game.entities.Chunk;
import com.game.entities.creatures.Creature;
import com.game.entities.tiles.Tile;

public class ChunkManager 
{
	public static ArrayList<Chunk> Chunks = new ArrayList<Chunk>();
	public static ArrayList<Chunk> LoadedChunks = new ArrayList<Chunk>();
	
	public static ArrayList<Creature> creatures = new ArrayList<Creature>();
	
	
	public static void initialize()
	{
		
		File directory;
		try {
			directory = new File(ChunkManager.class.getResource("/chunks").toURI());
			if(directory.isDirectory())
				setChunks(directory.listFiles());
			else
				System.out.print(directory.getPath() + " is not a directory");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void setChunks(File[] files)
	{
		Chunks.clear();
		for(File chunk : files)
		{
			try {
				Scanner s = new Scanner(chunk);
				Chunks.add(new Chunk(s.nextInt(), s.nextInt(), chunk.getPath()));
				s.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void checkLoadedChunks()
	{
		ArrayList<Chunk> temp = new ArrayList<Chunk>();
		ArrayList<Tile> tempTiles = new ArrayList<Tile>();
		for(Chunk c : Chunks)
		{
			if(c.isNearPlayer())
			{
				temp.add(c);
				AssignCreatureToChunk(c);
			}
				
		}
		
		for(Chunk c : temp)
		{
			for(Tile t : c.tiles)
			{
				if(t.nearCreature(Game.player))
					tempTiles.add(t);
			}
		}
		
		LoadedChunks = temp;
		
	}
	public static void renderTiles(Graphics g)
	{
		for(Chunk c : LoadedChunks)
			c.renderTiles(g);
	}
	/*
	public static void renderBacktiles(Graphics g)
	{
		for(Chunk c : LoadedChunks)
		{
			c.renderBackTiles(g);
		}
	}
	public static void renderFronttiles(Graphics g)
	{
		for(Chunk c : LoadedChunks)
		{
			c.renderFrontTiles(g);
		}
	}
	*/
	public static void renderCreatures(Graphics g)
	{
		for(Chunk c : LoadedChunks)
		{
			c.renderCreatures(g);
		}
	}
	
	public static void renderGUI(Graphics g)
	{
		for(Chunk c : LoadedChunks)
			c.renderHealthBars(g);
	}
	
	public static void updateChunks()
	{
		for(Chunk c : LoadedChunks)
		{
			c.updateTiles();
			c.updateCreatures();
			c.updateHealthBars();
		}
	}
	
	public static void longUpdateChunks()
	{
		for(Chunk  c : LoadedChunks)
		{
			
			c.longUpdate();
			AssignCreatureToChunk(c);
		}
		checkLoadedChunks();
	}
	
	public static void AssignCreatureToChunk(Chunk chunk)
	{
		for(Creature c : creatures)
		{
			if(c.getChunk() != null)
			{
				if(!(Game.inBetween(c.getOrigin(), chunk.origin, new Point(chunk.origin.x + Chunk.WIDTH, chunk.origin.y + Chunk.HEIGHT))))
				{
					chunk.creatures.remove(c);
				}
						
			}
			if(Game.inBetween(c.getOrigin(), chunk.origin, new Point(chunk.origin.x + Chunk.WIDTH, chunk.origin.y + Chunk.HEIGHT)))
			{
				chunk.addCreature(c);
				c.setChunk(chunk);
			}
			
		}
	}
	
	
	
}
