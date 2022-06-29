package com.game.entities.creatures;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import com.game.Game;
import com.game.entities.Entity;
import com.game.entities.EntityManager;
import com.game.entities.tiles.Tile;
import com.game.sprites.Animator;
import com.game.sprites.Sprite;

public abstract class Creature extends Entity
{
	
	
	public Creature(Animator anim, int x, int y)
	{
		super(anim, x, y);
		
		
	}
	
	
	
	public void update()
	{
		
		prepareMovement();
		
		checkCollsion();
		super.update();
	}
	
	
	public void checkCollsion()
	{
		setCanMoveUp(true);
		setCanMoveDown(true);
		setCanMoveRight(true);
		setCanMoveLeft(true);
		if(EntityManager.tiles.size() >= 1)
		{
			for(int e = 0; e < EntityManager.tiles.size(); e++)
			{
				if(EntityManager.tiles.get(e).nearCreature(this))
				{
					
					collsionWithTile((Tile)EntityManager.tiles.get(e));
				} 
			}
		}
			

	}
	
	public void collsionWithTile(Tile e)
	{
		
		Line2D.Double leftBound = new Line2D.Double(e.getHitbox().getMinX(), e.getHitbox().getMinY(), e.getHitbox().getMinX(), e.getHitbox().getMaxY());
		Line2D.Double rightBound = new Line2D.Double(e.getHitbox().getMaxX(), e.getHitbox().getMinY(), e.getHitbox().getMaxX(), e.getHitbox().getMaxY());
		Line2D.Double topBound = new Line2D.Double(e.getHitbox().getMinX(), e.getHitbox().getMinY(), e.getHitbox().getMaxX(), e.getHitbox().getMinY());
		Line2D.Double bottomBound = new Line2D.Double(e.getHitbox().getMinX(), e.getHitbox().getMaxY(), e.getHitbox().getMaxX(), e.getHitbox().getMaxY());

		
		/*
		//--> collsion
		if(getHitbox().getMaxX() + getXVelocity() >= e.getHitbox().getMinX() && getHitbox().getMaxX()<= e.getHitbox().getMinX() && (Game.inBetween(getHitbox().getMinY(), e.getHitbox().getMinY(), e.getHitbox().getMaxY(), true) || Game.inBetween(getHitbox().getMaxY(), e.getHitbox().getMinY(), e.getHitbox().getMaxY(), true )))
		{
			setCanMoveRight(false); 
			
			setX(e.getX() - getHitbox().width-1);
		}
		
		//<-- collsion
		if(getHitbox().getMinX() + getXVelocity() <= e.getHitbox().getMaxX() && getHitbox().getMinX()>= e.getHitbox().getMaxX() && (Game.inBetween(getHitbox().getMinY(), e.getHitbox().getMinY(), e.getHitbox().getMaxY(), true) || Game.inBetween(getHitbox().getMaxY(), e.getHitbox().getMinY(), e.getHitbox().getMaxY(), true )))
		{
			setCanMoveLeft(false); 
			setX(e.getX() + e.getHitbox().width+1);
		}
		
		
		if(getHitbox().getMaxY() + getYVelocity() >= e.getHitbox().getMinY() && getHitbox().getMaxY()<= e.getHitbox().getMinY() && (Game.inBetween(getHitbox().getMinX(), e.getHitbox().getMinX(), e.getHitbox().getMaxX(), true) || Game.inBetween(getHitbox().getMaxX(), e.getHitbox().getMinX(), e.getHitbox().getMaxX(), true )))
		{
			
			setCanMoveDown(false); 
			setY(e.getY() - getHitbox().height-1);
		}
		
		if(getHitbox().getMinY() + getYVelocity() <= e.getHitbox().getMaxY() && getHitbox().getMinY()>= e.getHitbox().getMaxY() && (Game.inBetween(getHitbox().getMinX(), e.getHitbox().getMinX(), e.getHitbox().getMaxX(), true) || Game.inBetween(getHitbox().getMaxX(), e.getHitbox().getMinX(), e.getHitbox().getMaxX(), true )))
		{
			setCanMoveUp(false); 
			setY(e.getY() + e.getHitbox().height+1);
		}
		*/
		Rectangle testHitbox = new Rectangle(getLocalX(), getLocalY(), (int) getHitbox().getWidth(), (int) getHitbox().getHeight());
		
		testHitbox.setLocation((int) (getLocalX() + getXVelocity()), getLocalY());
		/*
		if(testHitbox.intersects(e.getHitbox()))
		{
			System.out.print("sdadasd");
			if(getXVelocity()>0)
			{
				setCanMoveRight(false); 
				setX(e.getX() - getHitbox().width-1);
			} else
			{
				setCanMoveLeft(false); 
				setX(e.getX() + e.getHitbox().width+1);
			}
		}
		
		testHitbox.setLocation(getLocalX(), (int) (getLocalY() + getYVelocity()));
		
		if(testHitbox.intersects(e.getHitbox()))
		{
			if(getYVelocity()>0)
			{
				setCanMoveDown(false); 
				setY(e.getY() - getHitbox().height-1);
			} else
			{
				setCanMoveUp(false); 
				setY(e.getY() + e.getHitbox().height+1);
			}
		}
		*/

		if((Game.inBetween(getHitbox().getMinY(), e.getHitbox().getMinY(), e.getHitbox().getMaxY(), true) || Game.inBetween(getHitbox().getMaxY(), e.getHitbox().getMinY(), e.getHitbox().getMaxY(), true )))
		{
			if(Game.inBetween(getHitbox().getMaxX(), e.getHitbox().getMinX(), e.getHitbox().getMinX()+1, true))
			{
				setCanMoveRight(false);
			}
			
			if(Game.inBetween(getHitbox().getMinX(), e.getHitbox().getMaxX(), e.getHitbox().getMaxX()+1, true))
			{
				setCanMoveLeft(false);
			}
			
			
		}
		if((Game.inBetween(getHitbox().getMinX(), e.getHitbox().getMinX(), e.getHitbox().getMaxX(), true) || Game.inBetween(getHitbox().getMaxX(), e.getHitbox().getMinX(), e.getHitbox().getMaxX(), true )))
		{
			if(Game.inBetween(getHitbox().getMaxY(), e.getHitbox().getMinY(), e.getHitbox().getMinY()+1, true))
			{
				setCanMoveDown(false);
				
			}
			
			if(Game.inBetween(getHitbox().getMinY(), e.getHitbox().getMaxY(), e.getHitbox().getMaxY()+1, true))
			{
				setCanMoveUp(false);
				
			}
		}
		
		
		
		
		boolean l;
		boolean r;
		l = r = false;
		boolean u;
		boolean d;
		u = d = false;
		if(e.getHitbox().contains(getHitbox()))
		{
			setX(e.getX() + e.getHitbox().width+1);
		}
		
		if(testHitbox.intersectsLine(leftBound))
		{

			l = true;
		}
		
		if(testHitbox.intersectsLine(rightBound))
		{
			
			r = true;
		}
		
		testHitbox.setLocation(getLocalX(), (int) (getLocalY() + getYVelocity()));
		
		if(testHitbox.intersectsLine(topBound))
		{
			
			u = true;
		}
		
		if(testHitbox.intersectsLine(bottomBound))
		{
			
			d = true;
		}
		if(u && !(d||l||r))
		{
			setCanMoveDown(false); 
			setY(e.getY() - getHitbox().height-1);
		}
		
		
		if(d && !(u||l||r))
		{
			setCanMoveUp(false); 
			setY(e.getY() + e.getHitbox().height+1);
		}
		
		if(r && !(l||u||d))
		{
			setCanMoveLeft(false); 
			setX(e.getX() + e.getHitbox().width+1);
		}
		
		if(l && !(u || d || r))
		{
			setCanMoveRight(false); 		
			setX(e.getX() - getHitbox().width-1);
		}
		
		if(u && d)
		{
			setCanMoveUp(false);
			setCanMoveDown(false);
		}
		
		if(l && r)
		{
			setCanMoveLeft(false);
			setCanMoveRight(false);
		}
		
		if((u || d) &&(l|| r))
		{
			Point pCheck = new Point();
			pCheck.setLocation(testHitbox.getCenterX(), testHitbox.getCenterY());
			Point horCenter = new Point();
			Point sideCenter = new Point();
			System.out.print("hit corner");
			if(u)
			{
				horCenter.setLocation((topBound.x1 + topBound.x2)/2, topBound.y1);
				if(l)
				{
					System.out.print("leftSide hit top");
					if(getHitbox().getMaxX() == e.getHitbox().getMinX() && getHitbox().getMaxY() == e.getHitbox().getMinY())
					{
						setCanMoveDown(false);
						setCanMoveRight(false);
						
						if(getXVelocity() > 0 && getYVelocity() == 0)
							setX(e.getX() - getHitbox().width-1);
						if(getYVelocity() > 0 && getXVelocity() == 0)
							setY(e.getY() - getHitbox().height-1);
							
					}
					
					if(getHitbox().getMaxX() + getXVelocity()-1 > e.getHitbox().getMinX()+1 && getHitbox().getMaxY() + getYVelocity()-1 > e.getHitbox().getMinY()+1)
					{
						setCanMoveDown(false);
						setCanMoveRight(false);
					}
					
					sideCenter.setLocation(leftBound.x1, (leftBound.y1+leftBound.y2));
					if(pCheck.distance(sideCenter) < pCheck.distance(horCenter))
					{
						setCanMoveDown(false); 
						setY(e.getY() - getHitbox().height-1);
					} else if(pCheck.distance(sideCenter) > pCheck.distance(horCenter))
					{
						setCanMoveRight(false); 		
						setX(e.getX() - getHitbox().width-1);
						System.out.print("side closer");
					} else if(pCheck.distance(sideCenter) == pCheck.distance(horCenter))
					{
						setCanMoveRight(false);
						setCanMoveUp(false);
					}
					
					
				}
				
				if(r)
				{
					System.out.print("Rightside Hit");
					
					sideCenter.setLocation(rightBound.x1, (rightBound.y1+rightBound.y2));
					if(pCheck.distance(sideCenter) > pCheck.distance(horCenter))
					{
						setCanMoveDown(false); 
						setY(e.getY() - getHitbox().height-1);
					} else if(pCheck.distance(sideCenter) < pCheck.distance(horCenter))
					{
						setCanMoveLeft(false); 
						setX(e.getX() + e.getHitbox().width+1);
					} else if(pCheck.distance(sideCenter) == pCheck.distance(horCenter))
					{
						setCanMoveLeft(false);
						setCanMoveUp(false);
					}
					
				}
			}
			if(d)
			{
					horCenter.setLocation((bottomBound.x1 + bottomBound.x2)/2, bottomBound.y1);
					if(l)
					{
						System.out.print("leftSide hit");
						
						sideCenter.setLocation(leftBound.x1, (leftBound.y1+leftBound.y2));
						if(pCheck.distance(sideCenter) < pCheck.distance(horCenter))
						{
							setCanMoveUp(false); 
							setY(e.getY() + e.getHitbox().height+1);
							System.out.print("change up");
						} else if(pCheck.distance(sideCenter) > pCheck.distance(horCenter))
						{
							setCanMoveRight(false); 		
							setX(e.getX() - getHitbox().width-1);
						} else if(pCheck.distance(sideCenter) == pCheck.distance(horCenter))
						{
							setCanMoveRight(false);
							setCanMoveUp(false);
						}
						
					}
					
					if(r)
					{
						System.out.print("Rightside Hit");
						
						sideCenter.setLocation(rightBound.x1, (rightBound.y1+rightBound.y2));
						if(pCheck.distance(sideCenter) < pCheck.distance(horCenter))
						{
							setCanMoveUp(false); 
							setY(e.getY() + e.getHitbox().height+1);
						} else if(pCheck.distance(sideCenter) > pCheck.distance(horCenter))
						{
							setCanMoveLeft(false); 
							setX(e.getX() + e.getHitbox().width+1);
						} else if(pCheck.distance(sideCenter) == pCheck.distance(horCenter))
						{
							setCanMoveLeft(false);
							setCanMoveUp(false);
						}
						
					}
			}
			
			
		}
		
			
	}
	
	public String getType()
	{
		return "Creature";
	}
	

}
