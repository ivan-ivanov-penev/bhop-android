package com.bhop.game.utils.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bhop.game.utils.GameUtils;

/**
 * Created by Ivan on 4/18/2016.
 */
public class Animation
{
    private final Texture[] frames;

    private int fps;

    private int fpsIndex;

    private int frameIndex;

    private boolean pingPong;

    private boolean increment;

    private boolean stopped;

    public Animation(Texture[] frames, int fps)
    {
        this.frames = frames;
        this.fps = fps;
        this.increment = true;
    }

    public Texture getCurrentFrame()
    {
        return frames[frameIndex];
    }

    public int getFrameIndex()
    {
        return frameIndex;
    }

    public void update()
    {
        fpsIndex += 1;

        if (fpsIndex >= fps)
        {
            incrementFrameIndex();

            fpsIndex = 0;
        }
    }

    public void setSpeed(float speed)
    {
        fps /= speed;
    }

    public void setSpecialSpeed(float speed)
    {
        fps = (int) ((GameUtils.FPS / speed) / frames.length);
    }

    private int incrementFrameIndex()
    {
        if (increment)
        {
            frameIndex += 1;
        }
        else
        {
            frameIndex -= 1;

            if (frameIndex < 0)
            {
                frameIndex = 0;

                increment = true;
            }
        }

        if (frameIndex == frames.length)
        {
            if (pingPong)
            {
                frameIndex -= 1;

                increment = false;
            }
            else
            {
                frameIndex = 0;
            }
        }

        return frameIndex;
    }

    public void dispose()
    {
        for (Texture frame : frames)
        {
            frame.dispose();
        }
    }

    public void render(SpriteBatch spriteBatch, float x, float y)
    {
        //if (!stopped)
        {
            spriteBatch.draw(frames[frameIndex], x, y);
        }
    }

    public void setPingPong(boolean pingPong)
    {
        this.pingPong = pingPong;
    }

    public int getFrameCount()
    {
        return frames.length;
    }

    public boolean isStopped()
    {
        return stopped;
    }

    public void stop()
    {
        stopped = true;
    }

    public void start()
    {
        stopped = false;
    }

    public float getWidth()
    {
        return getCurrentFrame().getWidth();
    }

    public float getHeight()
    {
        return getCurrentFrame().getHeight();
    }
}
