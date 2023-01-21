package com.bhop.game.gameobjects.bunny;

import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.bunny.BunnyPhysics.BunnyJump;
import com.bhop.game.gameobjects.bunny.CameraMovement.RunSpeedBoost;
import com.bhop.game.gameobjects.bunny.animation.BunnyAnimation;
import com.bhop.game.gameobjects.coloroptions.ColorOption;
import com.bhop.game.gameobjects.dustcloud.DustCloud;
import com.bhop.game.gameobjects.gameinformation.InfoIcon;
import com.bhop.game.gameobjects.pauseicon.PauseIcon;
import com.bhop.game.gameobjects.sound.SoundIcon;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.utils.InputUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

@SingletonClass
public class Bunny implements GameObject, Singleton
{
	
	private float x;

	private float y;

	private boolean isHit;

	private final BunnyAnimation animation;

	private final CameraMovement movement;

	private final BunnyPhysics physics;

	private final BunnyJump jump;

	private final CollisionChecker collisionChecker;

	private final DustCloud dustCloud;
	
	private final SoundPlayer jumpSound;
	
	private final SoundPlayer hitSound;

	public Bunny()
	{
		movement = SingletonManager.getSingleton(CameraMovement.class);
		dustCloud = SingletonManager.getSingleton(DustCloud.class);
		collisionChecker = new CollisionChecker();
		physics = new BunnyPhysics();
		jump = new BunnyJump();
		jumpSound = new SoundPlayer("jump0.wav");
		hitSound = new SoundPlayer("hitting_sound.wav");
		animation = new BunnyAnimation();
		
		x = BUNNY_STARTING_X;
        y = 119;
		//y = WINDOW_HEIGHT - 215;
	}
	
	public void setBunnyAnimations(ColorOption.BunnyColor bunnyColor)
	{
		animation.setAnimations(bunnyColor);
	}
	
	@Override
	public void render(SpriteBatch spriteBatch)
	{
		animation.draw(spriteBatch, x, y);
	}

	private void updateHeightPosition()
	{
		y += physics.getGravityForce();

		if (y < 119)
		{
			y = 119;
		}
	}

	private boolean isOnTopOfAnObject()
	{
		return y == 119;
	}

	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		collisionCheck();
		
		updateMovement(playerHasNotClickedOnAnyIcon() && (InputUtils.isInputInvoked()));
		
		animation.update(physics.getGravityForce(), y, movement.getSpeedFactor(), isOnTopOfAnObject());
	}
	
	private boolean playerHasNotClickedOnAnyIcon()
	{
		return !SoundIcon.isClickedOnIcon() && !InfoIcon.isPlayerIsReadingInfo() && !PauseIcon.isGamePaused();
	}

	private void updateMovement(boolean buttonIsPressed)
    {
	    if (isOnTopOfAnObject())
		{
			BunnyIsHitEventWatcher.alertWatchersBunnyHasRecovered();
			
			attemptRun(buttonIsPressed);
			
			jumpSound.alertSoundHasToBePlayed();
		}
		else
		{
			fall();
		}
    }

	private void attemptRun(boolean buttonIsPressed)
	{
		RunSpeedBoost runSpeedBoost = animation.getSpeedBoost() == null ? RunSpeedBoost.MIN : animation.getSpeedBoost();

		if (buttonIsPressed)
		{
			movement.increaseSpeedFactor(runSpeedBoost);

			jumpSound.playSoundOnce();
			
			jump();
		}
		else
		{
			run();
		}
	}

	private void jump()
    {
		physics.setGravityToJumping(jump.getJumpHeightAccordingToSpeed(movement.getSpeedFactor()));
	    
	    updateHeightPosition();
    }

	private void run()
	{
		physics.resetGravityFallingBaseForce();
		
		movement.decreaseSpeedFactor();
	}

	private void fall()
	{
		physics.increaseGravityPullingForce();

		updateHeightPosition();
	}

	private void collisionCheck()
	{
		if (collisionChecker.checkForCollision(x, y, animation))
		{
			collide();
		}
		else
		{
			isHit = false;
			
			hitSound.alertSoundHasToBePlayed();
		}
	}

	private void collide()
	{
		if (!isHit)
	    {
            hitSound.playSoundOnce();
			
	    	isHit = true;
	    	
			BunnyIsHitEventWatcher.alertWatchersBunnyIsHit();

			dustCloud.alertHasToRender();
	    	
	    	jump();
	    }
	}

    @Override
    public void dispose()
    {
        jumpSound.dispose();

        hitSound.dispose();

        animation.dispose();
    }
}
