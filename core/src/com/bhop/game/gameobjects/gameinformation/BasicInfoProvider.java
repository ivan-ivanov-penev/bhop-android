package com.bhop.game.gameobjects.gameinformation;

import com.badlogic.gdx.math.Vector3;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class BasicInfoProvider extends BasicPopup implements Singleton
{
	
	private BasicInfoProvider() {}

	@Override
	protected String setMessage()
	{
		return "Get the carrot before the time runs out!";
	}
	
	@Override
	public void update(float delta, Vector3 touchPoint)
	{
		super.update(delta, touchPoint);
	    super.update();
	}

	@Override
    protected void attemptPopup()
    {
		if (!carrotManager.gameHasBegan())
		{
			popup();
		}
		else
		{
			hide();
		}
    }

	@Override
    protected boolean hasToPopup()
    {
	    return !carrotManager.gameHasBegan();
    }

}
