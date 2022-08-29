package com.game.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage sheet;

	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}

	public BufferedImage crop(int x, int y, int width, int height){
		return sheet.getSubimage(x, y, width, height);
	}

	public BufferedImage[] getAnimation(int count, int row, int width, int height)
	{
		BufferedImage frames[] = new BufferedImage[count];
		try
		 {

			 for(int x = 0; x <= (count-1) * width; x+=width )
			 {
				 frames[x/width] = crop(x, row * width, width, height);
			 }

		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }

		 return frames;
	}

	public BufferedImage[] getAnimation(int count, int row, int col, int width, int height)
	{
		BufferedImage frames[] = new BufferedImage[count];
		try
		 {

			 for(int x = col; x <= (count-1) * width; x+=width )
			 {
				 frames[x/width] = crop(x, row * width, width, height);
			 }

		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }

		 return frames;
	}
}