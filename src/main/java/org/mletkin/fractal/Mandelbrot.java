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
            double xn = x * x - y * y + cx;
            double yn = 2.0 * x * y + cy;
            if (Math.sqrt(x * x + y * y) > threshold) {
                return n;
            }
            x = xn;
            y = yn;
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
