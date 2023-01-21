package com.bhop.game.gameobjects.timecounter;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.gameobjects.bunny.CameraMovement.*;
import static com.bhop.game.utils.FontUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.utils.font.Font;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class TimeCounter implements GameObject, Singleton
{
	
	private final Font fontType;
	
	private final Texture sign;
	
	private int secondsLeft;
	
	private int fpsCounter;
	
	private TimeCounter()
    {
		sign = createTexture("signs/counter1");
		fontType = new Font("").setColor(COLOR_BLACKISH);
		secondsLeft = 10;
    }

	@Override
    public void update(float delta, Vector3 touchPoint)
    {
		updateFpsCounter();
		checkForTimeExpiration();
    }

	private void updateFpsCounter()
    {
	    fpsCounter++;
		
		if (fpsCounter == FPS)
		{
			fpsCounter = 0;
			
			secondsLeft -= 1;
		}
    }
	
	public int getSecondsLeft()
    {
	    return secondsLeft;
    }

	@Override
	public void render(SpriteBatch spriteBatch)
    {
		String timeLeft = String.valueOf(secondsLeft < 0 ? 0 : secondsLeft);
		
		float x = (WINDOW_WIDTH - sign.getWidth()) * 0.5f;
		
//		sign.draw(x, -26);
        spriteBatch.draw(sign, x, WINDOW_HEIGHT - sign.getHeight() * 0.77f -16);
        fontType.setMessage(timeLeft);
		fontType.draw(spriteBatch, x + (sign.getWidth() - fontType.getWidth()) / 2, WINDOW_HEIGHT - fontType.getHeight() - WINDOW_HEIGHT * 0.04f);
    }
	
	private void checkForTimeExpiration()
	{
		if (secondsLeft < 0)
		{
			GameEndWatcher.alertGameHasEnded();
		}
	}
	
	public void setTimeLeft(float x)
	{
		//secondsLeft += (int) (x / (CAMERA_SPEED * FPS * 2 * (((MAX_SPEED_FACTOR - MIN_SPEED_FACTOR) * 0.5f) + MIN_SPEED_FACTOR)));
        secondsLeft += (int) (x / (CAMERA_SPEED * FPS * 2 * (((MAX_SPEED_FACTOR - MIN_SPEED_FACTOR) * 0.45f) + MIN_SPEED_FACTOR)));

        fpsCounter = 0;
		
		if (secondsLeft < 5)
		{
			secondsLeft = 5;
		}
	}
	
	public float getDistanceBasedOnTimeLeft()
	{
		return secondsLeft * CAMERA_SPEED * FPS * 2 * (((MAX_SPEED_FACTOR - MIN_SPEED_FACTOR) / 2) + MIN_SPEED_FACTOR);
	}

    @Override
    public void dispose()
    {
        sign.dispose();
        fontType.dispose();
    }
}
