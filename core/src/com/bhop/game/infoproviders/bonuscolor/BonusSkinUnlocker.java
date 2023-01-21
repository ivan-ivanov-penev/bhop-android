package com.bhop.game.infoproviders.bonuscolor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class BonusSkinUnlocker implements Singleton
{

	private Preferences preferences;
	
	private BonusSkinUnlocker()
	{
		preferences = Gdx.app.getPreferences("bonus_skin");
	}
	
	public boolean playerHasUnlockedBonus()
	{
		return preferences.getBoolean("bonusUnlocked", false);
	}
	
	public void unlockBonus()
	{
		preferences.putBoolean("bonusUnlocked", true);
		preferences.flush();
	}

}
