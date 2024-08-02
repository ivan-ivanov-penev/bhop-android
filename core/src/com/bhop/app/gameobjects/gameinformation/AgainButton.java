package com.bhop.app.gameobjects.gameinformation;

import static com.bhop.app.utils.GameUtils.*;
import static com.bhop.app.utils.ImageUtils.*;
import static com.bhop.app.gameobjects.timecounter.GameEndWatcher.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.app.gameobjects.GameObject;
import com.bhop.app.gameobjects.booster.Booster;
import com.bhop.app.gameobjects.sound.SoundPlayer;
import com.bhop.app.states.Play;
import com.bhop.app.utils.InputUtils;
import com.bhop.app.utils.singleton.Singleton;
import com.bhop.app.utils.singleton.SingletonClass;

@SingletonClass
public class AgainButton implements GameObject, Singleton
{
	
	private final Texture image;
	
	private final SoundPlayer soundPlayer;
	
	private float x;
	
	private float y;
	
	private boolean playerHasPressedButton;
	
	private AgainButton()
	{
		image = createTexture("again_button/again_button");
		
		soundPlayer = new SoundPlayer("button.wav");
		
		x = (WINDOW_WIDTH - image.getWidth()) * 0.5f;
		y = WINDOW_HEIGHT * 0.5f - image.getHeight();
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		if (isGameEnd() && isOverImage(touchPoint, image, x, y) && InputUtils.isInputInvoked())
		{
			playerHasPressedButton = true;
			
			soundPlayer.playSoundOnce();
		}
		else
		{
			soundPlayer.alertSoundHasToBePlayed();
		}
		
		attemtPlayButtonAnimation();
	}

	private void attemtPlayButtonAnimation()
	{
		if (playerHasPressedButton)
		{
			x += 0.5f;
			y -= 0.5f;
			
			alertPlayerWantsRestartIfAnimationFinished();
		}
	}

	private void alertPlayerWantsRestartIfAnimationFinished()
	{
		if (y <= WINDOW_HEIGHT * 0.5f - image.getHeight() - 2)
		{
            Booster.alertGameIsRestarting();

            Play.alertPlayerWantsToRestart();
		}
	}
	
	@Override
	public void render(SpriteBatch spriteBatch)
	{
		if (isGameEnd())
		{
			spriteBatch.draw(image, x, y);
		}
	}

	@Override
	public void dispose()
	{
		image.dispose();
		soundPlayer.dispose();
	}
}
