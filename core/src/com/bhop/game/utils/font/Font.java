package com.bhop.game.utils.font;

import static com.bhop.game.utils.FontUtils.*;
import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Ivan on 4/19/2016.
 */
public class Font
{
    private final BitmapFont font;

    private final GlyphLayout layout;

    private String message;

    public Font(String message)
    {
        this(createConfiguredBitmapFont(), message);
    }

    public Font(BitmapFont font, String message)
    {
        this.font = font;
        this.message = message;
        this.layout = new GlyphLayout(font, message);
    }

    public Font setSize(float size)
    {
        font.getData().setScale(size / DEFAULT_SIZE);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        layout.setText(font, message);

        return this;
    }

    public Font setColor(Color color)
    {
        font.setColor(color);

        return this;
    }

    public void draw(SpriteBatch spriteBatch, float x, float y)
    {
        font.draw(spriteBatch, message, x, y);
    }

    public void setMessage(String message)
    {
        if (!this.message.equals(message))
        {
            this.message = message;
            this.layout.setText(font, message);
        }
    }

    public void dispose()
    {
        font.dispose();
    }

    public float getWidth()
    {
        return layout.width;
    }

    public float getHeight()
    {
        return layout.height;
    }

    public void specialDraw(SpriteBatch spriteBatch, float x, float y)
    {
        font.draw(spriteBatch, message, x, y, WINDOW_WIDTH * 0.8f, 5, true);
    }
}
