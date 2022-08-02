package com.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.game.entities.creatures.Creature;
import com.game.entities.creatures.Player;
import com.game.entities.tiles.ConnecterTile;
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
	public static Sprite grass_1, grasspath_center_1, grasspath_vertical_1, grasspath_horizontal_1, grasspath_right_1, grasspath_up_1, grasspath_left_1, grasspath_down_1, grasspath_topLeft_1, grasspath_topRight_1, grasspath_bottomLeft_1, grasspath_bottomRight_1,
	path_1, brick_1, grass_2, watergrass_up_1, grasspath_topEnd_1, grasspath_bottomEnd_1, grasspath_leftEnd_1, grasspath_rightEnd_1, watergrass_right_1, watergrass_left_1,  watergrass_topLeft_1, watergrass_topRight_1, watergrass_bottomLeft_1, watergrass_bottomRight_1,
	watergrass_down_1, water_1, watergrass_topLeftCorner_1, watergrass_topRightCorner_1, watergrass_bottomLeftCorner_1, watergrass_bottomRightCorner_1, watergrass_center_1, watergrass_vertical_1, watergrass_horizontal_1, watergrass_topEnd_1, watergrass_bottomEnd_1, watergrass_leftEnd_1, watergrass_rightEnd_1; 
	
	public static Sprite[] grasspathTiles_1;
	
	public static Sprite[] watergrassTiles_1; 
	public static Sprite air;
	
	public static HashMap<Integer, Tile> tileID = new HashMap<Integer, Tile>();
	
	static ArrayList<Creature> creatures = new ArrayList<>();
	
	public static void initialize(Game game) throws CloneNotSupportedException
	{
		initializeAssets(game);
		System.out.println("Assets Loaded");
		
		initializeEntities(game);
		
		game.chunkManager.initialize();
				
		game.chunkManager.creatures.add(game.player);
		
		System.out.print("ready");
	}
	
	public static void initializeAssets(Game game) throws CloneNotSupportedException
	{
		System.out.println("Intializing Assets \n[");
		playerSprites = new SpriteSheet(ImageLoader.loadImage("/textures/testplayer_idle.png"));
		player_move = new Sprite(playerSprites.getAnimation(1, 0, 30, 51), "upAnim", 30, 51);
		
		tiles = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles.png"));
		
		grass_1 = new Sprite(tiles.getAnimation(1, 0, 32, 32), "grass_1",  32, 32);
		grass_2 = new Sprite(tiles.getAnimation(1, 1, 32, 32), "grass_2", 32, 32);
		
		path_1 = new Sprite(tiles.getAnimation(1, 2, 32, 32), "path_1", 32, 32);
		
		grasspath_center_1 = new Sprite(tiles.getAnimation(1, 3, 32, 32), "grasspath_center_1", 32, 32);
		grasspath_vertical_1 = new Sprite(tiles.getAnimation(1, 4, 32, 32), "grasspath_vertical_1", 32, 32);
		grasspath_horizontal_1 = new Sprite(tiles.getAnimation(1, 5, 32, 32), "grasspath_horizontal_1", 32, 32);
		
		grasspath_up_1 = new Sprite(tiles.getAnimation(1, 6, 32, 32), "grasspath_up_1", 32, 32);
		grasspath_left_1 = new Sprite(tiles.getAnimation(1, 7, 32, 32), "grasspath_left_1", 32, 32);
		grasspath_right_1 = new Sprite(tiles.getAnimation(1, 8, 32, 32), "grasspath_right_1", 32, 32);
		grasspath_down_1 = new Sprite(tiles.getAnimation(1, 9, 32, 32), "grasspath_down_1", 32, 32);
		
		grasspath_topLeft_1 = new Sprite(tiles.getAnimation(1, 10, 32, 32), "grasspath_topLeft_1", 32, 32);
		grasspath_topRight_1 = new Sprite(tiles.getAnimation(1, 11, 32, 32), "grasspath_topRight_1", 32, 32);
		grasspath_bottomLeft_1 = new Sprite(tiles.getAnimation(1, 12, 32, 32), "grasspath_bottomLeft_1", 32, 32);
		grasspath_bottomRight_1 = new Sprite(tiles.getAnimation(1, 13, 32, 32), "grasspath_bottomRight_1", 32, 32);
		
		grasspath_topEnd_1 = new Sprite(tiles.getAnimation(1, 14, 32, 32), "grasspath_topEnd_1", 32, 32);
		grasspath_leftEnd_1 = new Sprite(tiles.getAnimation(1, 15, 32, 32), "grasspath_leftEnd_1", 32, 32);
		grasspath_rightEnd_1 = new Sprite(tiles.getAnimation(1, 16, 32, 32), "grasspath_rightEnd_1", 32, 32);
		grasspath_bottomEnd_1 = new Sprite(tiles.getAnimation(1, 17, 32, 32), "grasspath_bottomEnd_1", 32, 32);
		
		
		brick_1 = new Sprite(tiles.getAnimation(1, 18, 32, 32), "brick_1", 32, 32);

		water_1 = new Sprite(tiles.getAnimation(1, 19, 32, 32), "water_1", 32, 32);
		
		watergrass_center_1 = new Sprite(tiles.getAnimation(1, 20, 32, 32), "watergrass_center_1", 32, 32);
		
		watergrass_vertical_1 = new Sprite(tiles.getAnimation(1, 21, 32, 32), "watergrass_vertical_1", 32, 32);
		watergrass_horizontal_1 = new Sprite(tiles.getAnimation(1, 22, 32, 32), "watergrass_horizontal_1", 32, 32);
		
		watergrass_up_1 = new Sprite(tiles.getAnimation(1, 23, 32, 32), "watergrass_up_1", 32, 32);
		watergrass_left_1 = new Sprite(tiles.getAnimation(1, 24, 32, 32), "watergrass_left_1", 32, 32);
		watergrass_right_1 =  new Sprite(tiles.getAnimation(1, 25, 32, 32), "watergrass_right_1", 32, 32);
		watergrass_down_1 = new Sprite(tiles.getAnimation(1, 26, 32, 32), "watergrass_down_1", 32, 32);
		
		watergrass_topLeft_1 = new Sprite(tiles.getAnimation(1, 27, 32, 32), "watergrass_topLeft_1", 32, 32);
		watergrass_topRight_1 = new Sprite(tiles.getAnimation(1, 28, 32, 32), "watergrass_topRight_1", 32, 32);
		watergrass_bottomLeft_1 = new Sprite(tiles.getAnimation(1, 29, 32, 32), "watergrass_bottomLeft_1", 32, 32);
		watergrass_bottomRight_1 = new Sprite(tiles.getAnimation(1, 30, 32, 32), "watergrass_bottomRight_1", 32, 32);
		
		watergrass_topEnd_1 = new Sprite(tiles.getAnimation(1, 31, 32, 32), "watergrass_topEnd_1", 32, 32);
		watergrass_leftEnd_1 = new Sprite(tiles.getAnimation(1, 32, 32, 32), "watergrass_leftEnd_1", 32, 32);
		watergrass_bottomEnd_1 = new Sprite(tiles.getAnimation(1, 33, 32, 32), "watergrass_bottomEnd_1", 32, 32);
		watergrass_rightEnd_1 = new Sprite(tiles.getAnimation(1, 34, 32, 32), "watergrass_rightEnd_1", 32, 32);
		
		watergrass_topRightCorner_1 = new Sprite(tiles.getAnimation(1, 35, 32, 32), "watergrass_topRightCorner_1", 32, 32);
		watergrass_topLeftCorner_1 = new Sprite(tiles.getAnimation(1, 36, 32, 32), "watergrass_topLeftCorner_1", 32, 32);	
		watergrass_bottomRightCorner_1 = new Sprite(tiles.getAnimation(1, 37, 32, 32), "watergrass_bottomRightCorner_1", 32, 32);
		watergrass_bottomLeftCorner_1 = new Sprite(tiles.getAnimation(1, 38, 32, 32), "watergrass_bottomLeftCorner_1", 32, 32);
		
		tileSprites = new SpriteSheet(ImageLoader.loadImage("/textures/Test Block.png"));
		testTile = new Sprite(tileSprites.getAnimation(	10, 0, 32, 32), "idleSprite", 32, 32);
		
		air = new Sprite();
		grasspathTiles_1 = new Sprite[] {path_1, grasspath_center_1, grasspath_vertical_1, grasspath_horizontal_1, grasspath_up_1, grasspath_down_1, grasspath_left_1, grasspath_right_1, grasspath_topLeft_1, grasspath_topRight_1, grasspath_bottomLeft_1, grasspath_bottomRight_1, grasspath_topEnd_1, grasspath_bottomEnd_1, grasspath_leftEnd_1, grasspath_rightEnd_1};
		watergrassTiles_1 = new Sprite[] {water_1, watergrass_center_1, watergrass_vertical_1, watergrass_horizontal_1, watergrass_up_1, watergrass_down_1, watergrass_left_1, watergrass_right_1, watergrass_topLeft_1, watergrass_topRight_1, watergrass_bottomLeft_1, watergrass_bottomRight_1, watergrass_topEnd_1, watergrass_bottomEnd_1, watergrass_leftEnd_1, watergrass_rightEnd_1, watergrass_topLeftCorner_1, watergrass_topRightCorner_1, watergrass_bottomLeftCorner_1, watergrass_bottomRightCorner_1};
		
		assignTiles(game);
		System.out.print("]\nfinished initializing assets");
	}
	
	public static void initializeEntities(Game game) throws CloneNotSupportedException
	{
		game.player = new Player(game, new EntityAnimator( 32, 32, player_move), 30, 30);
	}
	
	private static void assignTiles(Game game) throws CloneNotSupportedException
	{
		System.out.println("Assigning Tiles");
		
		tileID.put(0, new Tile(game, new Animator(Assets.air) , true));
		tileID.put(1, new Tile(game, new Animator(Assets.grass_1) , false));
		tileID.put(2, new Tile(game, new Animator(Assets.grass_2) , false));
		tileID.put(3, new ConnecterTile(game, grasspathTiles_1, false));
		tileID.put(5, new Tile(game, new Animator(Assets.brick_1) , true));
		tileID.put(6, new ConnecterTile(game, watergrassTiles_1, true));
		
		System.out.println("Finished Assigning Tiles");
		
	}
}
