package org.mletkin.fractal;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.mletkin.fractal.front.Status;

public class Application {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(0, 0, 1500, 1000);
        frame.setLayout(new BorderLayout());

        Status status = new Status();
        Fractal fractal = new Fractal(status::setText);

        frame.add(fractal, BorderLayout.CENTER);
        frame.add(status, BorderLayout.EAST);
        frame.add(fractal.buttonPanel(), BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        fractal.reset();
    }

}
