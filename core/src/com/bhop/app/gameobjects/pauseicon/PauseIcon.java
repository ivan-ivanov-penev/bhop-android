package com.bhop.app.gameobjects.pauseicon;

import static com.bhop.app.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.app.gameobjects.GameObject;
import com.bhop.app.gameobjects.sound.SoundPlayer;
import com.bhop.app.utils.InputUtils;
import com.bhop.app.utils.singleton.Singleton;
import com.bhop.app.utils.singleton.SingletonClass;

@SingletonClass
public class PauseIcon implements GameObject, Singleton
{
	
	private static boolean gamePaused;
	
	private final Texture pauseIcon;
	
	private final Texture playIcon;
	
	private final SoundPlayer soundPlayer;
	
	private Texture renderIcon;
	
	private float x;
	
	private float y;
	
	private PauseIcon()
    {
		pauseIcon = createTexture("pause_icon/pause");
		playIcon = createTexture("pause_icon/play2");
		soundPlayer = new SoundPlayer("button.wav");
		renderIcon = pauseIcon;

		x = 8 + renderIcon.getWidth() * 1.5f;
		y = 7;
    }

	public static boolean isGamePaused()
	{
		return gamePaused;
	}

	@Override
    public void update(float delta, Vector3 touchPoint)
    {
		if (isOverImage(touchPoint, renderIcon, x, y) && InputUtils.isInputInvoked())
		{
			soundPlayer.playSoundOnce();
			
			changePauseState();
		}
		else
		{
			soundPlayer.alertSoundHasToBePlayed();
		}
    }

	private void changePauseState()
    {
	    boolean isPauseIcon = renderIcon == pauseIcon;
	    
	    renderIcon = isPauseIcon ? playIcon : pauseIcon;
	    
	    x += isPauseIcon ? 2 : -2;
	    y += isPauseIcon ? -2 : 2;
	    
	    gamePaused = !gamePaused;
    }

	@Override
    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(renderIcon, x, y);
    }

    @Override
    public void dispose()
    {
        playIcon.dispose();
        pauseIcon.dispose();
    }
}
