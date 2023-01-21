package com.bhop.game.gameobjects.gameinformation;

import static com.bhop.game.utils.GameUtils.*;

import com.bhop.game.infoproviders.bonuscolor.BonusSkinUnlocker;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

@SingletonClass
public class SkinUnlockerInfo extends BasicPopup implements Singleton
{
	
	private boolean playerHasUnlockedBonus;
	
	private boolean hasToPopUp;
	
	private int frameCounter;
	
	private SkinUnlockerInfo()
	{
		playerHasUnlockedBonus = SingletonManager.getSingleton(BonusSkinUnlocker.class).playerHasUnlockedBonus();
	}

	@Override
    protected boolean hasToPopup()
    {
		if (!hasToPopUp)
		{
			hasToPopUp = !playerHasUnlockedBonus && carrotManager.playerJustUnlockedBonus();
		}
		
	    return hasToPopUp;
    }

	@Override
	protected void attemptPopup()
	{
		if (frameCounter < FPS * 5)
		{
			frameCounter += 1;
			
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
		return "You have unlocked a new bonus skin for bunny!";
	}

}
