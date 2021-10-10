package org.mletkin.fractal.color;

import java.awt.Color;

public class Colouring {

    private int maxIterations;

    Colouring(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public Color toColor(int woop) {
        woop *= 256 / Math.min(maxIterations, 256);
        int blue = woop % 256;
        int green = (woop / 256) % 256;
        int red = (woop / 256 / 256) % 256;

        return new Color(red, green, blue);
    }

}
