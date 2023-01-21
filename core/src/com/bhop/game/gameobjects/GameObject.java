package com.bhop.game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Ivan on 4/16/2016.
 */
public interface GameObject
{
    public void update(float delta, Vector3 touchPoint);

    public void render(SpriteBatch spriteBatch);

    public void dispose();
}
