package org.mletkin.fractal.color;

import java.awt.Color;

/**
 * {@code ColorMapper} factory for the combobox.
 */
public class ColorMapperFactory {

    /**
     * Available color mappers.
     */
    public enum Cm {
        Blue,
        BlueRed,
        GreenYellow,
        Green,
        RedYellowGreen,
    }

    public static ColorMapper make(Cm type, int iterations) {
        switch (type) {
        case Blue:
            return new Blue(iterations);
        case Green:
            return new CycleList(iterations, Color.GREEN);
        case BlueRed:
            return new CycleList(iterations, Color.BLUE, Color.RED);
        case GreenYellow:
            return new CycleList(iterations, Color.GREEN, Color.YELLOW);
        case RedYellowGreen:
            return new CycleList(iterations, Color.RED, Color.GREEN, Color.YELLOW);

        default:
            return null;
        }
    }
}
