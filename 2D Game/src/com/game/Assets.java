package com.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.game.entities.Entity;
import com.game.entities.Chunk;
import com.game.entities.creatures.Creature;
import com.game.entities.creatures.Player;
import com.game.entities.tiles.Tile;
import com.game.gfx.ImageLoader;
import com.game.sprites.Animator;
import com.game.sprites.EntityAnimator;
import com.game.sprites.Sprite;
import com.game.sprites.SpriteSheet;

public class Assets 
{
	public static Sprite player_move;
	public static SpriteSheet playerSprites;
	public static SpriteSheet tileSprites;
	public static Sprite testTile;
	public static SpriteSheet tiles;
	public static boolean loaded = false;
	public static Sprite grass_1, grasspath_right_1, grasspath_up_1, grasspath_left_1, grasspath_down_1, 
	path_1, brick_1, grass_2, watergrass_up_1, watergrass_right_1, watergrass_left_1, 
	watergrass_down_1, water_1; 
	
	public static Sprite air;
	
	public static HashMap<String, Tile> tileID = new HashMap<String, Tile>();
	
	static ArrayList<Creature> creatures = new ArrayList<>();
	
	public static void initialize() throws CloneNotSupportedException
	{
		initializeAssets();
		System.out.println("Assets Loaded");
		
		initializeEntities();
		System.out.println("Entities loaded");
		ChunkManager.initialize();
		
		
		ChunkManager.creatures.add(Game.player);
		
		
		
		System.out.print("ready");
	}
	
	public static void initializeAssets()
	{
		System.out.println("Intializing Assets \n[");
		playerSprites = new SpriteSheet(ImageLoader.loadImage("/textures/testplayer_idle.png"));
		player_move = new Sprite(playerSprites.getAnimation(1, 0, 30, 51), "upAnim", 30, 51);
		tiles = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles.png"));
		
		grass_1 = new Sprite(tiles.getAnimation(1, 0, 32, 32), 32, 32);
		
		grasspath_right_1 = new Sprite(tiles.getAnimation(1, 1, 32, 32), 32, 32);
		grasspath_up_1 = new Sprite(tiles.getAnimation(1, 2, 32, 32), 32, 32);
		grasspath_left_1 = new Sprite(tiles.getAnimation(1, 3, 32, 32), 32, 32);
		grasspath_down_1 = new Sprite(tiles.getAnimation(1, 4, 32, 32), 32, 32);
		path_1 = new Sprite(tiles.getAnimation(1, 5, 32, 32), 32, 32);
				
		brick_1 = new Sprite(tiles.getAnimation(1, 6, 32, 32), 32, 32);

		grass_2 = new Sprite(tiles.getAnimation(1, 7, 32, 32), 32, 32);
		
		watergrass_up_1 = new Sprite(tiles.getAnimation(1, 8, 32, 32), 32, 32);
		watergrass_right_1 = new Sprite(tiles.getAnimation(1, 9, 32, 32), 32, 32);
		watergrass_left_1 = new Sprite(tiles.getAnimation(1, 10, 32, 32), 32, 32);
		watergrass_down_1 = new Sprite(tiles.getAnimation(1, 11, 32, 32), 32, 32);
		water_1 = new Sprite(tiles.getAnimation(1, 12, 32, 32), 32, 32);
		
		
		tileSprites = new SpriteSheet(ImageLoader.loadImage("/textures/Test Block.png"));
		testTile = new Sprite(tileSprites.getAnimation(	10, 0, 32, 32), "idleSprite", 32, 32);
		
		air = new Sprite();
		
		assignTiles();
		System.out.print("]\nfinished initializing assets");
	}
	public static void initializeEntities() throws CloneNotSupportedException
	{
		Game.player = new Player(new EntityAnimator( 32, 32, player_move), 30, 30);

		
		
		
		
		
		
		
	}
	
	private static void assignTiles()
	{
		System.out.println("Assigning Tiles");
		tileID.put("grass_1", new Tile(new Animator(Assets.grass_1) , false));
		tileID.put("grass_2", new Tile(new Animator(Assets.grass_2) , false));
		tileID.put("grasspath_right_1", new Tile(new Animator(Assets.grasspath_right_1) , false));
		tileID.put("grasspath_up_1", new Tile(new Animator(Assets.grasspath_up_1) , false));
		tileID.put("grasspath_left_1", new Tile(new Animator(Assets.grasspath_left_1) , false));
		tileID.put("grasspath_down_1", new Tile(new Animator(Assets.grasspath_down_1) , false));	
		tileID.put("path_1", new Tile(new Animator(Assets.path_1) , false));
		tileID.put("brick_1", new Tile(new Animator(Assets.brick_1) , true));
		tileID.put("watergrass_up_1", new Tile(new Animator(Assets.watergrass_up_1) , true));
		tileID.put("watergrass_right_1", new Tile(new Animator(Assets.watergrass_right_1) , true));
		tileID.put("watergrass_left_1", new Tile(new Animator(Assets.watergrass_left_1) , true));
		tileID.put("watergrass_down_1", new Tile(new Animator(Assets.watergrass_down_1) , true));
		tileID.put("water_1", new Tile(new Animator(Assets.water_1) , true));
		System.out.println("Finished Assigning Tiles");
		
	}
}
