package com.game.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.Game;
import com.game.display.Camera;
import com.game.entities.creatures.Creature;
import com.game.sprites.Animator;
import com.game.sprites.Sprite;

public abstract class Entity 
{
	
	private int x, y;  //Game coordinates
	private int localX; //Screen Location x
	private int localY; //Screen location Y
	private double maxXVel;  //The maximum velocity
	private double maxYVel;
	private double xVel, yVel; // The actual velocity
	private double xAcc, yAcc; //The actual acceleration
	private double xMagAcc, yMagAcc; //MagAcc is the  magnitude of acceleration. Should only be a positive real number 
	private boolean slowDownX, slowDownY;
	private Rectangle hitbox;
	private Animator animator;
	private boolean canMoveLeft = true, canMoveRight = true, canMoveUp = true, canMoveDown = true;
	private Dimension origin;

	
	
	private boolean isRendered;
	
	public Entity(Animator anim, int x, int y)
	{
		animator = anim;
		defautHitbox();
		this.x = x;
		this.y = y;
		origin = new Dimension(x, y);
		anim.setOwner(this);
	}
	
	public boolean nearCreature(Creature c)
	{
		if(Game.dist(new Dimension(x, y), c.getOrigin()) <= 200)
		{
			return true;
		}
		//System.out.println("Not nearby");
		return false;
	}
	
	private void defautHitbox()
	{
		hitbox = new Rectangle(x, y, animator.getHeight(), animator.getWidth());
	}
	public void drawHitbox(Graphics g)
	{
		g.setColor(Color.red);
		g.drawRect(getHitbox().x, getHitbox().y, getHitbox().width, getHitbox().height);
		
	}
	
	public void drawSprite(Graphics g)
	{
			localX = Game.WIDTH/2 - (Camera.X-x);
			localY = Game.HEIGHT/2 - (Camera.Y-y);
			hitbox.x = localX;
			hitbox.y = localY;
			animator.draw(g);
			
	}
	
	public void render(Graphics g)
	{
		//drawHitbox(g);
		drawSprite(g);
		
	}
	public void update()
	{
		getAnimator().update();
		
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
		
		
		setOrigin(getX()+getAnimator().getWidth()/2, getY() + getAnimator().getHeight()/2);
		
	}
	
	public void prepareMovement()
	{
		if(slowDownX)
			slowDownX();
		if(slowDownY)
			slowDownY();
		accelerate();
		
		
	}
	
	public void checkLoaded()
	{
		if(Game.dist(this.x, this.y, Camera.X, Camera.Y) < Game.WIDTH)
		{
			isRendered = true;
		} else
		{
			isRendered = false;
		}
		
	}
	
	public void setOrigin(int x, int y)
	{
		origin.setSize(x, y);
	}
	
	public Dimension getOrigin()
	{
		if(origin != null)
			return origin;
		else
			return new Dimension(getX(), getY());
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setX(double x) {
		this.x = (int) x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setY(double y) {
		this.y = (int)y;
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
			if(xVel - xAcc > 0)
				xVel -= xAcc;
			else
				xVel = 0;
		} else if (xVel < 0)
		{
			if(Math.abs(xVel) - xMagAcc > 0)
				xVel += xMagAcc;
			else
				xVel = 0;
		}
	}
	
	public void slowDownY()
	{
		slowDownY = true;
		if(yVel > 0)
		{
			if(yVel - yMagAcc > 0)
				yVel -= yMagAcc;
			else
				yVel = 0;
		} else if (yVel < 0)
		{
			if(Math.abs(yVel) - yMagAcc > 0)
				yVel += yMagAcc;
			else
				yVel = 0;
		}
	}
	
	private void accelerate()
	{
				
		if(!slowDownX)
			if(Math.abs(xVel) < maxXVel)
			{
				
				if(xVel + xAcc < getMaxXVel())
				{
					xVel += xAcc; 
				}
				else if((xVel + xAcc) * -1 < getMaxXVel())
					xVel += xAcc;
				else
					xVel = maxXVel;
			}
		
		if(!slowDownY)
			if(Math.abs(yVel) < maxYVel)
			{
				if(yVel + yAcc < getMaxYVel())
				{
					yVel += yAcc; 
				}
				else if((yVel + yAcc) * -1 < getMaxYVel())
					yVel += yAcc;
				else
					yVel = maxYVel;
			}
		
	}

	public void setSlowDownX(boolean slowDownX) {
		this.slowDownX = slowDownX;
	}

	public void setSlowDownY(boolean slowDownY) {
		this.slowDownY = slowDownY;
	}

	public double getMaxXVelocity() {
		return getMaxXVel();
	}

	public void setMaxXVelocity(double maxXVel) {
		this.setMaxXVel(maxXVel);
	}

	public double getMaxYVel() {
		return maxYVel;
	}

	public void setMaxYVelocity(double maxYVel) {
		this.setMaxYVel(maxYVel);
	}

	public double getXVelocity() {
		return xVel;
	}

	public void setXVelocity(double xVel) {
		this.xVel = xVel;
	}

	public double getYVelocity() {
		return yVel;
	}

	public void setYVelocity(double yVel) {
		this.yVel = yVel;
	}

	public double getXAcceleration() {
		return xAcc;
	}

	public void setXAcceleration(double xAcc) {
		this.xAcc = xAcc;
	}

	public double getyAcceleration() {
		return yAcc;
	}

	public void setyAcceleration(double yAcc) {
		this.yAcc = yAcc;
	}

	public double getMaxXVel() {
		return maxXVel;
	}

	public void setMaxXVel(double maxXVel) {
		this.maxXVel = maxXVel;
	}

	public void setMaxYVel(double maxYVel) {
		this.maxYVel = maxYVel;
	}
	
	public double getXMagAcc() {
		return xMagAcc;
	}

	public void setXMagAcc(double xMagAcc) {
		this.xMagAcc = xMagAcc;
	}

	public double getYMagAcc() {
		return yMagAcc;
	}

	public void setYMagAcc(double yMagAcc) {
		this.yMagAcc = yMagAcc;
	}

	public Rectangle getHitbox() {
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

		
	public abstract String getType();
}
