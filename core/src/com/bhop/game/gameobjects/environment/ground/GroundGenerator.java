package com.bhop.game.gameobjects.environment.ground;

import com.badlogic.gdx.graphics.Texture;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.generator.GeneratedObject;
import com.bhop.game.utils.generator.Generator;
import com.bhop.game.utils.singleton.Singleton;

/**
 * Created by Ivan on 4/17/2016.
 */
public class GroundGenerator extends Generator implements Singleton
{
    public GroundGenerator()
    {
        super(4);
    }

    @Override
    protected GeneratedObject createGeneratedObject(int index)
    {
        Texture image = ImageUtils.createTextureAccordingToTimePeriod("ground/");

        return new Ground(image, index * image.getWidth());
    }
}
