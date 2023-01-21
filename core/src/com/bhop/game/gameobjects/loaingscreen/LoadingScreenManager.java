package com.bhop.game.gameobjects.loaingscreen;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.animation.Animation;
import com.bhop.game.utils.font.Font;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class LoadingScreenManager implements GameObject, Singleton
{
	
	private static final Map<String, Animation> HINTS = new HashMap<String, Animation>();
	
	private final Texture loadingScreen;
	
	private final SpecaialAnimation specaialAnimation;
	
	private final Font font;
	
	private final Font info;

    private final Font hints;
	
	private final float boxX;
	
	private final float boxY;
	
	private final String randomHint;
	
	private String renderMessage;
	
	private int counter;
	
	private LoadingScreenManager()
    {
		fillHints();
		
		loadingScreen = createTexture("loading_screen/loading_screen1");
        randomHint = getRandomElement(new ArrayList<String>(HINTS.keySet()).toArray(new String[HINTS.size()]));
		font = new Font("").setSize(30).setColor(Color.BLACK);
		info = new Font(randomHint).setSize(20).setColor(Color.BLACK);
        hints = new Font("HINTS").setSize(20).setColor(Color.BLACK);
		renderMessage = "";
		//boxX = 230;
        boxX = 275;
		boxY = 160;
		specaialAnimation = new SpecaialAnimation();
    }
	
	private void fillHints()
	{
		//HINTS.put("THIS HINT IS NOT VERY HELPFUL. PROBLEM? :)", createAnimationWithArguments("troll", 1f, false));
		HINTS.put("IF YOU CLICK ON THE SCREEN EXACTLY WHEN BUNNY HAS LANDED YOU WILL GET AN EXTRA SPEED BONUS!", createAnimationWithArguments("speed_bonus", 0.75f, false));
		HINTS.put("THE TIME INDEXATOR ON THE TOP RIGHT CORNER OF THE SCREEN SHOWS WHETHER YOU WILL REACH THE CARROT IN TIME", createAnimationWithArguments("indexator", 0.8f, false));
		HINTS.put("IF YOU MISS THE CARROT DON'T WORRY - A NEW ONE WILL APPEAR", createAnimationWithArguments("carrot", 1.4f, true));
		HINTS.put("THE BOOSTER WILL GRANT YOU CONSTANT TOP SPEED FOR THE NEXT 10 SECONDS", createAnimationWithArguments("booster", 1f, false));
		HINTS.put("COLLECT 30 CARROTS IN A ROW AND YOU WILL UNLOCK BONUS SKIN FOR THE BUNNY!", createAnimationWithArguments("bonus_bunny", 1.2f, false));
		HINTS.put("THE DISTANCE INDEXATOR AT THE BOTTOM OF THE SCREEN SHOWS HOW FAR ARE YOU FROM THE CARROT", createAnimationWithArguments("distance_indexator", 1f, false));
		HINTS.put("EACH TIME YOU COLLECT 300 CARROTS A NEW SPECIAL BACKGROUND WILL BE UNLOCKED", createAnimationWithArguments("special_background_" + getTimePeriod(), 1f, false));
		HINTS.put("IF YOU GET LUCKY A SPECIAL BACKGROUND WILL APPEAR", createAnimationWithArguments("special_background_" + getTimePeriod(), 1f, false));
		HINTS.put("RUN SLOWLY TO JUMP HIGHER", createAnimationWithArguments("bunny_jump_run", 0.9f, false));
		HINTS.put("THE MORE YOU JUMP THE FASTER YOU RUN", createAnimationWithArguments("jump_bunny", 1.5f, false));
//		HINTS.put("", createAnimationWithArguments("", 1f, false));
	}
	
	private Animation createAnimationWithArguments(String directoryName, float animationSpeed, boolean pingPong)
	{
		//Animation animation = createAnimation("loading_screen/animations/" + directoryName);
        Animation animation = createConfiguredAnimation("loading_screen/animations/" + directoryName);
		animation.setSpeed(animationSpeed);
		animation.setPingPong(pingPong);
		
		return animation;
	}

	@Override
    public void update(float delta, Vector3 touchPoint)
    {
		counter += 1;
		
		if (counter > FPS * 0.25f)
		{
			counter = 0;
			
			updateRenderMessage();
		}

        HINTS.get(randomHint).update();
    }

	private void updateRenderMessage()
    {
		String message = "LOADING...";
		
	    if (renderMessage.length() == message.length())
	    {
	    	renderMessage = "";
	    }
	    
	    renderMessage += message.charAt(renderMessage.length());

        font.setMessage(renderMessage);
    }

    private float specialSpeed = 1;
	
	@Override
    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(loadingScreen, 0, 0);
		
		Animation animation = HINTS.get(randomHint);
		//animation.draw(boxX + (182 - animation.getWidth()) * 0.5f, boxY + (152 - animation.getHeight()) * 0.5f);
		
		if (randomHint.equals("THE MORE YOU JUMP THE FASTER YOU RUN") && animation.getFrameIndex() == animation.getFrameCount() - 1 && specialSpeed < 1.7)
		{
            specialSpeed += 0.04f;

			animation.setSpecialSpeed(specialSpeed);
		}

		font.draw(spriteBatch, (WINDOW_WIDTH - font.getWidth()) * 0.5f, WINDOW_HEIGHT - 25);
        hints.draw(spriteBatch, (WINDOW_WIDTH - hints.getWidth()) * 0.5f, WINDOW_HEIGHT * 0.78f);
        info.specialDraw(spriteBatch, WINDOW_WIDTH * 0.1f, 130);

        specaialAnimation.attemptToRenderHead(spriteBatch, animation);
    }

    @Override
    public void dispose()
    {
        font.dispose();
        info.dispose();
        hints.dispose();
        specaialAnimation.dispose();
        loadingScreen.dispose();

        for (Animation animation : HINTS.values())
        {
            animation.dispose();
        }
    }

    private class SpecaialAnimation
	{
		private final boolean animationIsDistanceIndexator;
		
		private final boolean animationIsBooster;
		
		private final Texture bunnyIcon;
		
		private float x;

        private float angle;
		
		public SpecaialAnimation()
        {
			animationIsDistanceIndexator = randomHint.equals("THE DISTANCE INDEXATOR AT THE BOTTOM OF THE SCREEN SHOWS HOW FAR ARE YOU FROM THE CARROT");
			animationIsBooster = randomHint.equals("THE BOOSTER WILL GRANT YOU CONSTANT TOP SPEED FOR THE NEXT 10 SECONDS");
			bunnyIcon = createTexture("distance_indexator/bunny_icon1");
			x = boxX + 20;
        }
		
		private void attemptToRenderHead(SpriteBatch spriteBatch, Animation animation)
		{
            if (animationIsBooster)
			{
				//for (int i = 0; i < HINTS.get(randomHint).getFrameCount(); i++)
				{
					//HINTS.get(randomHint).getImage(i).rotate(1);
                    angle += 2;

                    TextureRegion region = new TextureRegion(animation.getCurrentFrame());

                    spriteBatch.draw(region, boxX + (175 - animation.getWidth()) * 0.5f, boxY + (152 - animation.getHeight()) * 0.5f,
                            animation.getWidth() * 0.5f, animation.getHeight() * 0.5f, animation.getWidth(), animation.getHeight(), 1, 1, angle);
				}
			}
            else
            {
                spriteBatch.draw(animation.getCurrentFrame(), boxX + (175 - animation.getWidth()) * 0.5f, boxY + (152 - animation.getHeight()) * 0.5f);
            }

            if (animationIsDistanceIndexator)
            {
                spriteBatch.draw(bunnyIcon, x, boxY + 61, bunnyIcon.getWidth() * 0.9f, bunnyIcon.getHeight() * 0.9f);

                incrementX();
            }
		}

		private void incrementX()
        {
	        x += 0.5f;
	        
	        if (x > boxX + 120)
	        {
	        	x = boxX + 20;
	        }
        }

        private void dispose()
        {
            bunnyIcon.dispose();
        }
	}

}
