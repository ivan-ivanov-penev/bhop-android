package com.bhop.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.bunny.CameraMovement;
import com.bhop.game.utils.singleton.SingletonManager;

/**
 * Created by Ivan on 4/16/2016.
 */
public abstract class BasicGameObject implements GameObject
{
    protected Texture image;

    protected Vector2 coordinates;

    protected final CameraMovement movement;

    protected BasicGameObject(Texture image, Vector2 coordinates)
    {
        this.movement = SingletonManager.getSingleton(CameraMovement.class);
        this.image = image;
        this.coordinates = coordinates;
    }

    public Texture getImage()
    {
        return image;
    }

    public int getImageWidth()
    {
        return image.getWidth();
    }

    public int getImageHeight()
    {
        return image.getHeight();
    }

    public float getX()
    {
        return coordinates.x;
    }

    public float getY()
    {
        return coordinates.y;
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.draw(image, coordinates.x, coordinates.y);
    }

    @Override
    public void update(float delta, Vector3 touchPoint)
    {
        float cameraSpeed = movement.getMovementSpeed();

        delta *= cameraSpeed > 0 ? 1 : -1;

        coordinates.x -= cameraSpeed + delta;
    }

    @Override
    public void dispose()
    {
        image.dispose();
    }
}
