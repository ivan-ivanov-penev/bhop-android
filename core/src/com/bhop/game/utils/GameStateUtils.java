package com.bhop.game.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;

/**
 * Created by Ivan on 4/17/2016.
 */
public final class GameStateUtils
{
    private GameStateUtils() {}

    public static void renderGameObjects(Iterable<GameObject> gameObjects, SpriteBatch spriteBatch)
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.render(spriteBatch);
        }
    }

    public static void updateGameObjects(Iterable<GameObject> gameObjects, float delta, Vector3 touchPoint)
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.update(delta, touchPoint);
        }
    }

    public static void disposeGameObjects(Iterable<GameObject> gameObjects)
    {
        for (GameObject gameObject : gameObjects)
        {
            gameObject.dispose();
        }
    }
}
