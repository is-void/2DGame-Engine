package com.game.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;

import com.game.Game;
import com.game.entities.creatures.Player;

public class MouseMovement extends MouseMotionAdapter
{
	Game game;
	Player player;

	public MouseMovement(Game game)
	{
		this.game = game;
		player = game.player;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Point2D.Float local = new Point2D.Float(e.getX(), e.getY());

		player.localMouseX = local.x;
		player.localMouseY = local.y;

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		Point2D.Float local = new Point2D.Float(e.getX(), e.getY());

		player.localMouseX = local.x;
		player.localMouseY = local.y;

	}
}
