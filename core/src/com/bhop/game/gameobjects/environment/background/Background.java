package com.bhop.game.gameobjects.environment.background;

import static com.bhop.game.utils.ImageUtils.*;
import static com.bhop.game.infoproviders.bonusbackground.BonusBackgroundUnlocker.*;
import static com.bhop.game.utils.GameUtils.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bhop.game.infoproviders.bonusbackground.BonusBackgroundUnlocker;
import com.bhop.game.utils.generator.GeneratedObject;
import com.bhop.game.utils.singleton.SingletonManager;

/**
 * Created by Ivan on 4/17/2016.
 */
public class Background extends GeneratedObject
{
    private boolean special;

    private final Texture specialBackground;

    private final Texture normalBackground;

    public Background(Texture image, float x)
    {
        super(image, new Vector2(x, 0));

        specialBackground = createTextureAccordingToTimePeriod("backgrounds/special" + generateRandomNumber() + "/");
        normalBackground = createTextureAccordingToTimePeriod("backgrounds/landscapes/");
    }

    @Override
    public void update(float delta, Vector3 touchPoint)
    {
        float cameraSpeed = movement.getMovementSpeed();

        delta *= cameraSpeed > 0 ? 1 : -1;

        coordinates.x -= cameraSpeed / 15 + delta;;
    }

    void converToSpecialBackground()
    {
        image = specialBackground;

        special = true;
    }

    void convertToNormalBackground()
    {
        if (special)
        {
            image = normalBackground;

            special = false;
        }
    }

    @Override
    public void dispose()
    {
        super.dispose();

        specialBackground.dispose();
        normalBackground.dispose();
    }

    private static int generateRandomNumber()
    {
        int unlocked = SingletonManager.getSingleton(BonusBackgroundUnlocker.class).getNumberOfUnlockedBonusBackgrounds();

        return RANDOM.nextInt(unlocked > MAX_BACKGROUNDS_TO_UNLOCK ? MAX_BACKGROUNDS_TO_UNLOCK + 1 : unlocked + 1);
    }
}