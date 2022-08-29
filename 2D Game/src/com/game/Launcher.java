package com.game;

public class Launcher
{
	public static void main(String[] args)
	{
		 try {
			 System.out.print("running");
			new Game();
		  } catch (Throwable t) {
		   Game.ShowErrorMessageBox(t);
		    t.printStackTrace();
		    throw t; // don't suppress Throwable
		  }

	}
}
