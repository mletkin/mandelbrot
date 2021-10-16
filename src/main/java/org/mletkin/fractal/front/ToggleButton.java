package org.mletkin.fractal.front;

import javax.swing.JButton;

/**
 * Button with two states, starting in "off" state.
 */
public class ToggleButton extends JButton {

    boolean on = false;

    public ToggleButton(String textOn, Action actionOn, String textOff, Action actionOff) {
        setText(textOn);
        addActionListener(e -> {
            if (ToggleButton.this.on) {
                setText(textOn);
                actionOff.act();
            } else {
                setText(textOff);
                actionOn.act();
            }
            toggleState();
        });
    }

    private void toggleState() {
        on = !on;
    }
}
