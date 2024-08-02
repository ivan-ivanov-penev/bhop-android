package com.bhop.app.gameobjects.sound;

import com.badlogic.gdx.audio.Sound;

import static com.bhop.app.utils.SoundUtils.*;

public class SoundPlayer
{
	
	private Sound sound;
	
	private boolean soundPlayed;

	public SoundPlayer(String soundName)
	{
		sound = createSound(soundName);
	}
	
	public void playSoundContinuously()
	{
		playSound(sound);
	}
	
	public void playSoundOnce()
	{
		if (!soundPlayed)
		{
			playSound(sound);
			
			soundPlayed = true;
		}
	}
	
	public void alertSoundHasToBePlayed()
	{
		soundPlayed = false;
	}

	public void dispose()
	{
		sound.dispose();
	}

}
