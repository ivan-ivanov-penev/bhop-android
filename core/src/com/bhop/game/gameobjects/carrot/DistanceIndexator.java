package com.bhop.game.gameobjects.carrot;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.bunny.animation.BunnyAnimation;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class DistanceIndexator implements GameObject, Singleton
{
	
	private static final float LINE_X = (WINDOW_WIDTH - 155) * 0.5f;
	
	private final Texture compassLine;
	
	private final Texture line;
	
	private final Texture carrot;
	
	private final Texture bunnyIcon;
	
	private float distanceToNextCarrot;
	
	private float carrotX;
	
	private float renderX;
	
	private DistanceIndexator()
    {
		compassLine = createTexture("distance_indexator/compass_line");
		line = createTexture("distance_indexator/line0");
		carrot = createTexture("distance_indexator/carrot1");
		bunnyIcon =createTexture("distance_indexator/bunny_icon1");
    }

	@Override
    public void update(float delta, Vector3 touchPoint)
    {
		renderX = LINE_X + line.getWidth() - compassLine.getWidth() - ((line.getWidth() - compassLine.getWidth()) / ((distanceToNextCarrot - BunnyAnimation.IMAGE_WIDTH - BUNNY_STARTING_X) / (carrotX - BunnyAnimation.IMAGE_WIDTH)));
		renderX = renderX > LINE_X + line.getWidth() - compassLine.getWidth() ? LINE_X + line.getWidth() - compassLine.getWidth() : renderX;
		renderX = renderX < LINE_X ? LINE_X : renderX;
    }

	@Override
    public void render(SpriteBatch spriteBatch)
    {
		//int y = WINDOW_HEIGHT - line.getHeight() * 5;
        float y = line.getHeight() * 3.5f;
		
//		frame.draw(LINE_X - (frame.getWidth() - line.getWidth() - carrot.getWidth() * 1.5f) * 0.5f, y - frame.getHeight() * 0.45f);
        spriteBatch.draw(line, LINE_X, y);
        spriteBatch.draw(carrot, LINE_X + line.getWidth() + carrot.getWidth() * 0.5f, y + 2 + line.getHeight() * 0.5f - carrot.getHeight() * 0.5f);
        spriteBatch.draw(compassLine, renderX, y);
        spriteBatch.draw(bunnyIcon, renderX - bunnyIcon.getWidth() * 0.5f + compassLine.getWidth() * 0.65f, y - bunnyIcon.getHeight() * 0.25f); // y - bunnyIcon.getHeight() * 0.45f
    }
	
	void setCarrotX(float carrotX)
	{
		this.carrotX = carrotX;
	}
	
	void setDistanceToNextCarrot(float distanceToNextCarrot)
    {
	    this.distanceToNextCarrot = distanceToNextCarrot;
    }

    @Override
    public void dispose()
    {
        compassLine.dispose();
        line.dispose();
        carrot.dispose();
        bunnyIcon.dispose();
    }
}
