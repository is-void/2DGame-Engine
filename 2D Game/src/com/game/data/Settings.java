package com.game.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import com.game.Game;
import com.game.entities.Entity;

public class Settings
{
	Game game;
	File settings;
	InputStream stream;

	public Settings(Game game, String path) throws FileNotFoundException
	{

		this.game = game;

		stream = Settings.class.getResourceAsStream(Game.HierarichalFile(path));
		System.out.print(stream);

		loadFile();
	}

	public void loadFile() throws FileNotFoundException
	{
		Scanner s;
		s = new Scanner(stream);

		while(s.hasNextLine())
		{
			String vari;
			String value;

			String line = s.nextLine();
			int start = line.indexOf(": ");

			vari = line.substring(0, start);
			value = line.substring(start+2);

			switch(vari)
			{
				case "DISPLAY WIDTH":
					Game.WIDTH = Integer.parseInt(value);
					break;
				case "DISPLAY HEIGHT" :
					Game.HEIGHT = Integer.parseInt(value);
					break;

				case "COLLISION DISTANCE" :
					Entity.CollisionDistance = Integer.parseInt(value);
					break;

				default :
					System.out.print("Illegal Settings Argument Exception");
			}

		}
		s.close();
	}
}
