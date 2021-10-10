package org.mletkin.fractal.color;

import java.awt.Color;

/**
 * Class for mapping index values to colors.
 */
public abstract class ColorMapper {

    protected int iterations;

    public abstract Color toColor(int woop);

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

}
