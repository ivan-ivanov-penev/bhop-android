package com.bhop.game.gameobjects.carrot;

import static com.bhop.game.utils.GameUtils.*;

import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.gameobjects.bunny.CameraMovement;
import com.bhop.game.gameobjects.bunny.Collidable;
import com.bhop.game.gameobjects.pauseicon.PauseIcon;
import com.bhop.game.gameobjects.timecounter.GameEndWatcher;
import com.bhop.game.utils.animation.Animation;
import com.bhop.game.utils.singleton.SingletonManager;

public class Carrot implements GameObject, Collidable
{

	private float x;
	
	private final float y;
	
	private final CameraMovement movement;
	
	private final Animation animation;
	
	private final Map<Integer, Set<PixelLocation>> pixelLocations;
	
	Carrot(float x, Map<Integer, Set<PixelLocation>> pixelLocations, Animation animation)
    {
	    this.x = x;
		//this.y = GameUtils.WINDOW_HEIGHT - 245;
        this.y = 55;
		this.movement = SingletonManager.getSingleton(CameraMovement.class);
		this.pixelLocations = pixelLocations;/*new HashMap<Integer, Set<PixelLocation>>();*/
		this.animation = animation;
		this.animation.setPingPong(true);
    }

	@Override
    public Set<PixelLocation> getImagePixelLocations()
    {
	    return pixelLocations.get(animation.getFrameIndex());
    }
	
	void setX(float spawnX)
	{
		if (spawnX < WINDOW_WIDTH)
		{
			x = WINDOW_WIDTH;
		}
		else
		{
			x = spawnX;
		}
	}

	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public int getImageWidth()
	{
		return animation.getCurrentFrame().getWidth();
	}

	@Override
	public int getImageHeight()
	{
		return animation.getCurrentFrame().getHeight();
	}

	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		x -= movement.getMovementSpeed();
        animation.update();
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		checkForGamePause();

		if (GameEndWatcher.isGameEnd() && !animation.isStopped())
		{
			animation.stop();
		}
		
		animation.render(spriteBatch, x, y);
	}

	private void checkForGamePause()
    {
		if (PauseIcon.isGamePaused())
		{
			animation.stop();
		}
		else if (animation.isStopped())
		{
			animation.start();
		}
    }

    @Override
    public void dispose()
    {
        animation.dispose();
    }
}
