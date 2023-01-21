package com.bhop.game.gameobjects.bunny;

import static com.bhop.game.gameobjects.bunny.animation.BunnyAnimation.IMAGE_HEIGHT;
import static com.bhop.game.gameobjects.bunny.animation.BunnyAnimation.IMAGE_WIDTH;

import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.gameobjects.booster.Booster;
import com.bhop.game.gameobjects.bunny.animation.BunnyAnimation;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.gameobjects.dustcloud.DustCloud;
import com.bhop.game.gameobjects.log.Log;
import com.bhop.game.gameobjects.log.LogGenerator;
import com.bhop.game.utils.singleton.SingletonManager;

public class CollisionChecker
{

	private final LogGenerator logGenerator;
	
	private final CarrotManager carrotGenerator;

	private final DustCloud dustCloud;
	
	private final Booster booster;
	
	CollisionChecker()
	{
		logGenerator = SingletonManager.getSingleton(LogGenerator.class);
		carrotGenerator = SingletonManager.getSingleton(CarrotManager.class);
		dustCloud = SingletonManager.getSingleton(DustCloud.class);
		booster = SingletonManager.getSingleton(Booster.class);
	}

	public boolean checkForCollision(float bunnyX, float bunnyY, BunnyAnimation animation)
	{
		checkForCarrotCollision(bunnyX, bunnyY, animation);
		checkForBoosterCollision(bunnyX, bunnyY, animation);
		
		return checkForLogCollision(bunnyX, bunnyY, animation);
	}

	private void checkForCarrotCollision(float bunnyX, float bunnyY, BunnyAnimation animation)
    {
	    if (checkForCollision(carrotGenerator.getCarrot(), bunnyX, bunnyY, animation))
		{
			carrotGenerator.alertBunnyTookCarrot();
		}
    }
	
	private void checkForBoosterCollision(float bunnyX, float bunnyY, BunnyAnimation animation)
	{
		if (checkForCollision(booster, bunnyX, bunnyY, animation))
		{
			booster.alertBunnyTookBooster();
		}
	}

	private boolean checkForLogCollision(float bunnyX, float bunnyY, BunnyAnimation animation)
    {
	    for (Collidable collidable : logGenerator.getAllLogs())
		{
			if (checkForCollision(collidable, bunnyX, bunnyY, animation))
			{
				dustCloud.setLog((Log) collidable);

				return true;
			}
		}
		
		return false;
    }

	private boolean checkForCollision(Collidable collidable, float bunnyX, float bunnyY, BunnyAnimation animation)
	{
		if (checkForImageCollision(collidable, bunnyX, bunnyY, animation))
		{
			return checkForPixelCollision(collidable, bunnyX, bunnyY, animation);
		}
		
		return false;
	}

	private boolean checkForImageCollision(Collidable collidable, float bunnyX, float bunnyY, BunnyAnimation animation)
	{
        boolean horizontalCollision = bunnyX + IMAGE_WIDTH >= collidable.getX() && bunnyX <= collidable.getX() + collidable.getImageWidth();
        boolean verticalCollision = bunnyY <= collidable.getY() + collidable.getImageHeight() && bunnyY + IMAGE_HEIGHT >= collidable.getY();

        return horizontalCollision && verticalCollision;
	}
	
	private boolean checkForPixelCollision(Collidable collidable, float bunnyX, float bunnyY, BunnyAnimation animation)
	{
		for (PixelLocation location : animation.getCurrentFramePixelLocations())
		{
			int x = (int) (bunnyX + location.getX() - collidable.getX());
			int y = (int) (bunnyY + location.getY() - collidable.getY());
			
			if (collidable.getImagePixelLocations().contains(new PixelLocation(x, y)))
			{
				return true;
			}
		}
		
		return false;
	}

}
