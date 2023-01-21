package com.bhop.game.gameobjects.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SoundInfoManager
{

	private Preferences preferences;
	
	SoundInfoManager()
	{
		preferences = Gdx.app.getPreferences("sound_preferences");
	}

	void writeSoundInformation(boolean soundEnabled)
	{
		preferences.putBoolean("soundEnabled", soundEnabled);
		preferences.flush();
	}
	
	boolean isSoundEnabled()
	{
		return preferences.getBoolean("soundEnabled", true);
	}

}
