package com.bhop.game.gameobjects.sound;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.utils.SoundUtils.*;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.utils.singleton.Singleton;

/**
 * 
 * @author Ivan Penev
 *
 */
public class MusicPlayer implements Singleton, GameObject
{
	
	private static Music musicToPlay;
	
	private final Music[] musicForTimePeriod;
	
	private MusicPlayer()
    {
		musicForTimePeriod = new Music[3];
		
		fillMusicForTimePeriod();
		
		if (musicToPlay == null)
		{
			musicToPlay = getRandomElement(musicForTimePeriod);
		}
    }

	private void fillMusicForTimePeriod()
    {
		if (getTimePeriod().equals("dawn"))
		{
			musicForTimePeriod[0] = createMusic("day/Ketsa_-_01_-_Inexpress.wav");
			musicForTimePeriod[1] = createMusic("dawn/Ketsa_-_02_-_Forgetfulness_world_mix.wav");
			musicForTimePeriod[2] = createMusic("dawn/Ketsa_-_12_-_Long_Walk.wav");
		}
		else if (getTimePeriod().equals("day"))
		{
			musicForTimePeriod[0] = createMusic("day/Ketsa_-_01_-_Inexpress.wav");
			musicForTimePeriod[1] = createMusic("day/Ketsa_-_05_-_Conscience_remastered.wav");
			musicForTimePeriod[2] = createMusic("day/Ketsa_-_06_-_Sapphire_Sky.wav");
		}
		else
		{
			musicForTimePeriod[0] = createMusic("night/Ketsa_-_04_-_Wounds.wav");
			musicForTimePeriod[1] = createMusic("night/Ketsa_-_08_-_Changing_Seasons.wav");
			musicForTimePeriod[2] = createMusic("dawn/Ketsa_-_02_-_Forgetfulness_world_mix.wav");
		}
    }

	@Override
    public void update(float delta, Vector3 touchPoint)
    {
		if (SoundWatcher.isSoundEnabled())
		{
			attemptToSetNewMusicToPlay();

			musicToPlay.play();
		}
		else
		{
			musicToPlay.pause();
		}
    }

	private void attemptToSetNewMusicToPlay()
	{
		if (!musicToPlay.isPlaying())
        {
            setUnrepeateableMusicToPlay();
        }
	}

	private void setUnrepeateableMusicToPlay()
	{
		Music musicToPlay = getRandomElement(musicForTimePeriod);
		
		while (MusicPlayer.musicToPlay == musicToPlay)
		{
			musicToPlay = getRandomElement(musicForTimePeriod);
		}

		MusicPlayer.musicToPlay.stop();
		MusicPlayer.musicToPlay = musicToPlay;
		MusicPlayer.musicToPlay.setVolume(0.3f);
	}

	@Override
    public void render(SpriteBatch spriteBatch) {}

	@Override
	public void dispose()
	{
		for (Music music : musicForTimePeriod)
		{
			music.dispose();
		}
	}
}
