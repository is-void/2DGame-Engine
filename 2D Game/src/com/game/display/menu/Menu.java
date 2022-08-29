package com.game.display.menu;

import java.awt.Graphics;

import com.game.display.ui.UIElement;

public class Menu
{
	UIElement[] menuElements;

	public Menu(UIElement[] menuElements)
	{
		this.menuElements = menuElements;
	}

	public void displayMenu(Graphics g)
	{
		for(UIElement e : menuElements)
		{
			e.render(g);
		}
	}

	public void updateMenu()
	{
		for(UIElement e : menuElements)
		{
			e.update();
		}
	}
}
