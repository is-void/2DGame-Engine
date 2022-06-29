package com.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.game.entities.Entity;
import com.game.entities.EntityManager;
import com.game.entities.TestEntity;
import com.game.entities.creatures.Player;
import com.game.gfx.ImageLoader;
import com.game.sprites.Animator;
import com.game.sprites.EntityAnimator;
import com.game.sprites.Sprite;
import com.game.sprites.SpriteSheet;

public class Assets 
{
	static Sprite player_move;
	static SpriteSheet playerSprites;
	static SpriteSheet tileSprites;
	static Sprite testTile;
	static boolean loaded = false;
	static ArrayList<Entity> entities = new ArrayList<>();
	
	public static void initialize() throws CloneNotSupportedException
	{
		initializeAssets();
		System.out.print("Assets Loaded");
		initializeEntities();
		System.out.print("Entities loaded");
		
		
		
		Game.entityManager.addEntity(Game.player);
		
		for(Entity e : entities)
		{
			Game.entityManager.addEntity(e);
		}
		
		System.out.print("ready");
	}
	public static void initializeAssets()
	{
		System.out.print("Intializing Assets");
		playerSprites = new SpriteSheet(ImageLoader.loadImage("/textures/test Player.png"));
		player_move = new Sprite(playerSprites.getAnimation(6, 0, 32, 32), "upAnim", 32, 32);
		
		tileSprites = new SpriteSheet(ImageLoader.loadImage("/textures/Test Block.png"));
		testTile = new Sprite(tileSprites.getAnimation(	10, 0, 32, 32), "idleSprite", 32, 32);
		
		System.out.print("finished initializing assets");
	}
	public static void initializeEntities() throws CloneNotSupportedException
	{
		Game.player = new Player(new EntityAnimator( 32, 32, player_move.clone()), 0, 0);
		entities.add(new TestEntity(new Animator( testTile.clone()), 90, 90));
		entities.add(new TestEntity(new Animator( testTile.clone()), 90, -90));
		
		
		
		
		
		
	}
}
