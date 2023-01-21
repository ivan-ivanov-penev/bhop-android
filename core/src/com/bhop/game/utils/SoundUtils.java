package com.bhop.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.bhop.game.gameobjects.sound.SoundWatcher;

/**
 * Created by Ivan on 4/21/2016.
 */
public final class SoundUtils
{
    private SoundUtils() {}

    public static Music createMusic(String file)
    {
        return Gdx.audio.newMusic(Gdx.files.internal("sound/music/" + file));
    }

    public static Sound createSound(String file)
    {
        return Gdx.audio.newSound(Gdx.files.internal("sound/" + file));
    }

    public static void playSound(Sound sound)
    {
        if (SoundWatcher.isSoundEnabled() /*&& !sound.playing()*/)
        {
            sound.play();
        }
    }
}
