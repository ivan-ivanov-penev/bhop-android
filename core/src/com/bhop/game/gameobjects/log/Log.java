package com.bhop.game.gameobjects.log;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.ImageUtils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.gameobjects.bunny.Collidable;
import com.bhop.game.gameobjects.carrot.Carrot;
import com.bhop.game.utils.ImageUtils;

public class Log extends BasicGameObject implements Collidable
{
	
	public static final int IMAGE_WIDTH = 64;
	
	public static final int IMAGE_HEIGHT = 85;

    private static final Map<Integer, Float> VERTICAL_SPEEDS = createPossibleVerticalSpeeds();

    private static Map<Integer, Float> createPossibleVerticalSpeeds()
    {
        HashMap<Integer, Float> map = new HashMap<Integer, Float>();
        map.put(0, 1.5f);
        map.put(1, 2.25f);
        map.put(2, 3f);

        return map;
    }

    private final Set<PixelLocation> imagePixelLocations;

	private static Map<LogType, Set<PixelLocation>> typeToPixelLocations;
	
	private static int logKind;
	
	private boolean isFalling;
	
	private float verticalSpeed = 0.0f;
	
	private float horizontalSpeed = 0.0f;

    private float rotation;
	
	public Log(Carrot carrot)
	{
        super(null, new Vector2());

		//coordinates.y = WINDOW_HEIGHT - 190;
        coordinates.y = 109;

		LogType type = getRandomElement(LogType.values());
		String logType = getLogType(type);

		setXWithoutCarrotCollision(carrot);
		
		//image.setCenterOfRotation(29.5f, 43.5f);

		imagePixelLocations = ImageUtils.getPixelsLocations(logType);
//		imagePixelLocations = typeToPixelLocations.get(type);
	}

	static void initTypeToPixelLocations()
	{
		if (typeToPixelLocations == null)
		{
			typeToPixelLocations = new HashMap<LogType, Set<PixelLocation>>();
			typeToPixelLocations.put(LogType.STATIC, ImageUtils.getPixelsLocations("obsticales/static_log_collision.png"));
			typeToPixelLocations.put(LogType.ROLLING, ImageUtils.getPixelsLocations("obsticales/rolling_log_collision.png"));
			typeToPixelLocations.put(LogType.LEVITATING, ImageUtils.getPixelsLocations("obsticales/levitating_log_collision.png"));
		}
	}
	
	private String getLogType(LogType logType)
	{
        switch (logType)
        {
			case LEVITATING:
				//verticalSpeed = 1.0f * 2;
                //verticalSpeed = 1.5f + RANDOM.nextFloat() * 2 - 0.5f;
                verticalSpeed = VERTICAL_SPEEDS.get(RANDOM.nextInt(3));
				horizontalSpeed = 0.0f;
                coordinates.y = RANDOM.nextInt(WINDOW_HEIGHT - 360) + 109;
                isFalling = RANDOM.nextBoolean();
				image = createTexture("obsticales/levitating_log_kind" + logKind);
				
				return "obsticales/levitating_log_collision.png";
				
			case ROLLING:
				verticalSpeed = 0.0f;
				horizontalSpeed = 1.0f * 2;
				image = createTexture("obsticales/rolling_log_kind" + logKind);
				
				return "obsticales/rolling_log_collision.png";
				
			default:
				verticalSpeed = 0.0f;
				horizontalSpeed = 0.0f;
				image = createTexture("obsticales/static_log_kind" + logKind);
				//coordinates.y = WINDOW_HEIGHT - 200;
                coordinates.y = 119;

				return "obsticales/static_log_collision.png";
		}
	}

	private void setXWithoutCarrotCollision(Carrot carrot)
    {
	    if (WINDOW_WIDTH + image.getWidth() > carrot.getX() && WINDOW_WIDTH < carrot.getX() + carrot.getImageWidth())
		{
            coordinates.x = carrot.getX() + carrot.getImageWidth();
		}
		else
		{
			coordinates.x = WINDOW_WIDTH;
		}
    }

	@Override
	public void render(SpriteBatch spriteBatch)
	{
        //spriteBatch.draw(image, coordinates.x, coordinates.y, IMAGE_WIDTH, IMAGE_HEIGHT);
        TextureRegion textureRegion = new TextureRegion(image);

        spriteBatch.draw(textureRegion, coordinates.x, coordinates.y,
                29.5f, 41.5f,
                image.getWidth(), image.getHeight(),
                1, 1, rotation);
	}

	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		coordinates.x -= movement.getMovementSpeed() + horizontalSpeed;

        coordinates.y += isFalling ? verticalSpeed : -verticalSpeed;

		if (coordinates.y < WINDOW_HEIGHT - 360)
		{
			isFalling = true;
		}

		if (coordinates.y > WINDOW_HEIGHT - 200)
		{
			isFalling = false;
		}
		
		if (horizontalSpeed != 0)
		{
			//image.rotate(-2.5f);
            //rotation += 2.5f;
            rotation += 5;
		}
        //rotation -= 2.5f;
	}
	
	public Set<PixelLocation> getImagePixelLocations()
	{
		return imagePixelLocations;
	}
	
	static void setLogKind()
	{
		Log.logKind = RANDOM.nextInt(3);
	}
	
	static enum LogType
	{
		STATIC,
		ROLLING,
		LEVITATING;
	}

}
