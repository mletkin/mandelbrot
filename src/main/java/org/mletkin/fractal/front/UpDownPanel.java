package org.mletkin.fractal.front;

import java.util.function.Consumer;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * A Spinner to in/decrement a value via a {@code consumer}.
 *
 * TODO: the spinner should have a title
 *
 */
public class UpDownPanel extends JPanel {

    private JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));

    /**
     * Create a spinner.
     * <p>
     *
     * @param action
     *                   {@code Consumer} to be called on button click.
     */
    public UpDownPanel(Consumer<Integer> action) {
        add(spinner);
        add(new Button("+", () -> action.accept((Integer) spinner.getValue())));
        add(new Button("-", () -> action.accept(-(Integer) spinner.getValue())));
    }

}
