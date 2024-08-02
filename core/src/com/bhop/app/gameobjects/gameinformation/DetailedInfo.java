package com.bhop.app.gameobjects.gameinformation;

import static com.bhop.app.utils.GameUtils.*;
import static com.bhop.app.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.app.gameobjects.BasicGameObject;
import com.bhop.app.utils.singleton.Singleton;
import com.bhop.app.utils.singleton.SingletonClass;

@SingletonClass
public class DetailedInfo extends BasicGameObject implements Singleton
{
	
	private DetailedInfo()
	{
		super(createTexture("game_information/detailed_info1"), new Vector2());

		coordinates.x = (WINDOW_WIDTH - image.getWidth()) / 2;
		coordinates.y = 0;
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint) {}
	
	@Override
	public void render(SpriteBatch spriteBatch)
	{
		if (InfoIcon.isPlayerIsReadingInfo())
		{
			super.render(spriteBatch);
		}
	}

}
