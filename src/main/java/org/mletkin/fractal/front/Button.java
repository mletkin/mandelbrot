package org.mletkin.fractal.front;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {

    public interface Action {
        void act();
    }

    public Button(String text, Action action) {
        setText(text);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                action.act();
            }
        });
    }
}
