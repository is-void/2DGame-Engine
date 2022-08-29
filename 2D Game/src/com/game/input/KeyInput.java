package com.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.game.Game;
import com.game.GameState.GAMESTATE;
import com.game.entities.Chunk;


public class KeyInput extends KeyAdapter
{
	private Game game;

	private boolean pauseToggle = true;

	public KeyInput(Game game)
	{
		this.game = game;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{

		int key = e.getKeyCode();

		if(key == KeyEvent.VK_ESCAPE && pauseToggle)
		{
			pauseToggle = false;
			if(game.state == GAMESTATE.RUNNING)
			{
				game.state = GAMESTATE.PAUSED;

			} else if(game.state == GAMESTATE.PAUSED)
			{
				game.state = GAMESTATE.RUNNING;
			}
		}


		//player.up pressed
		if(key == KeyEvent.VK_W)
		{
			game.player.up = true;
		}

		//player.down pressed
		if(key == KeyEvent.VK_S)
		{
			game.player.down = true;
		}

		//player.right pressed
		if(key == KeyEvent.VK_D)
		{
			game.player.right = true;
		}

		//player.left pressed
		if(key == KeyEvent.VK_A)
		{
			game.player.left = true;
		}

		if(key == KeyEvent.VK_UP)
			game.gameCamera.zoomIn = true;


		if(key == KeyEvent.VK_DOWN)
			game.gameCamera.zoomOut = true;

		if(key == KeyEvent.VK_F3)
		{

			if(game.player != null && game.chunkManager != null)
				game.gameState.setStateToRunning();
			else
				System.out.println("\nChunkManager or player is null");
		}



	}

	@Override
	public void keyReleased(KeyEvent e)
	{

		int key = e.getKeyCode();
		if(key == KeyEvent.VK_H)
		{
			if(!Chunk.showHitbox)
				Chunk.showHitbox = true;
			else
				Chunk.showHitbox = false;
		}
		//player.up released


		if(key == KeyEvent.VK_W)
		{
			game.player.up = false;
		}

		//player.down released
		if (key ==  KeyEvent.VK_S)
		{
			game.player.down = false;
		}

		//player.left released
		if (key == KeyEvent.VK_A)
		{
			game.player.left = false;
		}

		//player.right released
		if (key == KeyEvent.VK_D)
		{
			game.player.right = false;
		}

		if(key == KeyEvent.VK_UP)
			game.gameCamera.zoomIn = false;


		if(key == KeyEvent.VK_DOWN)
			game.gameCamera.zoomOut = false;

		if(key == KeyEvent.VK_ESCAPE)
		{
			pauseToggle = true;
		}

	}
}
