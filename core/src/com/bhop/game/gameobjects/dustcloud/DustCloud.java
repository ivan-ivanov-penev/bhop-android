package com.bhop.game.gameobjects.dustcloud;

import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.log.Log;
import com.bhop.game.utils.ImageUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

/**
 * Created by ivan_ on 5/20/2016.
 */
@SingletonClass
public class DustCloud extends BasicGameObject implements Singleton
{
    private boolean toRender;

    private float scale;

    private float transperancy;

    private Log hitLog;

    public DustCloud()
    {
        super(ImageUtils.createTexture("colision_dust_clouds/dust_cloud1"), new Vector2(BUNNY_STARTING_X * 1.5f, WINDOW_HEIGHT * 0.3f));

        resetScaleAndTransperency();
    }

    private void resetScaleAndTransperency()
    {
        scale = 0.75f;
        transperancy = 1;
    }

    public void alertHasToRender()
    {
        toRender = true;

        resetScaleAndTransperency();
    }

    public void setLog(Log log)
    {
        hitLog = log;

//        coordinates.x = hitLog.getX();
        coordinates.y = hitLog.getY() + log.getImageHeight() * 0.5f;
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {
        if (toRender)
        {
            spriteBatch.setColor(1, 1, 1, transperancy);
            spriteBatch.draw(image, coordinates.x - image.getWidth() * scale * 0.5f, coordinates.y + image.getHeight() * scale * 0.5f, image.getWidth() * scale, image.getHeight() * scale);
            spriteBatch.setColor(1, 1, 1, 1);
        }
    }

    @Override
    public void update(float delta, Vector3 touchPoint)
    {
        if (transperancy <= 0)
        {
            toRender = false;

            resetScaleAndTransperency();
        }
        else if (toRender)
        {
            scale += 0.02f;
            transperancy -= 0.05f;

            if (transperancy < 0)
            {
                transperancy = 0;
            }

            coordinates.x = hitLog.getX() + hitLog.getImageWidth() * 0.5f;
//            coordinates.y = hitLog.getY();
        }
    }
}
