package com.bhop.game.states;

import com.badlogic.gdx.Gdx;
import com.bhop.game.gameobjects.blackscreen.BlackScreen;
import com.bhop.game.gameobjects.booster.Booster;
import com.bhop.game.gameobjects.bunny.Bunny;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.gameobjects.carrot.DistanceIndexator;
import com.bhop.game.gameobjects.dustcloud.DustCloud;
import com.bhop.game.gameobjects.environment.LightObject;
import com.bhop.game.gameobjects.environment.Sky;
import com.bhop.game.gameobjects.environment.background.BackgroundGenerator;
import com.bhop.game.gameobjects.environment.cloud.CloudGenerator;
import com.bhop.game.gameobjects.environment.ground.GroundGenerator;
import com.bhop.game.gameobjects.gameinformation.AgainButton;
import com.bhop.game.gameobjects.gameinformation.BasicInfoProvider;
import com.bhop.game.gameobjects.gameinformation.BonusBackgroundInfoProvider;
import com.bhop.game.gameobjects.gameinformation.DetailedInfo;
import com.bhop.game.gameobjects.gameinformation.HighScoreInfoProvider;
import com.bhop.game.gameobjects.gameinformation.InfoIcon;
import com.bhop.game.gameobjects.gameinformation.SkinUnlockerInfo;
import com.bhop.game.gameobjects.indexator.Indexator;
import com.bhop.game.gameobjects.log.LogGenerator;
import com.bhop.game.gameobjects.pauseicon.PauseIcon;
import com.bhop.game.gameobjects.sound.MusicPlayer;
import com.bhop.game.gameobjects.sound.SoundIcon;
import com.bhop.game.gameobjects.timecounter.GameEndWatcher;
import com.bhop.game.gameobjects.timecounter.TimeCounter;
import com.bhop.game.utils.singleton.SingletonManager;

import static com.bhop.game.utils.GameStateUtils.updateGameObjects;
import static com.bhop.game.utils.singleton.SingletonManager.getSingleton;

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
