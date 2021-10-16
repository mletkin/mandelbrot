package org.mletkin.fractal;

import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.mletkin.fractal.front.ToggleButton;

/**
 * Panel controlling a timer for automatic painitng.
 */
public class AutoPanel extends JPanel {

    @FunctionalInterface
    interface PaintAction {
        void act(double factor, int iterateInc);
    }

    private JSpinner scaleSpinner = new JSpinner(new SpinnerNumberModel(1.1, 0, 5, 0.01));
    private JSpinner iterateSpinner = new JSpinner(new SpinnerNumberModel(1, -100, 100, 5));

    private Timer timer;
    private PaintAction action;

    public AutoPanel(PaintAction action) {
        this.setLayout(new GridLayout(4, 1)); // ;this, BoxLayout.Y_AXIS));
        this.action = action;

        this.add(new JLabel("automatic"));
        JPanel x = new JPanel();
        x.setLayout(new GridLayout(1, 2));
        x.add(new JLabel(" scale factor"));
        x.add(scaleSpinner);

        JPanel y = new JPanel();
        y.setLayout(new GridLayout(1, 2));
        y.add(new JLabel(" iteration inc."));
        y.add(iterateSpinner);

        add(x);
        add(y);
        add(new ToggleButton("start", this::start, "stop", this::stop));
    }

    private void start() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.schedule(task(action), 0, 1000);
    }

    private TimerTask task(PaintAction action2) {
        return new TimerTask() {
            @Override
            public void run() {
                action.act((Double) scaleSpinner.getValue(), (Integer) iterateSpinner.getValue());
            }
        };
    }

    private void stop() {
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
    }
}
