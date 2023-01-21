package com.bhop.game.gameobjects.timecounter;

import com.badlogic.gdx.Gdx;
import com.bhop.game.gameobjects.sound.SoundPlayer;
import com.bhop.game.infoproviders.bonusbackground.BonusBackgroundUnlocker;
import com.bhop.game.states.Menu;
import com.bhop.game.states.Play;
import com.bhop.game.utils.singleton.SingletonManager;

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
