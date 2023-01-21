package com.bhop.game.gameobjects.booster;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.createTexture;

import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.gameobjects.bunny.Collidable;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.gameobjects.pauseicon.PauseIcon;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.gameobjects.timecounter.GameEndWatcher;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.SoundUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

@SingletonClass
public class Booster extends BasicGameObject implements Singleton, Collidable
{
	
	private static final int SPAWNING_INTERVAL_IN_SECONDS = 2;
	
	private static volatile boolean boosterAcquired;
	
	private final Set<PixelLocation> pixelLocations;
	
	private final CarrotManager carrotManager;
	
	private final BoosterExpireWatcher boosterExpireWatcher;
	
	private final BoosterAnimation animation;

    private final SoundPlayer soundPlayer;
	
	private boolean generateBooster;
	
	private int frameCounter;
	
	private Booster()
	{
		super(createTexture("booster/booster_lettuce_and_border"), new Vector2());

        boosterAcquired = false;
		
		coordinates.y = WINDOW_HEIGHT * 0.35f + image.getHeight();
        //coordinates.y = WINDOW_HEIGHT * 0.65f;
		
		pixelLocations = ImageUtils.getPixelsLocations("booster/booster_lettuce_border.png");
		
		carrotManager = SingletonManager.getSingleton(CarrotManager.class);
		
		boosterExpireWatcher = new BoosterExpireWatcher();
		
		animation = new BoosterAnimation();

        soundPlayer = new SoundPlayer("booster_collect.wav");
	}
	
	public static boolean isBoosterAcquired()
	{
		return boosterAcquired;
	}

    public static void alertGameIsRestarting()
    {
        boosterAcquired = false;
    }
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		boosterExpireWatcher.update();
		
		incrementFrameCounter();
		
		attemptToRemoveBooster();
		
		attemptUpdateBooster(delta, touchPoint);
	}

	private void attemptUpdateBooster(float delta, Vector3 touchPoint)
	{
		if (generateBooster)
		{
			super.update(delta, touchPoint);
		}
		else if (carrotManager.gameHasBegan())
		{
			attemptGenerateBooster();
		}
	}
	
	private void attemptGenerateBooster()
	{
		if (frameCounter == FPS * SPAWNING_INTERVAL_IN_SECONDS && RANDOM.nextInt(40) == 0)
        //if (frameCounter == FPS * SPAWNING_INTERVAL_IN_SECONDS && RANDOM.nextInt(2) == 0)
		{
			frameCounter = 0;
			
			generateBooster = true;
			
			coordinates.x = WINDOW_WIDTH * 2;
		}
	}
	
	private void attemptToRemoveBooster()
	{
		if (coordinates.x < -WINDOW_WIDTH)
		{
			generateBooster = false;
		}
	}
	
	private void incrementFrameCounter()
	{
		frameCounter += 1;
		
		if (frameCounter > FPS * SPAWNING_INTERVAL_IN_SECONDS)
		{
			frameCounter = 0;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		if (generateBooster && !boosterAcquired)
		{
			animation.render(spriteBatch);
		}
	}

	@Override
	public Set<PixelLocation> getImagePixelLocations()
	{
		return pixelLocations;
	}
	
	public void alertBunnyTookBooster()
	{
		soundPlayer.playSoundContinuously();

		boosterAcquired = true;
		
		coordinates.x = -image.getWidth() *1000;
	}
	
	private class BoosterExpireWatcher
	{
		
		private static final int BOOSTER_DURATION_IN_SECONDS = 10;
		
		private int frameCounter;
		
		private void update()
		{
			if (boosterAcquired)
			{
				incrementFrameCounter();
			}
		}
		
		private void incrementFrameCounter()
		{
			frameCounter += 1;
			
			if (GameEndWatcher.isGameEnd() || frameCounter > FPS * BOOSTER_DURATION_IN_SECONDS)
			{
				frameCounter = 0;
				
				boosterAcquired = false;
				
				generateBooster = false;
			}
		}
		
	}
	
	private class BoosterAnimation
	{
		
		private float scale;
		
		private boolean incrementImageSize;

        private float rotation;
		
		private BoosterAnimation()
        {
			scale = 1.0f;
			incrementImageSize = true;;
        }
		
		private void render(SpriteBatch spriteBatch)
		{
			if (!PauseIcon.isGamePaused() && !GameEndWatcher.isGameEnd())
			{
				changeScale();
				
				//image.rotate(1);
                rotation += 1;
			}

			//image.setCenterOfRotation(image.getWidth() * scale / 2, image.getHeight() * scale / 2);
			//image.draw(x, y, scale);
            TextureRegion textureRegion = new TextureRegion(image);

            spriteBatch.draw(textureRegion, coordinates.x, coordinates.y,
                    image.getWidth() * 0.5f, image.getHeight() * 0.5f,
                    image.getWidth(), image.getHeight(),
                    scale, scale, rotation);
		}

		private void changeScale()
        {
			scale += incrementImageSize ? 0.004f : -0.004f;
			
			if (scale > 1.2f)
			{
				incrementImageSize = false;
			}
			else if (scale < 1.0f)
			{
				incrementImageSize = true;
			}
        }
		
	}

}
