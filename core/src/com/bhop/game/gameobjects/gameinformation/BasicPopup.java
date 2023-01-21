package com.bhop.game.gameobjects.gameinformation;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.utils.font.Font;
import com.bhop.game.utils.singleton.SingletonManager;

public abstract class BasicPopup extends BasicGameObject
{
	
	private static PopupManager popupManager;
	
	protected final CarrotManager carrotManager;

	private final SoundPlayer soundPlayerPopUp;

	private final SoundPlayer soundPlayerHidePopUp;
	
	protected final Font font;
	
	protected final String message;
	
	protected boolean movesLeft;
	
	private boolean finished;
	
	protected BasicPopup()
	{
		super(createTexture("game_information/popupicon"), new Vector2());
		
		popupManager = new PopupManager();
		
		soundPlayerPopUp = new SoundPlayer("pop_up.wav");

		soundPlayerHidePopUp = new SoundPlayer("pop_up_hide.wav");

		carrotManager = SingletonManager.getSingleton(CarrotManager.class);
		
		message = setMessage();
		
		font = new Font(message).setSize(20);
		
		coordinates.y = WINDOW_HEIGHT - image.getHeight() - WINDOW_HEIGHT / 5.2f;
		coordinates.x = -image.getWidth();
	}
	
	protected abstract String setMessage();
	
	protected abstract boolean hasToPopup();
	
    protected abstract void attemptPopup();
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		if (hasToPopup())
		{
			popupManager.addToQueue(this);
		}
	}
	
	protected void update()
	{
		popupManager.update();
	}
	
	protected void popup()
	{
		soundPlayerPopUp.playSoundOnce();
		
		finished = false;
		
		if (movesLeft)
		{
			centerInScreen();
		}
		else if (coordinates.x < WINDOW_WIDTH - image.getWidth())
		{
			coordinates.x += 12.5 * 2;
		}
		else
		{
			movesLeft = true;
		}
	}

	private void centerInScreen()
    {
	    if (coordinates.x > (WINDOW_WIDTH - image.getWidth()) * 0.5f)
	    {
			coordinates.x -= 4 * 2;
	    }
    }

	protected void hide()
    {
		if (!movesLeft)
		{
			hideFromView();
		}
		else if (coordinates.x < WINDOW_WIDTH - image.getWidth())
		{
			coordinates.x += 4 * 2;
		}
		else
		{
			movesLeft = false;
			
			soundPlayerHidePopUp.playSoundOnce();
		}
    }

	private void hideFromView()
    {
	    if (coordinates.x > -image.getWidth())
	    {
			coordinates.x -= 12.5 * 2;
	    }
	    else
	    {
	    	finished = true;
	    }
    }
	
	public boolean isFinished()
    {
	    return finished;
    }

	@Override
    public void render(SpriteBatch spriteBatch)
    {
		if (coordinates.x > -image.getWidth())
		{
			spriteBatch.draw(image, coordinates.x, coordinates.y);

			font.draw(spriteBatch, coordinates.x + (image.getWidth() - font.getWidth()) * 0.5f, coordinates.y + (image.getHeight() - font.getHeight()) * 0.8f);
		}
    }

	@Override
	public void dispose()
	{
		super.dispose();

		font.dispose();
	}
}
