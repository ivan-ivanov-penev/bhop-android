package com.bhop.game.gameobjects.gameinformation;

import static com.bhop.game.utils.GameUtils.*;
import static com.bhop.game.gameobjects.timecounter.GameEndWatcher.*;
import static com.bhop.game.utils.ImageUtils.*;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.gameobjects.BasicGameObject;
import com.bhop.game.gameobjects.carrot.CarrotManager;
import com.bhop.game.infoproviders.highscore.HighScoreManager;
import com.bhop.game.utils.font.Font;
import com.bhop.game.utils.singleton.Singleton;
import com.bhop.game.utils.singleton.SingletonClass;
import com.bhop.game.utils.singleton.SingletonManager;

@SingletonClass
public class HighScoreInfoProvider extends BasicGameObject implements Singleton
{

	private final CarrotManager carrotManager;
	
	private final HighScoreManager highScoreManager;
	
	private final HighScoreMessages highScoreMessages;
	
	private HighScoreInfoProvider()
	{
		super(createTexture("signs/highscore2"), new Vector2());

		coordinates.x = (WINDOW_WIDTH - image.getWidth()) / 2;
        coordinates.y = WINDOW_HEIGHT - image.getHeight() * 2 + image.getHeight() / 10;
		
		carrotManager = SingletonManager.getSingleton(CarrotManager.class);
		highScoreManager = SingletonManager.getSingleton(HighScoreManager.class);

		highScoreMessages = new HighScoreMessages();
	}

	@Override
	public void update(float delta, Vector3 touchPoint) {}

	@Override
	public void render(SpriteBatch spriteBatch)
	{
		if (isGameEnd())
		{
            spriteBatch.draw(image, coordinates.x, WINDOW_HEIGHT - image.getHeight());
			spriteBatch.draw(image, coordinates.x, coordinates.y);
			
			int currentScore = carrotManager.getCarrotCounter();

			Font[] fonts = highScoreMessages.getCorrectFontMessages(currentScore);

            fonts[0].draw(spriteBatch, coordinates.x + (image.getWidth() - fonts[0].getWidth()) / 2, coordinates.y + image.getHeight() * 0.45f);
            fonts[1].draw(spriteBatch, coordinates.x + (image.getWidth() - fonts[1].getWidth()) / 2, WINDOW_HEIGHT - image.getHeight() + image.getHeight() * 0.45f);
			
			highScoreManager.rewriteHighScoreIfGreater(currentScore);
		}
	}

    @Override
    public void dispose()
    {
        super.dispose();

        highScoreMessages.dispose();
    }

    private class HighScoreMessages
	{
		private Font firstMessage;

		private Font secondMessage;

		private Font firstMessageNewHighscore;

		private Font secondMessageNewHighscore;

        private boolean fontsCreated;

		public Font[] getCorrectFontMessages(int currentScore)
		{
            attemptFontCreation(currentScore);

			if (currentScore > highScoreManager.getHighScore())
			{
				return new Font[] { firstMessageNewHighscore, secondMessageNewHighscore };
			}

			return new Font[] { firstMessage, secondMessage };
		}

        private void attemptFontCreation(int currentScore)
        {
            if (!fontsCreated)
            {
                firstMessage = new Font("Current score: " + currentScore).setSize(20);
                secondMessage = new Font("High score: " + highScoreManager.getHighScore()).setSize(20);
                firstMessageNewHighscore = new Font("New high score: " + currentScore).setSize(20);
                secondMessageNewHighscore = new Font("Old high score: " + highScoreManager.getHighScore()).setSize(20);

                fontsCreated = true;
            }
        }

        public void dispose()
        {
            firstMessage.dispose();
            secondMessage.dispose();
            firstMessageNewHighscore.dispose();
            secondMessageNewHighscore.dispose();
        }
	}

}
