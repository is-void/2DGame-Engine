package com.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.game.Game;

public class ImageLoader {

	public static BufferedImage loadImage(String path){
		try {
			
			return ImageIO.read(ImageLoader.class.getResourceAsStream(Game.HierarichalFile(path)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}