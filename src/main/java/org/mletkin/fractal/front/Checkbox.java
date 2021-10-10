package org.mletkin.fractal.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JCheckBox;

public class Checkbox extends JCheckBox {

    public Checkbox(String text) {
        setText(text);
    }

    public Checkbox(String text, Consumer<Boolean> action) {
        this(text);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                action.accept(Checkbox.this.isSelected());
            }

        });
    }
}
