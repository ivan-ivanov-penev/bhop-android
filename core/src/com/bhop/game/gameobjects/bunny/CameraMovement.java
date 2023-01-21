package com.bhop.game.gameobjects.bunny;

import com.bhop.game.gameobjects.booster.Booster;
import com.bhop.game.gameobjects.timecounter.GameEndWatcher;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class CameraMovement extends BunnyIsHitEventWatcher implements Singleton
{
	
	public static final float CAMERA_SPEED = 2.0f;

	public static final float MAX_SPEED_FACTOR = 3.0f;

	public static final float MIN_SPEED_FACTOR = 1;

    //private static final float SPEED_FACTOR_DECREMENT = 0.01f
    private static final float SPEED_FACTOR_DECREMENT = 0.02f;;
	
	private float speedFactor;

	private CameraMovement()
	{
		speedFactor = MIN_SPEED_FACTOR;
	}

	void increaseSpeedFactor(RunSpeedBoost runSpeedBoost)
	{
		speedFactor += runSpeedBoost.getSpeedFactor();

		if (speedFactor > MAX_SPEED_FACTOR)
		{
			speedFactor = MAX_SPEED_FACTOR;
		}
	}

	void decreaseSpeedFactor()
	{
	    speedFactor -= SPEED_FACTOR_DECREMENT;

		if (speedFactor < MIN_SPEED_FACTOR)
		{
			speedFactor = MIN_SPEED_FACTOR;
		}
	}

	@Override
	protected void bunnyHasRecovered()
	{
		if (bunnyIsHit)
		{
			speedFactor = MIN_SPEED_FACTOR;
		}
		
		bunnyIsHit = false;
	}

	float getSpeedFactor()
	{
		return speedFactor;
	}

	public float getMovementSpeed()
	{
		speedFactor = Booster.isBoosterAcquired() ? MAX_SPEED_FACTOR : speedFactor;
		
		float speed = CAMERA_SPEED * speedFactor * 2;
		float reverseSpeedFactor = speedFactor < MIN_SPEED_FACTOR * 1.5 ? -1.5f : -1f;
		
		return bunnyIsHit ? speed * reverseSpeedFactor: speed;
	}
	
	public float getCameraSpeed()
	{
		return bunnyIsHit ? -CAMERA_SPEED : CAMERA_SPEED;
	}

	public static enum RunSpeedBoost
	{

		MAX(1.25f),
		MIN(0.4f);

		private final float speedFactor;

		private RunSpeedBoost(float speedFactor)
		{
			this.speedFactor = speedFactor;
		}
		
		private float getSpeedFactor()
        {
	        return speedFactor;
        }

	}

}
