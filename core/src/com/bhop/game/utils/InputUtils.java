package com.bhop.game.utils;

import com.badlogic.gdx.Gdx;

/**
 * Created by Ivan on 4/15/2016.
 */
public final class InputUtils
{
    private InputUtils() {}

    public static boolean isInputInvoked()
    {
        return Gdx.input.justTouched();
    }
}
