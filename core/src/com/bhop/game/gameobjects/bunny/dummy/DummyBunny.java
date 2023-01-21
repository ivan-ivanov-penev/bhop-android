package com.bhop.game.gameobjects.bunny.dummy;

import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class DummyBunny implements GameObject, Singleton
{
	
	private float x;

	private float y;
	
	private final DummyBunnyAnimation dummyBunnyAnimation;

	public DummyBunny()
	{
		dummyBunnyAnimation = new DummyBunnyAnimation();

		x = BUNNY_STARTING_X;
		y = 119;
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		spriteBatch.draw(dummyBunnyAnimation.getCurrentFrame(), x, y);
	}

	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		dummyBunnyAnimation.update(delta, touchPoint);
	}

	@Override
	public void dispose()
	{
		dummyBunnyAnimation.dispose();
	}
}
