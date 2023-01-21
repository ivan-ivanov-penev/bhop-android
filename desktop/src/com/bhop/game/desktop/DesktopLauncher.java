package com.bhop.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bhop.game.Game;
import com.bhop.game.utils.GameUtils;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameUtils.WINDOW_WIDTH;
		config.height = GameUtils.WINDOW_HEIGHT;
		config.title = GameUtils.GAME_NAME;
        //config.backgroundFPS = 120;
        //config.foregroundFPS = 120;

		new LwjglApplication(new Game(), config);
	}
}
