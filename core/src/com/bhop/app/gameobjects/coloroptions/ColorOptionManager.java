package com.bhop.app.gameobjects.coloroptions;

import static com.bhop.app.gameobjects.coloroptions.ColorOption.IMAGE_WIDTH;
import static com.bhop.app.utils.GameUtils.*;
import static com.bhop.app.utils.FontUtils.*;
import static com.bhop.app.utils.ImageUtils.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.app.gameobjects.GameObject;
import com.bhop.app.gameobjects.coloroptions.ColorOption.BunnyColor;
import com.bhop.app.infoproviders.bonuscolor.BonusSkinUnlocker;
import com.bhop.app.states.Menu;
import com.bhop.app.utils.InputUtils;
import com.bhop.app.utils.font.Font;
import com.bhop.app.utils.singleton.Singleton;
import com.bhop.app.utils.singleton.SingletonClass;
import com.bhop.app.utils.singleton.SingletonManager;

@SingletonClass
public class ColorOptionManager implements GameObject, Singleton
{
	private final BonusSkinUnlocker colorUnlocker;
	
	private final Font font;
	
	private final Texture mainSign;
	
	private final float x;
	
	private final float y;
	
	private List<ColorOption> colorOptions;
	
	private ColorOptionManager()
	{
		font = new Font("CHOOSE BUNNY COLOR:").setColor(COLOR_BLACKISH);
		mainSign = createTexture("color_options/main_sign");
		x = (WINDOW_WIDTH - mainSign.getWidth()) *0.5f;
		y = WINDOW_HEIGHT - mainSign.getHeight() * 0.85f;
		colorUnlocker = SingletonManager.getSingleton(BonusSkinUnlocker.class);
		colorOptions = new ArrayList<ColorOption>();
		
		fillColorBlocks();
	}
	
	public List<ColorOption> getColorOptions()
    {
	    return colorOptions;
    }

	private void fillColorBlocks()
	{
		int numberOfBlocks = colorUnlocker.playerHasUnlockedBonus() ? BunnyColor.values().length : BunnyColor.values().length - 1;

		float firstX =(WINDOW_WIDTH - IMAGE_WIDTH * numberOfBlocks - IMAGE_WIDTH * 0.5f * (numberOfBlocks - 1)) / 2;
		
		for (BunnyColor bunnyColor : BunnyColor.values())
		{
			if (!(bunnyColor.equals(BunnyColor.BONUS) && !colorUnlocker.playerHasUnlockedBonus()))
			{
				colorOptions.add(new ColorOption(firstX, bunnyColor, y - mainSign.getHeight() * 0.65f));
				
				firstX += IMAGE_WIDTH * 1.5f;
			}
		}
	}

	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		for (ColorOption colorBlock : colorOptions)
		{
			if (isOverImage(touchPoint, colorBlock.getImage(), colorBlock.getX(), colorBlock.getY()) && InputUtils.isInputInvoked())
			{
				Menu.informPlayerHasPickedColor(colorBlock.getBunnyColor());
			}
		}
	}

	@Override
	public void dispose()
	{
		mainSign.dispose();

		font.dispose();

		for (ColorOption colorOption : colorOptions)
		{
			colorOption.dispose();
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		for (ColorOption colorBlock : colorOptions)
		{
			colorBlock.render(spriteBatch);
		}

		spriteBatch.draw(mainSign, x, y);
		
		renderChooseColorText(spriteBatch);
	}

	private void renderChooseColorText(SpriteBatch spriteBatch)
    {
		float x = this.x + (mainSign.getWidth() - font.getWidth()) * 0.5f;
		float y = this.y + (mainSign.getHeight() - font.getHeight()) * 0.45f;

		font.draw(spriteBatch, x, y);
    }

}
