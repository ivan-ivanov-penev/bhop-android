package com.bhop.app.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ivan on 4/15/2016.
 */
public class GameStateManager
{
    private volatile GameState currentGameState;

    public void update(float delta)
    {
        currentGameState.update(delta);
    }

    public void render(SpriteBatch spriteBatch)
    {
        currentGameState.render(spriteBatch);
    }

    public void enterState(final GameState gameState)
    {
        if (currentGameState != null)
        {
            currentGameState.dispose();
        }

        currentGameState = gameState.setGameStateManager(this);
    }
}
