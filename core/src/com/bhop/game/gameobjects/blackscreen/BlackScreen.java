package com.bhop.game.gameobjects.blackscreen;

import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.timecounter.GameEndWatcher;
import com.bhop.game.utils.singleton.Singleton;

public class BlackScreen implements Singleton, GameObject
{
	
	private static final float INCREMENT_VALUE = 0.01f;

	private final Texture blackScreen;
	
	private float transpeencyIncrementor;
	
	private BlackScreen()
	{
		blackScreen = createTexture("black_screen/screen");
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint) {}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		if (GameEndWatcher.isGameEnd())
		{
            spriteBatch.setColor(1, 1, 1, getTranspeencyIncrementor());

			//blackScreen.draw(0, 0, new Color(1, 1, 1, getTranspeencyIncrementor()));
            spriteBatch.draw(blackScreen, 0, 0);

            spriteBatch.setColor(1, 1, 1, 1);
		}
	}
	
	private float getTranspeencyIncrementor()
	{
		if (transpeencyIncrementor < 1)
		{
			transpeencyIncrementor += INCREMENT_VALUE;
		}

        if (transpeencyIncrementor > 1)
        {
            transpeencyIncrementor = 1;
        }
		
		return transpeencyIncrementor;
	}

    @Override
    public void dispose()
    {
        blackScreen.dispose();
    }
}
