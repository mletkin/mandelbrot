package org.mletkin.fractal.color;

import java.awt.Color;

public class Blue extends ColorMapper {

    public Blue(int iterations) {
        this.setIterations(iterations);
    }

    @Override
    public Color toColor(int woop) {
        return new Color(0, 0, woop * 256 / iterations);
    }

}
