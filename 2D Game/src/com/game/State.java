package com.game;

import java.awt.Graphics;

public abstract class State 
{
	
	public abstract void render(Graphics g);
	public abstract void update() throws CloneNotSupportedException;
	
}
