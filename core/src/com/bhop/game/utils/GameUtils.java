package com.bhop.game.utils;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Ivan on 4/15/2016.
 */
public final class GameUtils
{
    public static final Random RANDOM = new Random();

    public static final String GAME_NAME = "BHop";

    public static final int FPS = 60;

    public static final int WINDOW_WIDTH = 720;

    public static final int WINDOW_HEIGHT = 480;

    public static final int BUNNY_STARTING_X = WINDOW_WIDTH / 9;

    private GameUtils () {}

    private static String timePeriod = calculateTimePeriodAccordingToTimeOfDay();

    public static String getTimePeriod()
    {
        return timePeriod;
    }

    public static void reloadTimePeriod()
    {
        timePeriod = calculateTimePeriodAccordingToTimeOfDay();
    }

    private static String calculateTimePeriodAccordingToTimeOfDay()
    {
        int hoursOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hoursOfDay > 5 && hoursOfDay < 12)
        {
            return "dawn";
        }

        if (hoursOfDay < 20)
        {
            return "day";
        }

        return "night";
    }

    public static <T>T getRandomElement(T[] elements)
    {
        return elements[RANDOM.nextInt(elements.length)];
    }
}
