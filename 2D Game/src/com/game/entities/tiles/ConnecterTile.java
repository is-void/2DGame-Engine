package com.game.entities.tiles;

import com.game.Game;
import com.game.sprites.Animator;
import com.game.sprites.Sprite;
import java.util.HashMap;

public class ConnecterTile extends Tile
{
	HashMap<String, Sprite> directionalTiles = new HashMap<String, Sprite>();
	Sprite[] sprites;
	
	
	public ConnecterTile(Game game, Sprite[] sprites, boolean collison) throws CloneNotSupportedException 
	{
		super(game, collison);
		animator = new Animator(sprites[0]);
		defaultHitbox();
		animator.setOwner(this);
		this.sprites = sprites;
		
		directionalTiles.put("base", sprites[0].clone());
		directionalTiles.put("center", sprites[1].clone());
		directionalTiles.put("vertical", sprites[2].clone());
		directionalTiles.put("horizontal", sprites[3].clone());
		directionalTiles.put("up", sprites[4].clone());
		directionalTiles.put("down", sprites[5].clone());
		directionalTiles.put("left", sprites[6].clone());
		directionalTiles.put("right", sprites[7].clone());
		directionalTiles.put("topLeft", sprites[8].clone());
		directionalTiles.put("topRight", sprites[9].clone());
		directionalTiles.put("bottomLeft", sprites[10].clone());
		directionalTiles.put("bottomRight", sprites[11].clone());
		directionalTiles.put("topEnd", sprites[12].clone());
		directionalTiles.put("bottomEnd", sprites[13].clone());
		directionalTiles.put("leftEnd", sprites[14].clone());
		directionalTiles.put("rightEnd", sprites[15].clone());
		
		Tile.checkTileType(this);
		System.out.print(type);
		switch(type)
		{
			
			case PATH :
				break;
			
			case WATER :
				directionalTiles.put("topLeftCorner", sprites[16].clone());
				directionalTiles.put("topRightCorner", sprites[17].clone());
				directionalTiles.put("bottomLeftCorner", sprites[18].clone());
				directionalTiles.put("bottomRightCorner", sprites[19].clone());
				
				break;
				
			default :
				break;
			
		}
		

	}
	
	
	public void check() throws CloneNotSupportedException
	{
		
		super.check();
		
		boolean connecterAbove = false;
		boolean connecterBelow = false;
		boolean connecterRight = false;
		boolean connecterLeft = false;
		
		if(tileAbove.type == this.type )
		{
			connecterAbove = true;
		}
		if(tileBelow.type == this.type)
		{
			connecterBelow = true;
		}
		if(tileRight.type == this.type)
		{
			connecterRight = true;
		}
		if(tileLeft.type == this.type)
		{
			connecterLeft = true;
		}
		
		
		
		if(connecterAbove)
		{
			if(connecterBelow)
			{
				if(connecterLeft && connecterRight)
				{
						animator.setSprite(directionalTiles.get("base").clone());
						
				} else if(connecterLeft)
				{
					animator.setSprite(directionalTiles.get("right").clone());
					
				}else if(connecterRight)
				{
					animator.setSprite(directionalTiles.get("left").clone());
				} else
				{
					animator.setSprite(directionalTiles.get("vertical").clone());

				}
			} else
			{
				if(connecterLeft)
				{
					if(connecterRight)
					{
						animator.setSprite(directionalTiles.get("down").clone());
					} else
					{
						animator.setSprite(directionalTiles.get("bottomRight").clone());
					}
				} else if(connecterRight)
				{
					animator.setSprite(directionalTiles.get("bottomLeft").clone());
				} else
				{
					animator.setSprite(directionalTiles.get("bottomEnd"));
				}
			}
		} else if(connecterBelow)
		{
			if(connecterLeft)
			{
				if(connecterRight)
				{
					animator.setSprite(directionalTiles.get("up").clone());
				} else
				{
					animator.setSprite(directionalTiles.get("topRight").clone());
				}
			} else if(connecterRight)
			{
				animator.setSprite(directionalTiles.get("topLeft").clone());
			} else
			{
				animator.setSprite(directionalTiles.get("topEnd"));
			}
		} else
		{
			if(connecterLeft)
			{
				if(connecterRight)
				{
					animator.setSprite(directionalTiles.get("horizontal"));
				} else
				{
					animator.setSprite(directionalTiles.get("rightEnd"));
				}
			} else
			{
				if(connecterRight)
				{
					animator.setSprite(directionalTiles.get("leftEnd"));
				} else
				{
					animator.setSprite(directionalTiles.get("center"));
				}
			}
		}
		if(connecterAbove || connecterLeft || connecterRight || connecterLeft)
		{
			boolean topRight = false;
			boolean topLeft = false;
			boolean bottomRight = false;
			boolean bottomLeft = false;
			
			if(tileAbove.tileRight != null && tileAbove.tileRight.type != this.type)
			{
				topRight = true;
			}
			
			if(tileAbove.tileLeft != null && tileAbove.tileLeft.type != this.type)
			{
				topLeft = true;
			}
			
			if(tileBelow.tileRight != null && tileBelow.tileRight.type != this.type)
			{
				bottomRight = true;
			}
			
			if(tileBelow.tileLeft != null && tileBelow.tileLeft.type != this.type)
			{
				bottomLeft = true;
			}
			
			if(topRight || topLeft || bottomRight || bottomLeft)
			{
				
				if(topRight && connecterAbove && connecterRight)
				{
					animator.addOverlay(directionalTiles.get("topRightCorner").clone());
				}
				if(topLeft && connecterAbove && connecterLeft)
				{
					animator.addOverlay(directionalTiles.get("topLeftCorner").clone());
				}
				if(bottomRight && connecterBelow && connecterRight)
				{
					animator.addOverlay(directionalTiles.get("bottomRightCorner").clone());
				}
				if(bottomLeft && connecterBelow && connecterLeft)
				{
					animator.addOverlay(directionalTiles.get("bottomLeftCorner").clone());
				}
				
				
			}
		}
		
		
		
		
		
	}
	
	public ConnecterTile clone()
	{
		try {
			return new ConnecterTile(game, sprites, canCollide());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
