package com.bhop.game.gameobjects.sound;

import static com.bhop.game.gameobjects.sound.SoundWatcher.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.utils.InputUtils;
import com.bhop.game.utils.singleton.Singleton;

public class SoundIcon implements Singleton, GameObject
{
	
	private final Texture soundIcon;
	
	private final Texture soundIconUnchecked;
	
	private final SoundPlayer soundPlayer;
	
	private Texture renderImage;
	
	private float x;
	
	private float y;
	
	private static boolean clickedOnIcon;
	
	private SoundIcon()
	{
		soundIcon = createTexture("sound_icon/music_icon");
		soundIconUnchecked = createTexture("sound_icon/music_icon_unwanted");
		renderImage = isSoundEnabled() ? soundIcon : soundIconUnchecked;
		soundPlayer = new SoundPlayer("button.wav");
		x = 8;
		y = 7;
	}

	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		if (isOverImage(touchPoint, renderImage, x, y) && InputUtils.isInputInvoked())
		{
			soundPlayer.playSoundOnce();
			
			alertPlayerHasClickedIcon();
			
			renderImage = renderImage == soundIcon ? soundIconUnchecked : soundIcon;
			
			x += renderImage == soundIcon ? -2 : 2;
			y += renderImage == soundIcon ? 2 : -2;
			
			clickedOnIcon = true;
		}
		else
		{
			clickedOnIcon = false;
			
			soundPlayer.alertSoundHasToBePlayed();
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		spriteBatch.draw(renderImage, x, y);
	}

	@Override
	public void dispose()
	{
		soundIcon.dispose();
		soundIconUnchecked.dispose();
	}

	public static boolean isClickedOnIcon()
	{
		return clickedOnIcon;
	}
	
}
