package com.bhop.game.gameobjects.coloroptions;

import static com.bhop.game.utils.ImageUtils.*;
import static com.bhop.game.utils.ImageUtils.createTexture;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.utils.GameUtils;

public class ColorOption extends BasicGameObject
{
	static final int IMAGE_WIDTH = 105;
	
	private final BunnyColor bunnyColor;
	
	private final Sign sign;
	
	ColorOption(float x, BunnyColor bunnyColor, float y)
	{
		super(createTexture("color_options/" + bunnyColor.getColorName()), new Vector2(x, y));

		this.bunnyColor = bunnyColor;
		this.sign = new Sign();
	}
	
	public BunnyColor getBunnyColor()
	{
		return bunnyColor;
	}

	@Override
	public void update(float delta, Vector3 touchPoint) {}
	
	@Override
	public void render(SpriteBatch spriteBatch)
	{
		sign.render(spriteBatch, coordinates.x, coordinates.y);
		spriteBatch.draw(image, coordinates.x + (sign.image.getWidth() - image.getWidth()) * 0.5f, coordinates.y + (sign.image.getHeight() - image.getHeight()) * 0.29f);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		sign.image.dispose();
	}

	@Override
	public Texture getImage()
	{
	    return sign.image;
	}
	
	private static class Sign
	{
		private final Texture image;
		
		public Sign()
		{
			image = createTexture("color_options/sign");
		}
		
		public void render(SpriteBatch spriteBatch, float x , float y)
		{
			spriteBatch.draw(image, x, y);
		}
	}
	
	public static enum BunnyColor
	{
		BONUS("bonus"),
		BLUE("blue"),
		PINK("pink");
		
		private final String colorName;
		
		private BunnyColor(String colorName)
		{
			this.colorName = colorName;
		}
		
		public String getColorName()
		{
			return colorName;
		}
	}

}
