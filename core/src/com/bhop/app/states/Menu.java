package com.bhop.app.states;

import com.bhop.app.gameobjects.bunny.Bunny;
import com.bhop.app.gameobjects.bunny.dummy.DummyBunny;
import com.bhop.app.gameobjects.coloroptions.ColorOption.BunnyColor;
import com.bhop.app.gameobjects.coloroptions.ColorOptionManager;
import com.bhop.app.gameobjects.environment.LightObject;
import com.bhop.app.gameobjects.environment.Sky;
import com.bhop.app.gameobjects.environment.background.BackgroundGenerator;
import com.bhop.app.gameobjects.environment.cloud.CloudGenerator;
import com.bhop.app.gameobjects.environment.ground.GroundGenerator;
import com.bhop.app.gameobjects.gameinformation.DetailedInfo;
import com.bhop.app.gameobjects.gameinformation.InfoIcon;
import com.bhop.app.gameobjects.sound.MusicPlayer;
import com.bhop.app.gameobjects.sound.SoundIcon;
import com.bhop.app.gameobjects.timecounter.GameEndWatcher;
import com.bhop.app.utils.GameUtils;
import com.bhop.app.utils.singleton.SingletonManager;

import static com.bhop.app.utils.singleton.SingletonManager.getSingleton;

/**
 * Created by Ivan on 4/15/2016.
 */
public class Menu extends GameState
{
    private static boolean pickedColorByPlayer;

    private Play play;

    public static void informPlayerHasPickedColor(BunnyColor bunnyColor)
    {
        getSingleton(Bunny.class).setBunnyAnimations(bunnyColor);

        pickedColorByPlayer = true;
    }

    public Menu()
    {
        SingletonManager.clearSingletons();
        GameEndWatcher.restartGame();
        GameUtils.reloadTimePeriod();

        pickedColorByPlayer = false;

        gameObjects.add(getSingleton(Sky.class));
        gameObjects.add(getSingleton(LightObject.class));
        gameObjects.add(getSingleton(CloudGenerator.class));
        gameObjects.add(getSingleton(BackgroundGenerator.class));
        gameObjects.add(getSingleton(GroundGenerator.class));
        gameObjects.add(getSingleton(DummyBunny.class));
        gameObjects.add(getSingleton(ColorOptionManager.class));
        gameObjects.add(getSingleton(SoundIcon.class));
        gameObjects.add(getSingleton(InfoIcon.class));
        gameObjects.add(getSingleton(DetailedInfo.class));
        gameObjects.add(getSingleton(MusicPlayer.class));

        play = new Play();
    }

    @Override
    public void update(float delta)
    {
        if (pickedColorByPlayer)
        {
            pickedColorByPlayer = false;

            gameStateManager.enterState(play);

//            gameObjects = play.gameObjects;
        }
        else
        {
            super.update(delta);
        }
    }
}
