package com.bhop.game.gameobjects.environment.cloud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.utils.generator.GeneratedObject;

import java.awt.Color;

/**
 * Created by Ivan on 4/17/2016.
 */
public class Cloud extends GeneratedObject
{
    protected Cloud(Texture image, float x)
    {
        super(image, new Vector2(x, getRandomY()));
    }

    @Override
    public void update(float delta, Vector3 touchPoint)
    {
        float cameraSpeed = movement.getCameraSpeed();

        cameraSpeed *= cameraSpeed > 0 ? -1 : 2;

        delta *= cameraSpeed > 0 ? -1 : 1;

//        coordinates.x -= cameraSpeed / 25 + delta;
        coordinates.x -= cameraSpeed / 5 + delta;
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.setColor(1, 1, 1, 0.6f);

        super.render(spriteBatch);

        spriteBatch.setColor(1, 1, 1, 1);
    }

    @Override
    public void reposition(float x)
    {
        coordinates.set(x, getRandomY());
    }

    private static float getRandomY()
    {
        return (float) (Math.random() * -120);
    }
}
