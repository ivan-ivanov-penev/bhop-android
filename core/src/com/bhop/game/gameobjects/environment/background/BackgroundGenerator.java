package com.bhop.game.gameobjects.environment.background;

import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.bhop.game.utils.GameUtils;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.generator.GeneratedObject;
import com.bhop.game.utils.generator.Generator;
import com.bhop.game.utils.singleton.Singleton;

/**
 * Created by Ivan on 4/17/2016.
 */
public class BackgroundGenerator extends Generator implements Singleton
{
    public BackgroundGenerator()
    {
        super(3);
    }

    @Override
    protected GeneratedObject createGeneratedObject(int index)
    {
        Texture image = ImageUtils.createTextureAccordingToTimePeriod("backgrounds/landscapes/");

        Background background = new Background(image, index * image.getWidth());

        attemptConvertToSpecial(background);

        return background;
    }

    private void attemptConvertToSpecial(Background background)
    {
        if (RANDOM.nextInt(70) == 1)
        {
            background.converToSpecialBackground();
        }
    }

    @Override
    protected void manageObject(GeneratedObject generatedObject)
    {
        if (generatedObject.getX() < -generatedObject.getImageWidth())
        {
            ((Background) generatedObject).convertToNormalBackground();

            generatedObject.reposition(getLastObjectX() + generatedObject.getImageWidth());

            attemptConvertToSpecial((Background) generatedObject);
        }
        else if (generatedObject.getX() > GameUtils.WINDOW_WIDTH)
        {
            generatedObject.reposition(getFirstObjectX() - generatedObject.getImageWidth());
        }
    }
}
