package org.mletkin.fractal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {

    interface Action {
        void act();
    }

    Button(String text, Action action) {
        setText(text);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                action.act();
            }
        });
    }
}
