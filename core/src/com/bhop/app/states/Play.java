package com.bhop.app.states;

import com.badlogic.gdx.Gdx;
import com.bhop.app.gameobjects.blackscreen.BlackScreen;
import com.bhop.app.gameobjects.booster.Booster;
import com.bhop.app.gameobjects.bunny.Bunny;
import com.bhop.app.gameobjects.carrot.CarrotManager;
import com.bhop.app.gameobjects.carrot.DistanceIndexator;
import com.bhop.app.gameobjects.dustcloud.DustCloud;
import com.bhop.app.gameobjects.environment.LightObject;
import com.bhop.app.gameobjects.environment.Sky;
import com.bhop.app.gameobjects.environment.background.BackgroundGenerator;
import com.bhop.app.gameobjects.environment.cloud.CloudGenerator;
import com.bhop.app.gameobjects.environment.ground.GroundGenerator;
import com.bhop.app.gameobjects.gameinformation.AgainButton;
import com.bhop.app.gameobjects.gameinformation.BasicInfoProvider;
import com.bhop.app.gameobjects.gameinformation.BonusBackgroundInfoProvider;
import com.bhop.app.gameobjects.gameinformation.DetailedInfo;
import com.bhop.app.gameobjects.gameinformation.HighScoreInfoProvider;
import com.bhop.app.gameobjects.gameinformation.InfoIcon;
import com.bhop.app.gameobjects.gameinformation.SkinUnlockerInfo;
import com.bhop.app.gameobjects.indexator.Indexator;
import com.bhop.app.gameobjects.log.LogGenerator;
import com.bhop.app.gameobjects.pauseicon.PauseIcon;
import com.bhop.app.gameobjects.sound.MusicPlayer;
import com.bhop.app.gameobjects.sound.SoundIcon;
import com.bhop.app.gameobjects.timecounter.GameEndWatcher;
import com.bhop.app.gameobjects.timecounter.TimeCounter;

import static com.bhop.app.utils.GameStateUtils.updateGameObjects;
import static com.bhop.app.utils.singleton.SingletonManager.getSingleton;

/**
 * Created by Ivan on 4/15/2016.
 */
public class Play extends GameState
{
    private static boolean playerWantsToRestart;

    public static void alertPlayerWantsToRestart()
    {
        playerWantsToRestart = true;
    }

    public Play()
    {
        playerWantsToRestart = false;

        gameObjects.add(getSingleton(Sky.class));
        gameObjects.add(getSingleton(LightObject.class));
        gameObjects.add(getSingleton(CloudGenerator.class));
        gameObjects.add(getSingleton(BackgroundGenerator.class));
        gameObjects.add(getSingleton(GroundGenerator.class));
        gameObjects.add(getSingleton(SoundIcon.class));
        gameObjects.add(getSingleton(PauseIcon.class));
        gameObjects.add(getSingleton(InfoIcon.class));
        gameObjects.add(getSingleton(BasicInfoProvider.class));
        gameObjects.add(getSingleton(BonusBackgroundInfoProvider.class));
        gameObjects.add(getSingleton(SkinUnlockerInfo.class));
        gameObjects.add(getSingleton(Booster.class));
        gameObjects.add(getSingleton(MusicPlayer.class));
        gameObjects.add(getSingleton(DustCloud.class));
        gameObjects.add(getSingleton(LogGenerator.class));
        gameObjects.add(getSingleton(CarrotManager.class));
        gameObjects.add(getSingleton(TimeCounter.class));
        gameObjects.add(getSingleton(Indexator.class));
        gameObjects.add(getSingleton(DistanceIndexator.class));
        gameObjects.add(getSingleton(BlackScreen.class));
        gameObjects.add(getSingleton(Bunny.class));
        gameObjects.add(getSingleton(DetailedInfo.class));
        gameObjects.add(getSingleton(AgainButton.class));
        gameObjects.add(getSingleton(HighScoreInfoProvider.class));
    }

    @Override
    public void update(float delta)
    {
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        enterMenuStateIfPlayerWantsToRestart();

        if (GameEndWatcher.isGameEnd())
        {
            getSingleton(AgainButton.class).update(delta, touchPoint);
        }
        else if(InfoIcon.isPlayerIsReadingInfo())
        {
            getSingleton(InfoIcon.class).update(delta, touchPoint);
            getSingleton(SoundIcon.class).update(delta, touchPoint);
            //getSingleton(ClickCirclesGenerator.class).update(delta, touchPoint);
        }
        else if (PauseIcon.isGamePaused())
        {
            getSingleton(PauseIcon.class).update(delta, touchPoint);
            getSingleton(SoundIcon.class).update(delta, touchPoint);
            getSingleton(InfoIcon.class).update(delta, touchPoint);
            getSingleton(MusicPlayer.class).update(delta, touchPoint);
            //getSingleton(ClickCirclesGenerator.class).update(delta, touchPoint);
        }
        else
        {
            updateGameObjects(gameObjects, delta, touchPoint);
        }
    }

    private void enterMenuStateIfPlayerWantsToRestart()
    {
        if (playerWantsToRestart)
        {
            gameObjects.clear();

            playerWantsToRestart = false;

            gameStateManager.enterState(new Menu());
        }
    }
}
