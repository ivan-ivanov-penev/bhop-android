package com.bhop.game.gameobjects.environment.cloud;

import com.badlogic.gdx.graphics.Texture;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.generator.GeneratedObject;
import com.bhop.game.utils.generator.Generator;
import com.bhop.game.utils.singleton.Singleton;

/**
 * Created by Ivan on 4/17/2016.
 */
public class CloudGenerator extends Generator implements Singleton
{
    public CloudGenerator()
    {
        super(3);
    }

    @Override
    protected GeneratedObject createGeneratedObject(int index)
    {
        Texture image = ImageUtils.createTextureAccordingToTimePeriod("backgrounds/clouds/");

        return new Cloud(image, index * image.getWidth());
    }
}
