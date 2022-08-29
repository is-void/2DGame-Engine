package com.game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.game.Game;
import com.game.display.Camera;
import com.game.entities.creatures.Creature;
import com.game.sprites.Animator;

public abstract class Entity
{
	protected Game game;
	protected float x;  //Game coordinates
	protected float y;
	protected int localX; //Screen Location x
	protected int localY; //Screen location Y
	protected float maxXVel;  //The maximum velocity
	protected float maxYVel;
	protected float xVel, yVel; // The actual velocity
	protected float xAcc, yAcc; //The actual acceleration
	protected float xMagAcc, yMagAcc; //MagAcc is the  magnitude of acceleration. Should only be a positive real number
	protected float friction;
	protected boolean slowDownX, slowDownY;

	protected Rectangle2D.Float hitbox;
	protected Animator animator;
	private boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;
	private Point2D.Float origin;
	private boolean canCollide;
	public boolean up, down, left, right;
	public boolean isStatic = false;
	public Chunk chunk;
	public int hitboxXOffset, hitboxYOffset;
	public boolean showHitbox = false;

	public boolean load = false;
	public boolean scheduledUpdate = true;

	public static int CollisionDistance = 1000;

	public enum EntityType
	{
		CREATURE,
		PLAYER,
		TILE;
	}

	EntityType type;


	private boolean isRendered = false;

	public Entity(Game game, boolean col)
	{
		this.game = game;
		canCollide = col;
		x = 0;
		y = 0;
		origin = new Point2D.Float(x, y);

	}
	public Entity(Game game, Animator anim, boolean col)
	{
		this.game = game;
		animator = anim;
		defaultHitbox();
		anim.setOwner(this);
		canCollide = col;
		x = 0;
		y = 0;
		origin = new Point2D.Float(x, y);

	}
	public Entity(Game game, Animator anim, float x2, float y2)
	{
		this.game = game;
		animator = anim;
		defaultHitbox();
		this.x = x2;
		this.y = y2;
		origin = new Point2D.Float(x2, y2);
		anim.setOwner(this);
		doCollision(false);
	}

	public Entity(Game game, Animator anim, int x, int y, boolean coll)
	{
		this.game = game;
		animator = anim;

		defaultHitbox();
		this.x = x;
		this.y = y;
		origin = new Point2D.Float(x, y);
		anim.setOwner(this);
		doCollision(coll);
	}

	public Chunk getChunk()
	{
		return chunk;
	}

	public void setChunk(Chunk c)
	{
		chunk = c;
	}


	public boolean nearCreature(Creature c)
	{
		if(Game.dist(origin, c.getOrigin()) <= CollisionDistance)
		{
			return true;
		}
		//System.out.println("Not nearby");
		return false;
	}

	protected void defaultHitbox()
	{
		hitbox = new Rectangle2D.Float(x, y, animator.getWidth(), animator.getHeight());
	}
	public void drawHitbox(Graphics g)
	{
		g.setColor(Color.red);
		g.drawRect((int) (Game.WIDTH/2 - (Camera.X-hitbox.x) * Camera.zoom), (int) (Game.HEIGHT/2 - (Camera.Y-hitbox.y) * Camera.zoom), (int) ((getHitbox().width) * Camera.zoom), (int) ( (getHitbox().height) * Camera.zoom));

	}

	public void drawSprite(Graphics g)
	{
			localX = (int) (Game.WIDTH/2 - (Camera.X-x) * Camera.zoom);
			localY = (int) (Game.HEIGHT/2 - (Camera.Y-y) * Camera.zoom);
			hitbox.x = x + hitboxXOffset;
			hitbox.y = y + hitboxYOffset;

			animator.draw(g);

			if(showHitbox)
			{
				drawHitbox(g);
			}

	}


	public void update()
	{
		getAnimator().update();
		if(!isStatic)
		{
			if(xVel > 0)
				if(!canMoveRight)
					xVel = 0;

			if(xVel < 0)
				if(!canMoveLeft)
					xVel = 0;


			if(yVel < 0)
				if(!canMoveUp)
					yVel = 0;

			if(yVel > 0)
				if(!canMoveDown)
					yVel = 0;

			x += xVel;
			y += yVel;

		}

		setOrigin(getX()+getAnimator().getWidth()/2, getY() + getAnimator().getHeight()/2);

	}

	public void prepareMovement()
	{
		if(up ^ down)
		{
			if(up)
				moveUp();

			if(down)
				moveDown();
		} else
		{
			slowDownY = true;
		}

		if(left ^ right)
		{
			if(left)
				moveLeft();
			if(right)
				moveRight();
		} else
		{
			slowDownX = true;
		}

		if(slowDownX)
			slowDownX();
		if(slowDownY)
			slowDownY();

		accelerate();



	}

	public void checkLoaded()
	{
		if(Game.dist(origin.x, origin.y, Camera.X, Camera.Y) < Game.WIDTH/2)
		{
			isRendered = true;
		} else
		{
			isRendered = false;
		}

	}

	public void setOrigin(float d, float e)
	{
		origin.setLocation(d, e);
	}

	public Point2D getOrigin()
	{
		if(origin != null)
			return origin;
		else
			return new Point2D.Float(getX(), getY());
	}

	public boolean isRendered()
	{
		return isRendered;
	}

	public Animator getAnimator()
	{
		return animator;
	}
	public int getLocalX() {
		return localX;
	}

	public int getLocalY() {
		return localY;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}


	public void setY(float y) {
		this.y = y;
	}
	public void setXWithHitbox(float x)
	{
		hitbox.x = x;
		this.x = hitbox.x-hitboxXOffset;
	}
	public void setYWithHitbox(float y)
	{
		hitbox.y = y;
		this.y = hitbox.y-hitboxYOffset;
	}
	public void setLocation(Point2D.Float point)
	{
		x = point.x;
		y = point.y;
	}
	public void moveUp()
	{
		if(canMoveUp)
		{
			slowDownY = false;
			yAcc = -yMagAcc;
		}
	}

	public void moveDown()
	{
		if(canMoveDown)
		{
			slowDownY = false;
			yAcc = yMagAcc;
		}

	}

	public void moveRight()
	{
		if(canMoveRight)
		{
			slowDownX = false;
			xAcc = xMagAcc;
		}
	}

	public void moveLeft()
	{
		if(canMoveLeft)
		{
			slowDownX = false;
			xAcc = -xMagAcc;
		}
	}

	public void slowDownX()
	{

		slowDownX = true;

		if(xVel > 0)
		{
			if(xVel - friction > 0)
				xAcc = friction * -1;
			else
				xAcc = xVel * -1;
		} else if (xVel < 0)
		{

			if(xVel + friction < 0)
				xAcc = friction;
			else
				xAcc = xVel * -1;
		} else
			xAcc = 0;
	}

	public void slowDownY()
	{
		slowDownY = true;

		if(yVel > 0)
		{
			if(yVel - friction > 0)
				yAcc = friction * -1;
			else
				yAcc = yVel * -1;
		} else if (yVel < 0)
		{

			if(yVel + friction < 0)
				yAcc = friction;
			else
				yAcc = yVel * -1;
		} else
			yAcc = 0;
	}

	private void accelerate()
	{


		if(Math.abs(xVel + xAcc) <= Math.abs(getMaxXVel()))
		{
			xVel += xAcc;
		}


		if(Math.abs(yVel + yAcc) <= Math.abs(getMaxYVel()))
		{
			yVel += yAcc;
		}


	}

	public boolean isAtPosition(Point p)
	{
		return hitbox.contains(p);
	}

	public void setSlowDownX(boolean slowDownX) {
		this.slowDownX = slowDownX;
	}

	public void setSlowDownY(boolean slowDownY) {
		this.slowDownY = slowDownY;
	}

	public float getMaxXVelocity() {
		return getMaxXVel();
	}

	public void setMaxXVelocity(float maxXVel) {
		this.setMaxXVel(maxXVel);
	}

	public float getMaxYVel() {
		return maxYVel;
	}

	public void setMaxYVelocity(float maxYVel) {
		this.setMaxYVel(maxYVel);
	}

	public float getXVelocity() {
		return xVel;
	}

	public void setXVelocity(float xVel) {
		this.xVel = xVel;
	}

	public float getYVelocity() {
		return yVel;
	}

	public void setYVelocity(float yVel) {
		this.yVel = yVel;
	}

	public float getXAcceleration() {
		return xAcc;
	}

	public void setXAcceleration(float xAcc) {
		this.xAcc = xAcc;
	}

	public float getyAcceleration() {
		return yAcc;
	}

	public void setyAcceleration(float yAcc) {
		this.yAcc = yAcc;
	}

	public float getMaxXVel() {
		return maxXVel;
	}

	public void setMaxXVel(float maxXVel) {
		this.maxXVel = maxXVel;
	}

	public void setMaxYVel(float maxYVel) {
		this.maxYVel = maxYVel;
	}

	public float getXMagAcc() {
		return xMagAcc;
	}

	public void setXMagAcc(float xMagAcc) {
		this.xMagAcc = xMagAcc;
	}

	public float getYMagAcc() {
		return yMagAcc;
	}

	public void setYMagAcc(float yMagAcc) {
		this.yMagAcc = yMagAcc;
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean getCanMoveLeft() {
		return canMoveLeft;
	}

	public void setCanMoveLeft(boolean canMoveLeft) {
		this.canMoveLeft = canMoveLeft;
	}

	public boolean getCanMoveRight() {
		return canMoveRight;
	}

	public void setCanMoveRight(boolean canMoveRight) {
		this.canMoveRight = canMoveRight;
	}

	public boolean getCanMoveUp() {
		return canMoveUp;
	}

	public void setCanMoveUp(boolean canMoveUp) {
		this.canMoveUp = canMoveUp;
	}

	public boolean getCanMoveDown() {
		return canMoveDown;
	}

	public void setCanMoveDown(boolean canMoveDown) {
		this.canMoveDown = canMoveDown;
	}


	public abstract EntityType getType();

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public void doCollision(boolean val)
	{
		canCollide = val;
	}

	public boolean canCollide()
	{
		return canCollide;
	}
}
