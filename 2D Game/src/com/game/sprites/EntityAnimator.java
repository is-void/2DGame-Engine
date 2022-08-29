package com.game.sprites;

import com.game.entities.Entity;

public class EntityAnimator extends Animator
{

	public EntityAnimator( int w,int h, Sprite spr)
	{
		super( spr);

	}

	public EntityAnimator( int w,int h, Sprite spr, Entity owner)
	{
		super(spr, owner);

	}




}
