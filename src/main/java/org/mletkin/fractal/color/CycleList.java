package org.mletkin.fractal.color;

import java.awt.Color;

/**
 * Cycle through colours from a list.
 */
public class CycleList extends ColorMapper {

    private Color[] list;

    public CycleList(int iterations, Color... colors) {
        this.setIterations(iterations);
        this.list = colors;
    }

    @Override
    public Color toColor(int woop) {
        int scaled = woop * 256 / iterations;
        Color one = color(scaled);
        return new Color( //
                scaled(scaled, one.getRed()), //
                scaled(scaled, one.getGreen()), //
                scaled(scaled, one.getBlue()));
    }

    private Color color(int woop) {
        return list[woop % list.length];
    }

    private int scaled(int scaled, int c) {
        return c > 0 ? scaled : 0;
    }

}
