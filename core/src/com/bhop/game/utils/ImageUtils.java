package com.bhop.game.utils;

import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.PixelLocation;
import com.bhop.game.utils.animation.Animation;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

/**
 * Created by Ivan on 4/15/2016.
 */
public final class ImageUtils
{
    private ImageUtils() {}

    private static final java.util.Map<String, Texture> ALL_TEXTURES = new HashMap<String, Texture>();

    public static void fillAllImages(String path)
    {
        FileHandle[] handles = Gdx.files.internal(path).list();

        for (FileHandle handle : handles)
        {
            String fullPath = path + "/" + handle.name();

            if (Gdx.files.internal(fullPath).isDirectory())
            {
                fillAllImages(fullPath);
            }
            else
            {
//                ALL_TEXTURES.put(path + handle.name(), new Texture("sprites/" + filename + ".png"));
                Texture texture = new Texture(fullPath);
                texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

                ALL_TEXTURES.put(fullPath, texture);
            }
        }
    }

    public static Set<PixelLocation> getPixelsLocations(String fileName)
    {
        Set<PixelLocation> locationToPixelPresence = new HashSet<PixelLocation>();

        try
        {
            //fillSetWithLocations(getWritableRaster(fileName), locationToPixelPresence);
            fillSetWithLocations(fileName, locationToPixelPresence);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return locationToPixelPresence;
    }

    private static void fillSetWithLocations(String fileName, Set<PixelLocation> locationToPixelPresence)
    {
        Texture texture = createTexture(fileName.replace(".png", ""));

        TextureData textureData = texture.getTextureData();
        textureData.prepare();

        Pixmap pixmap = textureData.consumePixmap();

        ByteBuffer pixels = pixmap.getPixels();

        ArrayList<Byte> bytes = new ArrayList<Byte>();

        int limit = pixels.limit();

        for (int i = 0; i < limit; i++)
        {
            bytes.add(pixels.get());
        }

        int counter = 3;

        for (int i = texture.getHeight() - 1; i >= 0; i--)
        {
            for (int j = 0; j < texture.getWidth(); j++)
            {
                //if (bytes.get(counter) != 0)
                if (bytes.get(counter) < 0)
                {
                    locationToPixelPresence.add(new PixelLocation(j, i));
                    //locations.add(new PixelLocation(j, i));
                }

                counter += 4;
            }
        }

        //int k = 0;
        //
        //for (int i = texture.getHeight() - 1; i >= 0; i--)
        //{
        //    for (int j = 0; j < texture.getWidth(); j++)
        //    {
        //        for (PixelLocation location : locations)
        //        {
        //            if (location.getX() == j && location.getY() == i)
        //            {
        //                System.out.print("0");
        //            }
        //        }
        //        System.out.print(" ");
        //    }
        //
        //    System.out.println();
        //}
    }

    private static void fillSetWithLocations(WritableRaster raster, Set<PixelLocation> locationToPixelPresence)
    {
        byte[] bytes = ((DataBufferByte) raster.getDataBuffer()).getData();

        int counter = 0;

        for (int i = raster.getHeight() - 1; i >= 0; i--)
        {
            for (int j = 0; j < raster.getWidth(); j++)
            {
                if (bytes[counter] != 0)
                {
                    locationToPixelPresence.add(new PixelLocation(j, i));
                }

                counter += 4;
            }
        }

        //for (int i = 0; i < raster.getHeight(); i++)
        //{
        //    for (int j = 0; j < raster.getWidth(); j++)
        //    {
        //        if (bytes[counter] != 0)
        //        {
        //            locationToPixelPresence.add(new PixelLocation(j, i));
        //        }
        //
        //        counter += 4;
        //    }
        //}
    }

    private static WritableRaster getWritableRaster(String filename) throws Exception
    {
        InputStream inputStream = Gdx.files.internal("sprites/" + filename).read();

        BufferedImage bufferedImage = ImageIO.read(inputStream);

        return bufferedImage.getRaster();
    }

    public static Texture createTexture(String filename)
    {
//        Texture texture = ALL_TEXTURES.get("sprites/" + filename + ".png");
        Texture texture = new Texture("sprites/" + filename + ".png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        return texture;
    }

    public static Texture createTextureAccordingToTimePeriod(String path)
    {
        return createTexture(path + getTimePeriod());
    }

    public static Texture[] createImageArrayFromDirectorySafely(String path)
    {
        FileHandle[] files = Gdx.files.internal("sprites/" + path).list();

        Texture[] frames = new Texture[files.length];

        for (int i = 0; i < files.length; i++)
        {
            frames[i] = createTexture(path + "/" + files[i].name().replace(".png", ""));
        }

        return frames;
    }

    public static Animation createAnimation(String dirName)
    {
        return new Animation(createImageArrayFromDirectorySafely(dirName), FPS);
    }

    public static Animation createConfiguredAnimation(String dirName)
    {
        Texture[] array = createImageArrayFromDirectorySafely(dirName);

        return new Animation(array, FPS / array.length);
    }

    public static boolean isOverImage(Vector3 touchPoint, Texture texture, float x, float y)
    {
        boolean horizontalOverlap = touchPoint.x > x && touchPoint.x < texture.getWidth() + x;
        boolean verticalOverlap = touchPoint.y > y && touchPoint.y < texture.getHeight() + y;

        return horizontalOverlap && verticalOverlap;
    }

    public static boolean isOverImage(Vector3 touchPoint, BasicGameObject gameObject)
    {
        return isOverImage(touchPoint, gameObject.getImage(), gameObject.getX(), gameObject.getY());
    }
}
