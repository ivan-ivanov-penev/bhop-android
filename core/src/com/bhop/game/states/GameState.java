package com.bhop.game.states;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.GameStateUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.utils.singleton.SingletonManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 4/15/2016.
 */
public abstract class GameState
{
    protected OrthographicCamera camera;

    protected Vector3 touchPoint;

    protected GameStateManager gameStateManager;

    protected List<GameObject> gameObjects;

    protected GameState()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WINDOW_WIDTH, WINDOW_HEIGHT);

        touchPoint = new Vector3();

        gameObjects = new ArrayList<GameObject>();
    }

    protected GameState setGameStateManager(GameStateManager gameStateManager)
    {
        this.gameStateManager = gameStateManager;

        return this;
    }

    public void update(float delta)
    {
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        updateGameObjects(gameObjects, delta, touchPoint);
    }

    public void render(SpriteBatch spriteBatch)
    {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        renderGameObjects(gameObjects, spriteBatch);

        spriteBatch.end();
    }

    public void dispose()
    {
//        disposeGameObjects(gameObjects);

        //SingletonManager.clearSingletons();
    }
}
