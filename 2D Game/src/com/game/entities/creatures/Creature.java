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
		super(game, anim, x, y);

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
		heal(0.1f);
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

			switch(direction)
			{
				case UP :
					setCanMoveUp(false);
					setYWithHitbox(e.getY() + e.getHitbox().height);
					break;

				case DOWN :
					setCanMoveDown(false);
					setYWithHitbox(e.getHitbox().y - getHitbox().height);
					break;
				case LEFT :
					System.out.print("left test");
					setCanMoveLeft(false);
					setXWithHitbox(e.getX() + e.getHitbox().width);
					break;
				case RIGHT :
					setCanMoveRight(false);
					setXWithHitbox(e.getX() - getHitbox().width);
					break;

				case DOWNLEFT :
				{
					Point2D.Float dl = new Point2D.Float();
					dl.setLocation(testHitbox.getMinX(), testHitbox.getMaxY());

					Point2D.Float urOther = new Point2D.Float();
					urOther.setLocation(e.getHitbox().getMaxX(), e.getHitbox().getMinY());

					float xDist = Math.abs(dl.x - urOther.x);
					float yDist = Math.abs(dl.y - urOther.y);

					if(xDist > yDist)
					{
						setCanMoveDown(false);
						setYWithHitbox(e.getHitbox().y - getHitbox().height);
						break;
					}
					if(yDist > xDist)
					{
						setCanMoveLeft(false);
						setXWithHitbox(e.getX() + e.getHitbox().width);
						break;
					}
					setCanMoveLeft(false);
					setXWithHitbox(e.getX() + e.getHitbox().width);
					setYWithHitbox(e.getHitbox().y - getHitbox().height);
					break;

				}
				case DOWNRIGHT :
				{
					Point2D.Float dr = new Point2D.Float();
					dr.setLocation(testHitbox.getMaxX(), testHitbox.getMaxY());

					Point2D.Float ulOther = new Point2D.Float();
					ulOther.setLocation(e.getHitbox().getMinX(), e.getHitbox().getMinY());

					float xDist = Math.abs(dr.x - ulOther.x);
					float yDist = Math.abs(dr.y - ulOther.y);

					if(xDist > yDist)
					{
						setCanMoveDown(false);
						setYWithHitbox(e.getHitbox().y - getHitbox().height);
						break;
					}
					if(yDist > xDist)
					{
						setCanMoveRight(false);
						setXWithHitbox(e.getX() - getHitbox().width);
						break;
					}
					setCanMoveRight(false);
					setXWithHitbox(e.getX() - getHitbox().width);
					setYWithHitbox(e.getHitbox().y - getHitbox().height);
					break;
				}

				case UPLEFT :
				{
					Point2D.Float ul = new Point2D.Float();
					ul.setLocation(testHitbox.getMinX(), testHitbox.getMinY());

					Point2D.Float drOther = new Point2D.Float();
					drOther.setLocation(e.getHitbox().getMaxX(), e.getHitbox().getMaxY());

					float xDist = Math.abs(ul.x - drOther.x);
					float yDist = Math.abs(ul.y - drOther.y);

					if(xDist < yDist)
					{
						setCanMoveLeft(false);
						setXWithHitbox(e.getX() + e.getHitbox().width);
						break;
					}
					if(yDist < xDist)
					{
						setCanMoveUp(false);
						setYWithHitbox(e.getY() + e.getHitbox().height);
						break;
					}
					setCanMoveLeft(false);
					setXWithHitbox(e.getX() + e.getHitbox().width);
					setYWithHitbox(e.getY() + e.getHitbox().height);
					break;

				}
				case UPRIGHT:
				{
					Point2D.Float ur = new Point2D.Float();
					ur.setLocation(testHitbox.getMaxX(), testHitbox.getMinY());

					Point2D.Float dlOther = new Point2D.Float();
					dlOther.setLocation(e.getHitbox().getMinX(), e.getHitbox().getMaxY());

					float xDist = Math.abs(ur.x - dlOther.x);
					float yDist = Math.abs(ur.y - dlOther.y);

					if(xDist < yDist)
					{
						setCanMoveRight(false);
						setXWithHitbox(e.getX() - getHitbox().width);
						break;
					}
					if(yDist < xDist)
					{
						setCanMoveUp(false);
						setYWithHitbox(e.getY() + e.getHitbox().height);
						break;
					}
					setCanMoveRight(false);
					setXWithHitbox(e.getX() - getHitbox().width);
					setYWithHitbox(e.getY() + e.getHitbox().height);
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
