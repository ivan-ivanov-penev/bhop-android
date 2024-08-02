package com.bhop.app.gameobjects.timecounter;

import com.bhop.app.gameobjects.sound.SoundPlayer;
import com.bhop.app.infoproviders.bonusbackground.BonusBackgroundUnlocker;
import com.bhop.app.utils.singleton.SingletonManager;

public final class GameEndWatcher
{
	
	private static boolean gameEnd = false;
	
	private static SoundPlayer soundPlayer = createSoundPlayer();

	private static SoundPlayer createSoundPlayer()
	{
		return new SoundPlayer("game_over2.wav");
	}
	
	private GameEndWatcher() {}
	
	static void alertGameHasEnded()
	{
		gameEnd = true;

		soundPlayer.playSoundOnce();

        SingletonManager.getSingleton(BonusBackgroundUnlocker.class).saveTotalCarrots();
	}

	public static boolean isGameEnd()
	{
		return gameEnd;
	}
	
	public static void restartGame()
	{
		gameEnd = false;
		
		soundPlayer.alertSoundHasToBePlayed();
	}

}
