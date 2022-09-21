package com.game.entities.creatures;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.game.Game;
import com.game.display.ui.Bar;
import com.game.display.ui.Bar.BarType;
import com.game.entities.Chunk;
import com.game.entities.Entity;
import com.game.entities.tiles.Tile;
import com.game.sprites.Animator;


public abstract class Creature extends Entity
{
	Bar healthBar;
	private float health, maxHealth;
	public ArrayList<Tile> nearbyTiles = new ArrayList<>();

	public boolean displayHitbox = false;

	enum Direction
	{
		UP,
		DOWN,
		LEFT,
		RIGHT,
		UPLEFT,
		UPRIGHT,
		DOWNLEFT,
		DOWNRIGHT,
		STATIONARY
	}

	enum CreatureState
	{
		IDLE,
		SPAWN,
		DEAD,
		WALK,
		WALK_ATTACK,
		ATTACK;
	}

	CreatureState state;
	Direction direction = Direction.STATIONARY;


	public Creature(Game game, Animator anim, float hp, float x, float y)
	{
		super(game, anim, x, y, true);

		healthBar = new Bar(game, 80, 10, this, BarType.HEALTH);
		health = hp;
		maxHealth = hp;
		isStatic = false;
		hitboxXOffset = getAnimator().getWidth()/8;
		hitboxYOffset = 0;
		creatureHitbox();
	}

	private void creatureHitbox()
	{


		hitbox = new Rectangle2D.Float(x + hitboxXOffset, y + hitboxYOffset, getAnimator().getWidth() - 2 * hitboxXOffset, getAnimator().getHeight() - 2 * hitboxYOffset);

	}

	public void updateHealthBar()
	{
		healthBar.update();
	}

	public void drawHealthBar(Graphics g)
	{
		healthBar.render(g);
	}


	@Override
	public void update()
	{
		checkState();
		prepareMovement();
		checkCollsion();
		super.update();
	}

	public void checkState()
	{
		if(health <= 0)
		{
			state = CreatureState.DEAD;
		}
	}

	public void longUpdate()
	{
		ArrayList<Tile> temp = new ArrayList<>();
		for(Chunk c : game.chunkManager.LoadedChunks)
		{
			for(Tile t : c.tiles)
			{
				if(t.nearCreature(this))
				{
					temp.add(t);

				}
			}
		}
		nearbyTiles = temp;

	}

	public void checkCollsion()
	{
		setCanMoveUp(true);
		setCanMoveDown(true);
		setCanMoveRight(true);
		setCanMoveLeft(true);


		for(Tile t : nearbyTiles)
			if(t.canCollide())
				collsionWithTile(t);



	}
	public void checkDirection()
	{

		if(xVel > 0)
		{
			if(yVel == 0)
				direction = Direction.RIGHT;
			if(yVel >= 0)
				direction = Direction.DOWNRIGHT;
			else
				direction = Direction.UPRIGHT;
			return;
		}
		if(xVel < 0)
		{
			if(yVel == 0)
				direction = Direction.LEFT;
			if(yVel >= 0)
				direction = Direction.DOWNLEFT;
			else
				direction = Direction.UPLEFT;
			return;
		}
		if(yVel < 0)
		{
			direction = Direction.UP;
			return;
		}

		if(yVel > 0)
		{
			direction = Direction.DOWN;
			return;
		} else
			direction = Direction.STATIONARY;
	}
	public void collsionWithTile(Tile e)
	{
		checkDirection();
		Rectangle2D.Float testHitbox = new Rectangle2D.Float(hitbox.x + xVel, hitbox.y + yVel, (float)getHitbox().getWidth(), (float)getHitbox().getHeight());

		if(testHitbox.intersects(e.getHitbox()))
		{
			System.out.print("Colliding while trying to move " + direction);
			game.highlightedTile = e;
			switch(direction)
			{
			
				case UP :
					setCanMoveUp(false);
					setYWithHitbox(e.getY() + e.getHitbox().height+1);
					break;

				case DOWN :
					setCanMoveDown(false);
					setYWithHitbox(e.getHitbox().y - getHitbox().height-1);
					break;
				case LEFT :
					setCanMoveLeft(false);
					setXWithHitbox(e.getX() + e.getHitbox().width);
					break;
				case RIGHT :
					setCanMoveRight(false);
					setXWithHitbox(e.getX() - getHitbox().width);
					break;

				case DOWNLEFT :
				{
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x, hitbox.y + yVel, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveLeft(false);
						setXWithHitbox(e.getX() + e.getHitbox().width);
						break;
					}
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x + xVel, hitbox.y, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveDown(false);
						setYWithHitbox(e.getHitbox().y - getHitbox().height-1);
						break;
					}
					setCanMoveLeft(false);
					setXWithHitbox(e.getX() + e.getHitbox().width);
					setCanMoveDown(false);
					setYWithHitbox(e.getHitbox().y - getHitbox().height-1);
					
					
				}
				case DOWNRIGHT :
				{
					
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x, hitbox.y + yVel, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveRight(false);
						setXWithHitbox(e.getX() - getHitbox().width);
						break;
					}
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x + xVel, hitbox.y, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveDown(false);
						setYWithHitbox(e.getHitbox().y - getHitbox().height-1);
						break;
					}
					setCanMoveRight(false);
					setXWithHitbox(e.getX() - getHitbox().width);
					setCanMoveDown(false);
					setYWithHitbox(e.getHitbox().y - getHitbox().height-1);
					break;
				}

				case UPLEFT :
				{
					
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x, hitbox.y + yVel, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveLeft(false);
						setXWithHitbox(e.getX() + e.getHitbox().width);
						break;
					}
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x + xVel, hitbox.y, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveUp(false);
						setYWithHitbox(e.getY() + e.getHitbox().height+1);
						break;
					}
					setCanMoveLeft(false);
					setXWithHitbox(e.getX() + e.getHitbox().width);
					setCanMoveUp(false);
					setYWithHitbox(e.getY() + e.getHitbox().height+1);
					break;
					
				}
				case UPRIGHT:
				{
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x, hitbox.y + yVel, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveRight(false);
						setXWithHitbox(e.getX() - getHitbox().width);
						break;
					}
					
					testHitbox.setFrame(new Rectangle2D.Float(hitbox.x + xVel, hitbox.y, (float)getHitbox().getWidth(), (float)getHitbox().getHeight()));
					if(!testHitbox.intersects(e.getHitbox()))
					{
						setCanMoveUp(false);
						setYWithHitbox(e.getY() + e.getHitbox().height+1);
						break;
					}
					setCanMoveRight(false);
					setXWithHitbox(e.getX() - getHitbox().width);
					setCanMoveUp(false);
					setYWithHitbox(e.getY() + e.getHitbox().height+1);
					break;
					
				}

				default:
					break;


			}
		}


	}

	@Override
	public EntityType getType()
	{
		return EntityType.CREATURE;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float hp) {
		if(hp >= 0 && hp<=maxHealth)
			health = hp;
		else if(hp < 0)
		{
			health = 0;
		}

	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void damage(float dmg) {
		health -= dmg;
	}

	public void heal(float hp) {
		if(health + hp <= maxHealth)
			health += hp;
		else
			health = maxHealth;
	}

	public void kill()
	{
		health = 0;
	}
}
