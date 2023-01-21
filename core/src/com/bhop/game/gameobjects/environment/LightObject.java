package com.bhop.game.gameobjects.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.singleton.Singleton;

/**
 * Created by Ivan on 4/16/2016.
 */
public class LightObject extends BasicGameObject implements Singleton
{
    public LightObject()
    {
        super(ImageUtils.createTextureAccordingToTimePeriod("backgrounds/light_objects/"), new Vector2(0, 0));
    }

    @Override
    public void update(float delta, Vector3 touchPoint) {}
}
