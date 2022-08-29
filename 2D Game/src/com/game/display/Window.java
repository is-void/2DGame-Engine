package com.game.display;


import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.game.Game;

public class Window
{
	public Window(String title, Game game)
	{
		JFrame frame = new JFrame(title);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


		frame.setCursor(frame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),"null"));


		try {
			game.start();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
