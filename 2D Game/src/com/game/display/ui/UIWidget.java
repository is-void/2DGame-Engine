package com.game.display.ui;

import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.game.Game;
import com.game.sprites.Animator;

public abstract class UIWidget implements UIElement
{
	public Game game;
	protected Method action;
	UIManager uiManager;
	public int x;
	public int y;
	public int width;
	public int height;
	public Rectangle2D widgetHitbox;
	public Animator animator;
	public String text;


	public UIWidget(Animator animator, UIManager uiManager)
	{
		this.animator = animator;
		animator.setOwner(this);
		this.uiManager = uiManager;
		this.game = uiManager.game;

	}
	public UIWidget(UIManager uiManager)
	{
		this.uiManager = uiManager;
		this.game = uiManager.game;
	}

	public void onClick()
	{
		if(action != null)
		{
			try
			{
				action.invoke(this);
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setOnClick(Method method)
	{
		action = method;
	}







}
