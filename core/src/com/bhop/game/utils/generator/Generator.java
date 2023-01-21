package com.bhop.game.utils.generator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.environment.ground.Ground;
import com.bhop.game.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 4/17/2016.
 */
public abstract class Generator implements GameObject
{
    protected List<GeneratedObject> generatedObjects;

    protected Generator(int numberOfGeneratedObjects)
    {
        generatedObjects = new ArrayList<GeneratedObject>();

        fillGeneratedObjects(numberOfGeneratedObjects);
    }

    protected void fillGeneratedObjects(int numberOfGeneratedObjects)
    {
        for (int i = 0; i < numberOfGeneratedObjects; i++)
        {
            generatedObjects.add(createGeneratedObject(i));
        }
    }

    protected abstract GeneratedObject createGeneratedObject(int index);

    @Override
    public void update(float delta, Vector3 touchPoint)
    {
        for (GeneratedObject generatedObject : generatedObjects)
        {
            manageObject(generatedObject);

            generatedObject.update(delta, touchPoint);
        }
    }

    protected void manageObject(GeneratedObject generatedObject)
    {
        if (generatedObject.getX() < -generatedObject.getImageWidth())
        {
            generatedObject.reposition(getLastObjectX() + generatedObject.getImageWidth() /*- 5*/);
        }
        else if (generatedObject.getX() > GameUtils.WINDOW_WIDTH)
        {
            generatedObject.reposition(getFirstObjectX() - generatedObject.getImageWidth() /*+ 5*/);
        }
    }

    protected float getLastObjectX()
    {
        float largestX = 0;

        for (GeneratedObject generatedObject : generatedObjects)
        {
            if (generatedObject.getX() > largestX)
            {
                largestX = generatedObject.getX();
            }
        }

        return largestX;
    }

    protected float getFirstObjectX()
    {
        float smallestX = 30;

        for (GeneratedObject generatedObject : generatedObjects)
        {
            if (generatedObject.getX() < smallestX)
            {
                smallestX = generatedObject.getX();
            }
        }

        return smallestX;
    }

    protected void fixXCoordinateOnGeneratedObject()
    {
        for (int i = 1; i < generatedObjects.size(); i++)
        {
//            if (generatedObjects.get(i).getX() >)
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch)
    {
        for (GeneratedObject generatedObject : generatedObjects)
        {
            generatedObject.render(spriteBatch);
        }
    }

    @Override
    public void dispose()
    {
        for (GeneratedObject gameObject : generatedObjects)
        {
            gameObject.dispose();
        }
    }
}
