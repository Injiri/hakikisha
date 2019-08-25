package org.aplusscreators.hakikisha.utils;

import android.graphics.Color;

import java.util.Random;

public class ColorTool {

    public static int getRandomDarkColor() {

        return generateRandomColour();
    }

    private static int generateRandomColour() {

        Random random = new Random();
        int color = Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255));

        return color;
    }
}

