package com.bhop.game.gameobjects.bunny.dummy;

import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class DummyBunnyAnimation
{
	
	private final Texture[] runImages;
	
	private Texture currentFrame;
	
	private static int frameCounter;
	
	private static int frameIndex;

	DummyBunnyAnimation()
	{
		runImages = createImageArrayFromDirectorySafely("bunny/bonus/run");
		
		currentFrame = runImages[0];
	}

	public Texture getCurrentFrame()
	{
		return currentFrame;
	}

	public void dispose()
	{
		for (Texture runImage : runImages)
		{
			runImage.dispose();
		}
	}

	public void update(float delta, Vector3 touchPoint)
	{
		frameCounter += 1;
		
		if (frameCounter > 3)
		{
			currentFrame = getNextRunFrame();
			
			frameCounter = 0;
		}
	}

	private Texture getNextRunFrame()
	{
		for (int i = 0; i < runImages.length - 1; i++)
		{
			if (currentFrame.equals(runImages[i]))
			{
				frameIndex += 1;
				
				return runImages[i + 1];
			}
		}
		
		frameIndex = 0;
		
		return runImages[0];
	}
	
	public static int getFrameCounter()
	{
		return frameCounter;
	}
	
	public static int getFrameIndex()
	{
		return frameIndex;
	}

}
