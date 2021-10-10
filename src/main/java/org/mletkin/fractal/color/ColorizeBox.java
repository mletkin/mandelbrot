package org.mletkin.fractal.color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JComboBox;

/**
 * Combobox for choosing a colorizer.
 */
public class ColorizeBox extends JComboBox<ColorMapperFactory.Cm> {

    public ColorizeBox(Consumer<ColorMapperFactory.Cm> action) {
        super(ColorMapperFactory.Cm.values());
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox) e.getSource();
                ColorMapperFactory.Cm mapper = (ColorMapperFactory.Cm) cb.getSelectedItem();
                action.accept(mapper);
            }
        });
    }

}
