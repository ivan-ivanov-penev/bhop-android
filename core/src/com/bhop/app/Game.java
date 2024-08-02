package com.bhop.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bhop.app.states.GameStateManager;
import com.bhop.app.states.LoadingScreen;

public class Game extends ApplicationAdapter
{
	private GameStateManager gameStateManager;

	private SpriteBatch spriteBatch;

	@Override
	public void create ()
	{
		spriteBatch = new SpriteBatch();

		gameStateManager = new GameStateManager();
		gameStateManager.enterState(new LoadingScreen());
	}

	@Override
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
	}
}
