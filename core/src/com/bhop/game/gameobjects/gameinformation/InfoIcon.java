package com.bhop.game.gameobjects.gameinformation;

import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.utils.GameUtils;
import com.bhop.game.utils.InputUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class InfoIcon extends BasicGameObject implements Singleton
{
	
	private static boolean playerIsReadingInfo;
	
	private final SoundPlayer soundPlayer;
	
	private InfoIcon()
	{
		super(createTexture("info_icon/info_icon"), new Vector2());
		
		soundPlayer = new SoundPlayer("button.wav");

		coordinates.x = 8;
		coordinates.y = GameUtils.WINDOW_HEIGHT - 7 - image.getHeight();
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
        //System.out.println(isOverImage(touchPoint, this));

		if (isOverImage(touchPoint, this) && InputUtils.isInputInvoked())
		{
			playerIsReadingInfo = !playerIsReadingInfo;
			
			soundPlayer.playSoundOnce();
		}
		else
		{
			soundPlayer.alertSoundHasToBePlayed();
		}
	}

    @Override
    public void dispose()
    {
        super.dispose();

        soundPlayer.dispose();
    }

    public static boolean isPlayerIsReadingInfo()
	{
		return playerIsReadingInfo;
	}

}
