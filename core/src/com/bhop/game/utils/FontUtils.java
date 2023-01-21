package com.bhop.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Ivan on 4/18/2016.
 */
public final class FontUtils
{
    private FontUtils() {};

    public static final int DEFAULT_SIZE = 25;

    public static final Color COLOR_DEFAULT = new Color(0.294f, 0.2f, 0.11f, 1);

    public static final Color COLOR_BLACKISH = new Color(0.157f, 0.118f, 0.078f, 1);

    public static BitmapFont createConfiguredBitmapFont()
    {
        return createConfiguredBitmapFont(DEFAULT_SIZE, COLOR_DEFAULT);
    }

    public static BitmapFont createConfiguredBitmapFont(float size)
    {
        return createConfiguredBitmapFont(size, COLOR_DEFAULT);
    }

    public static BitmapFont createConfiguredBitmapFont(Color color)
    {
        return createConfiguredBitmapFont(DEFAULT_SIZE, color);
    }

    public static BitmapFont createConfiguredBitmapFont(float size, Color color)
    {
        BitmapFont font = new BitmapFont(Gdx.files.internal("font/font25.fnt"));
        font.getData().setScale(size / DEFAULT_SIZE);
        font.setColor(color);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        return font;
    }
}
