package com.bhop.game.gameobjects.carrot;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.gameobjects.timecounter.TimeCounter;
import com.bhop.game.infoproviders.bonusbackground.BonusBackgroundUnlocker;
import com.bhop.game.infoproviders.bonuscolor.BonusSkinUnlocker;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.animation.Animation;
import com.bhop.game.utils.font.Font;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SingletonClass
public class CarrotManager implements GameObject, Singleton
{
	
	private final TimeCounter timeCounter;
	
	private final BonusSkinUnlocker bonusUnlocker;
	
	private final BonusBackgroundUnlocker backgroundUnlocker;
	
	private final Font fontType;
	
	private final DistanceIncrementFactor distanceIncrementFactor;
	
	private final DistanceIndexator distanceIndexator;
	
	private final CarrotIcon carrotIcon;
	
	private final SoundPlayer soundPlayer;

    private final Animation animation;

    private final Map<Integer, Set<PixelLocation>> pixelLocations;
	
	private Carrot carrot;
	
	private int carrotCounter;
	
	private CarrotManager()
	{
		timeCounter = SingletonManager.getSingleton(TimeCounter.class);
		bonusUnlocker = SingletonManager.getSingleton(BonusSkinUnlocker.class);
		backgroundUnlocker = SingletonManager.getSingleton(BonusBackgroundUnlocker.class);
		distanceIndexator = SingletonManager.getSingleton(DistanceIndexator.class);
		//fontType = new TrueTypeFont(new Font(FONT_TYPE, STYLE, 30), true);
        fontType = new Font("").setSize(30).setColor(Color.WHITE);
		carrotIcon = new CarrotIcon();
        animation = new Animation(createImageArrayFromDirectorySafely("carrot/animation"), 6);
        pixelLocations = new HashMap<Integer, Set<PixelLocation>>();

        fillPixelLocations();

		carrot = new Carrot(WINDOW_WIDTH * 1.8f, pixelLocations, animation);
		distanceIncrementFactor = new DistanceIncrementFactor(carrot.getX());
		distanceIndexator.setDistanceToNextCarrot(carrot.getX());
		soundPlayer = new SoundPlayer("carrot_collect.wav");
	}

    private void fillPixelLocations()
    {
        for (int i = 0; i < animation.getFrameCount(); i++)
        {
            pixelLocations.put(i, ImageUtils.getPixelsLocations("carrot/collision/" + i + "_carrot_border.png"));
        }
    }
	
	public Carrot getCarrot()
    {
	    return carrot;
    }
	
	public int getCarrotCounter()
	{
		return carrotCounter;
	}
	
	public void alertBunnyTookCarrot()
	{
		carrotIcon.alertBunnyPickedCarrot();
		carrotCounter ++;
		backgroundUnlocker.alertBunnyHasPickedUpCarrot();

		spawnNewCarrot();
		checkBonusColorUnclock();
		
		distanceIndexator.setDistanceToNextCarrot(carrot.getX());
		soundPlayer.playSoundOnce();
	}
	
	private void checkBonusColorUnclock()
	{
		if (playerJustUnlockedBonus())
		{
			bonusUnlocker.unlockBonus();
		}
	}
	
	private void spawnNewCarrot()
	{
		float x = distanceIncrementFactor.getNextCarrotSpawnDistance();

		carrot = new Carrot(x, pixelLocations, animation);
		timeCounter.setTimeLeft(x);
		
		soundPlayer.alertSoundHasToBePlayed();
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		checkIfCarrotIsMissed();
		
		carrot.update(delta, touchPoint);
		distanceIndexator.setCarrotX(carrot.getX());
	}
	
	private void checkIfCarrotIsMissed()
	{
		if (carrot.getX() + carrot.getImageWidth() < -WINDOW_WIDTH / 5)
		{
			carrot.setX(gameHasBegan() ? timeCounter.getDistanceBasedOnTimeLeft() : carrot.getX());
			distanceIndexator.setDistanceToNextCarrot(carrot.getX());
			
			soundPlayer.alertSoundHasToBePlayed();
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		renderCarrotCounter(spriteBatch);
		
		carrot.render(spriteBatch);
	}
	
	private void renderCarrotCounter(SpriteBatch spriteBatch)
	{
		float x = WINDOW_WIDTH - WINDOW_WIDTH / 3.5f + 10;
		float y = 7;
		
		carrotIcon.render(spriteBatch, x, y - 12);

        fontType.setMessage( " x " + carrotCounter);
		fontType.draw(spriteBatch, x + carrotIcon.getWidth() - 32, y + 32);
    }
	
	public boolean gameHasBegan()
	{
		return carrotCounter > 0;
	}
	
	public boolean playerJustUnlockedBonus()
	{
		return carrotCounter == 30;
	}

    @Override
    public void dispose()
    {
        fontType.dispose();
        soundPlayer.dispose();
        carrot.dispose();
        carrotIcon.dispose();
    }

    private class CarrotIcon
	{
		
		private final Texture carrotImage;
		
		private boolean hasToScale;
		
		private float scale;

        private float rotation;
		
		private CarrotIcon()
		{
			carrotImage = createTexture("carrot/carrot_icon2");
			scale = 1f;
		}
		
		private void render(SpriteBatch spriteBatch, float x, float y)
		{
			scaleImage();
			adjustRotation();
			
			x -= (carrotImage.getWidth() * scale - carrotImage.getWidth()) * 0.5f;
			y -= (carrotImage.getHeight() * scale - carrotImage.getHeight()) * 0.5f;

            TextureRegion textureRegion = new TextureRegion(carrotImage);

            //spriteBatch.draw(carrotImage, x, y, carrotImage.getWidth() * scale, carrotImage.getHeight() * scale);
            spriteBatch.draw(textureRegion, x, y,
                    carrotImage.getWidth() * 0.5f, carrotImage.getHeight() * 0.5f,
                    carrotImage.getWidth(), carrotImage.getHeight(),
                    scale, scale, rotation);
		}
		
		private void scaleImage()
		{
			incrementScale(0.035f);
			
			if (scale > 1.2f)
			{
				hasToScale = false;
			}
			else if (scale < 1)
			{
				scale = 1;
			}
		}

		private void incrementScale(final float scaleValue)
		{
			if (hasToScale)
			{
				scale += scaleValue;
			}
			else
			{
				scale -= scaleValue;
			}
		}
		
		private void adjustRotation()
		{
			//carrotImage.setCenterOfRotation(carrotImage.getWidth() * scale * 0.5f, carrotImage.getHeight() * scale * 0.5f);
			
			final int angle = 7;
			
			if (hasToScale)
			{
				if (scale < 1.12f)
				{
					rotation += angle;
				}
				else
				{
                    rotation -= angle;
				}
			}
			else if (scale != 1f)
			{
				if (scale > 1.08f)
				{
                    rotation -= angle;
				}
				else
				{
                    rotation += angle;
				}
			}
			else if (rotation != 0)
			{
                rotation += angle;
			}
		}

		private void alertBunnyPickedCarrot()
		{
			hasToScale = true;
		}
		
		private float getWidth()
		{
			return carrotImage.getWidth();
		}

        public void dispose()
        {
            carrotImage.dispose();
        }

	}
	
}
