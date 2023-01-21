package com.bhop.game.infoproviders.bonusbackground;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.bhop.game.gameobjects.gameinformation.BonusBackgroundInfoProvider;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;

@SingletonClass
public class BonusBackgroundUnlocker implements Singleton
{
	public static final int MAX_BACKGROUNDS_TO_UNLOCK = 2;

    private Preferences preferences;

    private int totalCarrots;

    private int numberOfUnlockedSpecialBackgrounds;
	
	private BonusBackgroundUnlocker()
	{
        preferences = Gdx.app.getPreferences("bonus_background");

        totalCarrots = preferences.getInteger("totalCarrots", 0);
	}
	
	public void alertBunnyHasPickedUpCarrot()
	{
        totalCarrots += 1;
		
		if (totalCarrots == 300 && numberOfUnlockedSpecialBackgrounds < MAX_BACKGROUNDS_TO_UNLOCK)
		{
			unlockedNewSpecialBackgrounds();
			
			BonusBackgroundInfoProvider.alertBonusBackroundIsUnlocked();
		}
	}

    public void saveTotalCarrots()
    {
        preferences.putInteger("totalCarrots", totalCarrots);
        preferences.flush();
    }

    private void unlockedNewSpecialBackgrounds()
    {
        totalCarrots = 0;
        numberOfUnlockedSpecialBackgrounds += 1;

        preferences.putInteger("totalCarrots", totalCarrots);
        preferences.putInteger("numberOfUnlockedSpecialBackgrounds", numberOfUnlockedSpecialBackgrounds);
        preferences.flush();
    }
	
	public int getNumberOfUnlockedBonusBackgrounds()
	{
		return numberOfUnlockedSpecialBackgrounds;
	}

}
