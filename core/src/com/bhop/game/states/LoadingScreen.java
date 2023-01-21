package com.bhop.game.states;

import com.badlogic.gdx.Gdx;
import com.bhop.game.gameobjects.loaingscreen.LoadingScreenManager;
import com.bhop.game.utils.ImageUtils;

import static com.bhop.game.utils.GameUtils.FPS;
import static com.bhop.game.utils.GameUtils.RANDOM;
import static com.bhop.game.utils.singleton.SingletonManager.getSingleton;

public class LoadingScreen extends GameState
{

    private static final double LOADING_TIME_SECONDS = RANDOM.nextInt(4) + 3 + Math.random();

    private int counter;

    private volatile Menu menu;

    public LoadingScreen()
    {
//        ImageUtils.fillAllImages("sprites");

        gameObjects.add(getSingleton(LoadingScreenManager.class));

//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Gdx.app.postRunnable(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        menu = new Menu();
//                    }
//                });
//            }
//        }).start();

        new Runnable()
        {
            @Override
            public void run()
            {
                Gdx.app.postRunnable(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        menu = new Menu();
                    }
                });
            }
        }.run();
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if (counter >= FPS * LOADING_TIME_SECONDS)
        {
//            counter = -FPS * 500;

			gameStateManager.enterState(menu);
        } else
        {
            counter += 1;
        }
    }

}
