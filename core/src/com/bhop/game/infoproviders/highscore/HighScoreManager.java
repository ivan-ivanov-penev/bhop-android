package com.bhop.game.infoproviders.highscore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class HighScoreManager implements Singleton
{

	private final Preferences preferences;
	
	private final int oldHighScore;
	
	private HighScoreManager()
	{
		preferences = Gdx.app.getPreferences("highscore_info");

		oldHighScore = preferences.getInteger("highScore", 0);
	}
	
	public int getHighScore()
    {
	    return oldHighScore;
    }
	
	public void rewriteHighScoreIfGreater(int currentHighScore)
	{
		if (preferences.getInteger("highScore", 0) < currentHighScore)
		{
			preferences.putInteger("highScore", currentHighScore);
            preferences.flush();
		}
	}

}
