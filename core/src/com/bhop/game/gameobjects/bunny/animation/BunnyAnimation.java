package com.bhop.game.gameobjects.bunny.animation;

import static com.bhop.game.gameobjects.bunny.CameraMovement.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.gameobjects.bunny.BunnyIsHitEventWatcher;
import com.bhop.game.gameobjects.bunny.dummy.DummyBunnyAnimation;
import com.bhop.game.gameobjects.coloroptions.ColorOption;
import com.bhop.game.gameobjects.coloroptions.ColorOption.BunnyColor;
import com.bhop.game.utils.ImageUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BunnyAnimation extends BunnyIsHitEventWatcher
{

    public static final int IMAGE_WIDTH = 96;

    public static final int IMAGE_HEIGHT = 96;

    private Map<Texture, RunSpeedBoost> speedBoostsForFrame;

    private Map<Texture, Set<PixelLocation>> imagePixelLocations;

	private static Map<BunnyColor, BunnyAnimations> colorToAnimations;

    private Texture[] hitImages;

    private Texture[] jumpImages;

    private Texture[] runImages;

    private final FrameCounter frameCounter;

    private final SpriteManager spriteManager;

    private Texture currentFrame;

    public BunnyAnimation()
    {
		initializeColorToAnimations();

        hitImages = createImageArrayFromDirectorySafely("bunny/" + BunnyColor.BONUS.getColorName() + "/hit");
        jumpImages = createImageArrayFromDirectorySafely("bunny/" + BunnyColor.BONUS.getColorName() + "/jump");
        runImages = createImageArrayFromDirectorySafely("bunny/" + BunnyColor.BONUS.getColorName() + "/run");
        frameCounter = new FrameCounter();
        spriteManager = new SpriteManager();

//		initializeSpeedBoostsForFrame();
//        initializeImagePixelLocations();
    }

	private void initializeColorToAnimations()
	{
		if (colorToAnimations == null)
		{
			colorToAnimations = new HashMap<BunnyColor, BunnyAnimations>();

			for (BunnyColor color : BunnyColor.values())
			{
				colorToAnimations.put(color, new BunnyAnimations(color));
//            createImageArrayFromDirectorySafely("bunny/" + color.getColorName() + "/hit");
//            createImageArrayFromDirectorySafely("bunny/" + color.getColorName() + "/jump");
//            createImageArrayFromDirectorySafely("bunny/" + color.getColorName() + "/run");
			}
		}
	}

	public void setAnimations(BunnyColor bunnyColor)
	{
		hitImages = colorToAnimations.get(bunnyColor).hitImages;
		jumpImages = colorToAnimations.get(bunnyColor).jumpImages;
		runImages = colorToAnimations.get(bunnyColor).runImages;

		speedBoostsForFrame = colorToAnimations.get(bunnyColor).speedBoostsForFrame;
		imagePixelLocations = colorToAnimations.get(bunnyColor).imagePixelLocations;

		currentFrame = runImages[DummyBunnyAnimation.getFrameIndex() >= runImages.length ? 0 : DummyBunnyAnimation.getFrameIndex()];
	}

    public void dispose()
    {
        disposeTextures(runImages);
        disposeTextures(jumpImages);
        disposeTextures(hitImages);
    }

    private static void disposeTextures(Texture[] textures)
    {
        for (Texture texture : textures)
        {
            texture.dispose();
        }
    }

	public void draw(SpriteBatch spriteBatch, float x, float y)
	{
		spriteBatch.draw(currentFrame, x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
	}

	public void update(float gravityForce, float y, float speedFactor, boolean isOnTopOfAnObject)
	{
		frameCounter.adjustSpeed(speedFactor);
		
		if (bunnyIsHit)
		{
			spriteManager.preciseHitFrame(gravityForce, y);
		}
		else
		{
			spriteManager.preciseJumpFrame(gravityForce, y, isOnTopOfAnObject);
		}
	}

	public RunSpeedBoost getSpeedBoost()
	{
		return speedBoostsForFrame.get(currentFrame);
	}
	
	public Set<PixelLocation> getCurrentFramePixelLocations()
	{
		return imagePixelLocations.get(currentFrame);
	}

	private class BunnyAnimations
	{
		private final Texture[] hitImages;

		private final Texture[] jumpImages;

		private final Texture[] runImages;

		private Map<Texture, RunSpeedBoost> speedBoostsForFrame;

		private Map<Texture, Set<PixelLocation>> imagePixelLocations;

		public BunnyAnimations(BunnyColor bunnyColor)
		{
			hitImages = createImageArrayFromDirectorySafely("bunny/" + bunnyColor.getColorName() + "/hit");
			jumpImages = createImageArrayFromDirectorySafely("bunny/" + bunnyColor.getColorName() + "/jump");
			runImages = createImageArrayFromDirectorySafely("bunny/" + bunnyColor.getColorName() + "/run");

			initializeSpeedBoostsForFrame();
			initializeImagePixelLocations();
		}

		private void initializeSpeedBoostsForFrame()
		{
			speedBoostsForFrame = new HashMap<Texture, RunSpeedBoost>();
			speedBoostsForFrame.put(runImages[1], RunSpeedBoost.MAX);
		}

		private void initializeImagePixelLocations()
		{
			imagePixelLocations = new HashMap<Texture, Set<PixelLocation>>();

			putPixelLocationsForImageInMap(hitImages, createImageArrayFromDirectorySafely("bunny/collision/hit"), "bunny/collision/hit/");
			putPixelLocationsForImageInMap(jumpImages, createImageArrayFromDirectorySafely("bunny/collision/jump"), "bunny/collision/jump/");
			putPixelLocationsForImageInMap(runImages, createImageArrayFromDirectorySafely("bunny/collision/run"), "bunny/collision/run/");
		}

		private void putPixelLocationsForImageInMap(Texture[] realImageArray, Texture[] collisionImageArray, String path)
		{
			FileHandle[] files = Gdx.files.internal("sprites/" + path).list();

			for (int i = 0; i < files.length; i++)
			{
				imagePixelLocations.put(realImageArray[i], ImageUtils.getPixelsLocations(path + files[i].name()));
			}
		}
	}
	
	private class SpriteManager
	{
		
		private void preciseHitFrame(float gravityForce, float y)
		{
		    if (gravityForce > 6)
			{
				currentFrame = hitImages[0];
			}
			else if (gravityForce > 3)
			{
				currentFrame = hitImages[1];
			}
			else if (gravityForce > 1)
			{
				currentFrame = hitImages[2];
			}
			else if (gravityForce > -2)
			{
				currentFrame = hitImages[3];
			}
			else if (gravityForce > -5)
			{
				currentFrame = hitImages[4];
			}
			else if (gravityForce > -7)
			{
				currentFrame = hitImages[5];
			}
			//else if (y > WINDOW_HEIGHT - 230)
            else if (y < 134)
			{
				currentFrame = hitImages[7];
			}
            //else if (y > WINDOW_HEIGHT - 250)
            else if (y < 154)
			{
				currentFrame = hitImages[6];
			}
		}

		private void preciseJumpFrame(float gravityForce, float y, boolean isOnTopOfAnObject)
	    {
		    if (gravityForce > 6)
			{
				currentFrame = jumpImages[0];
			}
			else if (gravityForce > 3)
			{
				currentFrame = jumpImages[1];
			}
			else if (gravityForce > 0)
			{
				currentFrame = jumpImages[2];
			}
			else if (gravityForce > -3)
			{
				currentFrame = jumpImages[3];
			}
			else if (isOnTopOfAnObject)
			{
				preciseLandSprite();
			}
			//else if (y > WINDOW_HEIGHT - 230)
            else if (y < 135)
			{
				currentFrame = jumpImages[5];
			}
			else
			{
				currentFrame = jumpImages[4];
			}
	    }

		private void preciseLandSprite()
		{
			if (currentFrame.equals(jumpImages[5]))
			{
				currentFrame = jumpImages[6];
			}
			else if (frameCounter.animationHasEnded())
			{
				currentFrame = getNextRunFrame();
			}
		}

		private Texture getNextRunFrame()
		{
			for (int i = 0; i < runImages.length - 1; i++)
			{
				if (currentFrame.equals(runImages[i]))
				{
					return runImages[i + 1];
				}
			}
			
			return runImages[0];
		}
		
	}

}
