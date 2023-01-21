package com.bhop.game.gameobjects.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.GameObject;
import com.bhop.game.gameobjects.booster.Booster;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.utils.GameUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

@SingletonClass
public class LogGenerator implements GameObject, Singleton
{

	private final List<Log> logs;

	private final Random random;
	
	private final CarrotManager carrotManager;

	private LogGenerator()
	{
//		Log.initTypeToPixelLocations();

		random = new Random();
		logs = new ArrayList<Log>();
		carrotManager = SingletonManager.getSingleton(CarrotManager.class);
		
		Log.setLogKind();
	}

	public void manage()
	{
		attemptToCreateLog();
		clearLogsOutsideScrren();
	}

	private void attemptToCreateLog()
	{
        int cap = Booster.isBoosterAcquired() ? 5 : 10;

		if (carrotManager.gameHasBegan() && random.nextInt(cap) == 1)
		{
			attemptToAddNewLog();
		}
	}

	private void attemptToAddNewLog()
    {
	    if (logs.isEmpty())
	    {
	    	logs.add(new Log(carrotManager.getCarrot()));
	    }
	    else if (logs.get(logs.size() - 1).getX() < GameUtils.WINDOW_WIDTH - 400)
	    {
	    	logs.add(new Log(carrotManager.getCarrot()));
	    }
    }

	private void clearLogsOutsideScrren()
	{
		if (!logs.isEmpty() && logs.get(0).getX() < -GameUtils.WINDOW_WIDTH * 2)
		{
            logs.get(0).dispose();

			logs.remove(0);
		}
	}

	public List<Log> getAllLogs()
	{
		return logs;
	}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		for (Log log : logs)
		{
			log.render(spriteBatch);
		}
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		manage();
		
		for (Log log : logs)
		{
			log.update(delta, touchPoint);
		}
	}

    @Override
    public void dispose()
    {
        for (Log log : logs)
        {
            log.dispose();
        }
    }
}
