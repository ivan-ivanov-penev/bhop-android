package com.bhop.game.gameobjects.gameinformation;

import com.bhop.game.utils.GameUtils;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;


@SingletonClass
public class BonusBackgroundInfoProvider extends BasicPopup implements Singleton
{
	
	private static boolean justUnlockedBonusBackground;
	
	private int frameCounter;
	
	private BonusBackgroundInfoProvider()
	{
		justUnlockedBonusBackground = false;
	}
	
	public static void alertBonusBackroundIsUnlocked()
	{
		justUnlockedBonusBackground = true;
	}

	@Override
	protected void attemptPopup()
	{
		frameCounter += 1;
		
		if (frameCounter < GameUtils.FPS * 5)
		{
			popup();
		}
		else
		{
			hide();
		}
	}

	@Override
	protected String setMessage()
	{
		return "You have unlocked a new special background!";
	}

	@Override
    protected boolean hasToPopup()
    {
	    return justUnlockedBonusBackground;
    }

}
