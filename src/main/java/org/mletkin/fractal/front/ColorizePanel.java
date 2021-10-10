package org.mletkin.fractal.front;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JPanel;

import org.mletkin.fractal.color.ColorMapper;
import org.mletkin.fractal.color.ColorMapperFactory;
import org.mletkin.fractal.color.ColorizeBox;

public class ColorizePanel extends JPanel {

    private ColorizeBox cBox = new ColorizeBox();
    private Checkbox scaleColour = new Checkbox("scale");

    private Supplier<Integer> getIterations;

    public ColorizePanel(Supplier<Integer> getIterations, Consumer<ColorMapper> setMapper) {
        this.getIterations = getIterations;

        add(cBox);
        add(scaleColour);
        cBox.addActionListener(e -> setMapper.accept(makeMapper()));
        scaleColour.addActionListener(e -> setMapper.accept(makeMapper()));
    }

    private ColorMapper makeMapper() {
        return ColorMapperFactory.make(cBox.choice(), getIterations.get(), scaleColour.isSelected());
    }
}
