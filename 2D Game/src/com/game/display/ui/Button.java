package com.game.display.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.game.Game;
import com.game.sprites.Animator;

public class Button extends UIWidget
{

	Object actionOwner;
	Method hoverAction;

	boolean hasMouseDown;
	int hoverChecker;

	public Button(UIManager uiManager, int x, int y, int width, int height)
	{
		super(uiManager);
		init(x, y, width, height);

	}

	public Button(Animator animator, UIManager uiManager, int x, int y, int width, int height, Method action, Method hoverAction)
	{
		super(animator, uiManager);
		this.action = action;
		init(x, y, width, height);
	}
	public Button(Animator animator, UIManager uiManager, int x, int y, int width, int height)
	{
		super(animator, uiManager);
		this.action = null;
		
		init(x, y, width, height);
	}
	
	
	public Button(Animator animator, UIManager uiManager, int x, int y, int width, int height, Method action, Object owner)
	{
		super(animator, uiManager);
		this.action = action;
		if(owner != null)
		{
			actionOwner = owner;
		}
		if(actionOwner == null)
		{
			actionOwner = this;
		}
		init(x, y, width, height);
	}
	
	

	public Button(UIManager uiManager, String text, int x, int y, int width, int height, Method action)
	{
		super(uiManager);
		this.action = action;
		init(x, y, width, height);
	}

	public Button(UIManager uiManager, String text, int x, int y, int width, int height, Method action, Method hoverAction)
	{
		super(uiManager);
		this.action = action;
		this.hoverAction = hoverAction;
		init(x, y, width, height);
	}


	public void init(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		widgetHitbox = new Rectangle(x, y, width, height);

	}
	public void onHover()
	{

		if(hoverAction != null)
		{
			try {
				hoverAction.invoke(this, this);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean hitBoxContainsMouse()
	{
		if(widgetHitbox.contains(game.player.localMouseX, game.player.localMouseY))
		{
			return true;
		}
		return false;
	}




	@Override
	public void render(Graphics g)
	{

		if(animator != null)
		{

			animator.draw(g);
		} else
		{
			g.setColor(Color.cyan);
			g.fillRect(x, y, width, height);
			g.drawString(text, x + width/3, y + height/3);
		}
	}




	@Override
	public void update()
	{
		if(hitBoxContainsMouse())
		{
			onHover();

			if(game.player.leftMouse)
			{

				hasMouseDown = true;

			}


			if(hasMouseDown && !game.player.leftMouse)
			{
				if(action != null)
				{
					try {
						action.invoke(actionOwner, (UIWidget) (this));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						Game.ShowErrorMessageBox(e);
						e.printStackTrace();
					}
				} 
			}
		} else
		{
			hasMouseDown = false;
		}


	}



}
