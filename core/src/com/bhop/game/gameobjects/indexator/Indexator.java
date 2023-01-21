package com.bhop.game.gameobjects.indexator;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.bunny.CameraMovement;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.gameobjects.timecounter.TimeCounter;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

@SingletonClass
public class Indexator implements GameObject, Singleton
{
	
	private final TimeCounter timeCounter;
	
	private final CarrotManager carrotManager;
	
	private final CameraMovement cameraMovement;
	
	private final Texture happy;
	
	private final Texture angry;
	
	private Texture renderImage;
	
	private int frameCounter;
	
	private Indexator()
    {
		timeCounter = SingletonManager.getSingleton(TimeCounter.class);
		carrotManager = SingletonManager.getSingleton(CarrotManager.class);
		cameraMovement = SingletonManager.getSingleton(CameraMovement.class);
		
		happy = createTexture("indexator/indexator_happy");
		angry = createTexture("indexator/indexator_angry");
		renderImage = happy;
    }

	@Override
    public void update(float delta, Vector3 touchPoint)
    {
		if (cameraMovement.getMovementSpeed() < 0)
		{
			renderImage = angry;
		}
		else if (frameCounter == FPS)
		{
			frameCounter = 0;
			
			renderAccordinglyToPlayerProgress(cameraMovement.getMovementSpeed() * timeCounter.getSecondsLeft() * FPS > carrotManager.getCarrot().getX());
		}
		else
		{
			frameCounter++;
		}
    }

	@Override
    public void render(SpriteBatch spriteBatch)
    {
        float scale = 0.85f;

        spriteBatch.draw(renderImage, WINDOW_WIDTH / 1.32f, WINDOW_HEIGHT - renderImage.getHeight() * scale, renderImage.getWidth() * scale, renderImage.getHeight() * scale);
		//renderImage.draw(WINDOW_WIDTH / 1.3f, 0, 0.75f);
    }

	private void renderAccordinglyToPlayerProgress(boolean isOnTime)
    {
	    if (isOnTime)
		{
			renderImage = happy;
		}
		else
		{
			renderImage = angry;
		}
    }

    @Override
    public void dispose()
    {
        happy.dispose();
        angry.dispose();
    }
}
