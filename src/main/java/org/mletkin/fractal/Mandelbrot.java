package org.mletkin.fractal;

/**
 * Calculator for the mandelbrot set.
 */
public class Mandelbrot {

    private int iterations = 50;
    private double threshold = 2;

    /**
     * Speed of non converging :-)
     */
    public int speed(double cx, double cy) {
        double x = 0;
        double y = 0;
        for (int n = 0; n < iterations; n++) {
            double xSquare = x * x;
            double ySquare = y * y;
            if (Math.sqrt(xSquare + ySquare) > threshold) {
                return n;
            }
            y = 2.0 * x * y + cy;
            x = xSquare - ySquare + cx;
        }
        return 0;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
}
