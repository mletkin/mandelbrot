package org.mletkin.fractal;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.mletkin.fractal.front.Status;

public class Application {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1500, 1000);
        frame.setLayout(new BorderLayout());

        Status status = new Status();
        Fractal fractal = new Fractal(status::setText);
        AutoPanel auto = new AutoPanel((s, i) -> {
            fractal.incIterations(i);
            fractal.rescale(false, s);
        });

        JPanel east = new JPanel();
        east.setLayout(new BorderLayout());
        east.add(status, BorderLayout.NORTH);
        east.add(auto, BorderLayout.SOUTH);

        frame.add(fractal, BorderLayout.CENTER);
        frame.add(east, BorderLayout.EAST);
        frame.add(fractal.buttonPanel(), BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        fractal.reset();
    }

}
