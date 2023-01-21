package com.bhop.game.gameobjects.gameinformation;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;
import static com.bhop.game.gameobjects.timecounter.GameEndWatcher.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.booster.Booster;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.states.Play;
import com.bhop.game.utils.InputUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

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
