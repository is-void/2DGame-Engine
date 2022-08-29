package com.game;

import java.awt.Graphics;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.game.entities.Chunk;
import com.game.entities.creatures.Creature;
import com.game.entities.creatures.Player;
import com.game.entities.tiles.Tile;

public class ChunkManager
{
	private Game game;
	private String path;
	public ArrayList<Chunk> Chunks = new ArrayList<>();
	public ArrayList<Chunk> LoadedChunks = new ArrayList<>();
	public ArrayList<Creature> creatures = new ArrayList<>();


	public ChunkManager(Game game, String path)
	{
		this.game = game;
		this.path = path;
	}

	public void initialize()
	{
		game.state = GameState.GAMESTATE.LOADING;
		File directory;
		
		
		directory = new File(path);
		
		
		if(directory.isDirectory())
			setChunks(directory.listFiles());
		else
		{
			System.out.print(directory.getPath() + " is not a directory");
			Game.ShowMessageBox("Error : " + directory + "is not a directory");
		}

	}

	public void setChunks(File[] files)
	{
		Chunks.clear();

		for(File chunk : files)
		{

			try {

				Scanner s = new Scanner(chunk);
				Chunks.add(new Chunk(game, s.nextInt()*Chunk.WIDTH, s.nextInt()*Chunk.HEIGHT, chunk.getPath()));

				s.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public void checkLoadedChunks(Player player) throws CloneNotSupportedException
	{
		ArrayList<Chunk> temp = new ArrayList<>();
		for(Chunk c : Chunks)
		{
			if(c.isNearPlayer(player))
			{
				temp.add(c);
				AssignCreatureToChunk(c);
			}

		}

		for(Chunk c : temp)
		{
			c.checkTileDependent();
			/*
			for(Tile t : c.tiles)
			{
				if(t.nearCreature(game.player))
					tempTiles.add(t);
			}
			*/
		}

		LoadedChunks = temp;

	}
	public void renderTiles(Graphics g)
	{
		for(Chunk c : LoadedChunks)
		{
			c.renderTiles(g);
		}
	}

	public void renderCreatures(Graphics g, Game game)
	{
		for(Chunk c : LoadedChunks)
		{

			c.renderCreatures(g, game.player);

		}
	}

	public void renderGUI(Graphics g)
	{
		for(Chunk c : LoadedChunks)
			c.renderHealthBars(g);
	}

	public void updateChunks()
	{
		for(Chunk c : LoadedChunks)
		{
			c.updateTiles();
			c.updateCreatures();
			c.updateHealthBars();
		}
	}

	public void longUpdateChunks(Game game) throws CloneNotSupportedException
	{

		for(Chunk  c : LoadedChunks)
		{

			c.longUpdate();

			AssignCreatureToChunk(c);
		}
		try {
			checkLoadedChunks(game.player);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void AssignCreatureToChunk(Chunk chunk)
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

	public ArrayList<Chunk> getChunks() {
		return Chunks;
	}

	public void setChunks(ArrayList<Chunk> chunks) {
		Chunks = chunks;
	}

	public ArrayList<Chunk> getLoadedChunks() {
		return LoadedChunks;
	}

	public void setLoadedChunks(ArrayList<Chunk> loadedChunks) {
		LoadedChunks = loadedChunks;
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}

	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}

	public Tile getTileAtPosition(Point postion)
	{
		for(Chunk c : Chunks)
		{
			for(Tile t : c.tiles)
			{
				if(t.getHitbox().contains(postion))
				{
					return t;
				}
			}
		}

		return null;
	}

	public Tile getTileAtPosition(Point postion, Chunk c)
	{
		for(Tile t : c.tiles)
		{
			if(t.getHitbox().contains(postion))
				return t;
		}

		return null;
	}

}
