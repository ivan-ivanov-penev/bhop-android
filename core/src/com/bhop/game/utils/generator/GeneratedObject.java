package com.bhop.game.utils.generator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bhop.game.gameobjects.BasicGameObject;

/**
 * Created by Ivan on 4/17/2016.
 */
public abstract class GeneratedObject extends BasicGameObject
{
    protected GeneratedObject(Texture image, Vector2 coordinates)
    {
        super(image, coordinates);
    }

    public void reposition(float x)
    {
        coordinates.set(x, 0);
    }
}
