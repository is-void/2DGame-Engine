package com.game.entities.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import com.game.Game;
import com.game.entities.Entity;
import com.game.sprites.Animator;


public class Tile extends Entity
{
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	public static int check = 0;
	public int chunkIndex;
	public boolean drawHitbox;
	
	public Tile tileAbove;
	public Tile tileBelow;
	public Tile tileRight;
	public Tile tileLeft;
	public Tile tileBottomRight;
	public Tile tileBottomLeft;
	public Tile tileTopRight;
	public Tile tileTopLeft;
	
	enum TileType
	{
		GRASS,
		GRASSPATH,
		WATERPATH,
		PATH,
		WATER,
		OTHER
	}
	public TileType type;
	
	public Tile(Game game, boolean col)
	{
		super(game, col);
	}
	public Tile(Game game, Animator anim, boolean collison)
	{
		super(game, anim, 0, 0, collison);
		anim.setOwner(this);
		
		
	}
	
	public Tile(Game game, Animator anim, boolean collison, int x, int y)
	{
		super(game, anim, x, y, collison);
		anim.setOwner(this);
		//isStatic = true;
		
	}
	
	
	public void drawSprite(Graphics g) 
	{
		super.drawSprite(g);
		g.setColor(Color.red);
		/*
		g.drawLine(getLocalX(), getLocalY(), Game.player.getLocalX(), Game.player.getLocalY());
		*/
		if(getHitbox().contains(new Point2D.Double(game.player.mouseX, game.player.mouseY)))
			game.highlightedTile = this;
		if(drawHitbox)
			drawHitbox(g);
		
		
	}
	
	public void update() {
		
		super.update();
		checkTileType(this);
		
	}
	
	public static void checkTileType(Tile t)
	{
		String nam = t.animator.sprite.name.substring(0, t.animator.sprite.name.indexOf("_"));
		 
		switch(nam)
		{
			case "grass" :
				t.type = TileType.GRASS;
				break;
			
			case "watergrass" :
			case "water" :
				t.type = TileType.WATER;
				break;

				
			case "grasspath" :
			case "path" : 
				t.type = TileType.GRASSPATH;
				break;
				
			default : 
				t.type = TileType.OTHER;
				return;
		}
		
	}
	
	public void check() throws CloneNotSupportedException
	{
		if(scheduledUpdate)
		{
				tileTopRight = tileTopLeft = tileBottomRight = tileBottomLeft = tileAbove = tileBelow = tileRight = tileLeft = null;
			
			checkTileType(this);
			
			if(chunkIndex <= 7)
			{
				if(chunk.chunkAbove() != null)
				{
					tileAbove = chunk.chunkAbove().tiles[56 + chunkIndex];
				} else
				{
					tileAbove = null;
				}
				
			} else
			{		
				tileAbove = chunk.tiles[chunkIndex-8];
			}
			
			
			
			if(chunkIndex >= 56)
			{
				if(chunk.chunkBelow() != null)
				{
					tileBelow = chunk.chunkBelow().tiles[chunkIndex - 56];
				} else
				{
					tileBelow = null;
				}
			} else
			{
				tileBelow = chunk.tiles[chunkIndex + 8];
			}
			
			
			if(chunkIndex  % 8 == 0)
			{
				if(chunk.chunkLeft() != null)
				{
					tileLeft = chunk.chunkLeft().tiles[chunkIndex + 7];
				} else
				{
					tileLeft = null;
				}
			} else
			{
				tileLeft = chunk.tiles[chunkIndex-1];
			}
			
			
			if((chunkIndex+1) % 8 == 0)
			{
				if(chunk.chunkRight() != null)
				{
					tileRight = chunk.chunkRight().tiles[chunkIndex - 7];
				} else
				{
					tileRight = null;
				}
			} else
			{
				tileRight = chunk.tiles[chunkIndex+1];
			}
			scheduledUpdate = false;
		}
		
		
		/*
		switch(type)
		{
			case GRASS :
				 
				
				if(tileAbove != null)
					switch(tileAbove.animator.sprite.name)
					{
						case "path_1" :
							animator.setSprite(Assets.grasspath_down_1.clone());
							return;
						
						case "water_1" :
							animator.setSprite(Assets.watergrass_down_1.clone());
							doCollision(true);
							return;
					}
					
				
				if(tileBelow != null)
					switch(tileBelow.animator.sprite.name)
					{
						case "path_1" :
							animator.setSprite(Assets.grasspath_up_1.clone());
							return;
							
						case "water_1" :							
							animator.setSprite(Assets.watergrass_up_1.clone());
							doCollision(true);
							return;
					}

				
				if(tileLeft != null)
					switch(tileLeft.animator.sprite.name)
					{
						case "path_1" :
							animator.setSprite(Assets.grasspath_right_1.clone());
							return;
							
						case "water_1" :						
							animator.setSprite(Assets.watergrass_right_1.clone());
							doCollision(true);
							return;
					}

				
				
				if(tileRight != null)
					switch(tileRight.animator.sprite.name)
					{
						case "path_1" :
							animator.setSprite(Assets.grasspath_left_1.clone());
							return;
						case "water_1" :
							animator.setSprite(Assets.watergrass_left_1.clone());
							doCollision(true);
							return;
					}

				break;
				
			
			case PATH :
			{
				
				if(tileAbove != null)
				{
					if(tileAbove.tileLeft != null)
					{
						if(tileAbove.tileLeft.type == TileType.GRASS)
						{
							if(tileAbove.type == TileType.GRASSPATH)
							{
								if(tileLeft.type == TileType.GRASSPATH)
								{
									tileAbove.tileLeft.animator.setSprite(Assets.grasspath_topLeft_1);
									System.out.println("top left");
								}
							}
						}
						
						if(tileAbove.tileRight.type == TileType.GRASS)
						{
							if(tileAbove.type == TileType.GRASSPATH)
							{
								if(tileRight.type == TileType.GRASSPATH)
								{
									tileAbove.tileRight.animator.setSprite(Assets.grasspath_topRight_1);
								}
							}
						}
					}
					
				}
				
				if(tileBelow != null)
				{
					if(tileBelow.tileLeft != null)
					{
						if(tileBelow.tileLeft.type == TileType.GRASS)
						{
							if(tileBelow.type == TileType.GRASSPATH)
							{
								if(tileLeft.type == TileType.GRASSPATH)
								{
									tileBelow.tileLeft.animator.setSprite(Assets.grasspath_bottomLeft_1);
								}
							}
						}
						
						if(tileBelow.tileRight.type == TileType.GRASS)
						{
							if(tileBelow.type == TileType.GRASSPATH)
							{
								if(tileRight.type == TileType.GRASSPATH)
								{
									tileBelow.tileRight.animator.setSprite(Assets.grasspath_bottomRight_1);
								}
							}
						}
					}
					
				}
				break;
			}
				
			case WATER :
			{
				
				if(tileAbove != null)
				{
					if(tileAbove.tileLeft.type == TileType.GRASS)
						{
							
							if(tileAbove.type == TileType.WATERPATH)
							{
								
								if(tileLeft.type == TileType.WATERPATH)
								{
									
									tileAbove.tileLeft.animator.setSprite(Assets.watergrass_topLeft_1);
									tileAbove.tileLeft.doCollision(true);
									
								}
							}
						}
						
					if(tileAbove.tileRight.type == TileType.GRASS)
					{
						if(tileAbove.type == TileType.WATERPATH)
						{
							if(tileRight.type == TileType.WATERPATH)
							{
								tileAbove.tileRight.animator.setSprite(Assets.watergrass_topRight_1);
								tileAbove.tileRight.doCollision(true);
							}
						}
					}
					
					
				}
				
				if(tileBelow != null)
				{
					if(tileBelow.tileLeft != null)
					{
						if(tileBelow.tileLeft.type == TileType.GRASS)
						{
							if(tileBelow.type == TileType.WATERPATH)
							{
								if(tileLeft.type == TileType.WATERPATH)
								{
									tileBelow.tileLeft.animator.setSprite(Assets.watergrass_bottomLeft_1);
									tileBelow.tileLeft.doCollision(true);
								}
							}
						}
						
						if(tileBelow.tileRight.type == TileType.GRASS)
						{
							if(tileBelow.type == TileType.WATERPATH)
							{
								if(tileRight.type == TileType.WATERPATH)
								{
									tileBelow.tileRight.animator.setSprite(Assets.watergrass_bottomRight_1);
									tileBelow.tileRight.doCollision(true);
								}
							}
						}
					}
					
				}
				break;
			}
			
			case WATERPATH :
			{
				if(tileAbove != null && tileAbove.type == TileType.WATERPATH)
				{
					if(tileLeft != null && tileLeft.type == TileType.WATERPATH)
					{
						if(tileRight != null && tileRight.type == TileType.WATER)
							this.animator.setSprite(Assets.watergrass_topLeftCorner_1);
					}
					if(tileRight != null && tileRight.type == TileType.WATERPATH)
					{
						this.animator.setSprite(Assets.watergrass_topRightCorner_1);
					}
				}
			}
			default :
				break;
		}
		*/
	}
	
	public EntityType getType()
	{
		return EntityType.TILE;
	}
	
	
	public Tile clone()
	{
		return new Tile(game, new Animator(getAnimator().getSprite()), isStatic);
	}
	
}
