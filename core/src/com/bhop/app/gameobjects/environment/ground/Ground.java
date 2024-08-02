package com.bhop.app.gameobjects.environment.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bhop.app.utils.generator.GeneratedObject;

/**
 * Created by Ivan on 4/17/2016.
 */
public class Ground extends GeneratedObject
{
    protected Ground(Texture image, float x)
    {
        super(image, new Vector2(x, 0));
    }

//    @Override
//    public void update(float delta, Vector3 touchPoint)
//    {
//        super.update(delta, touchPoint);
//    }
}
