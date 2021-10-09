package org.mletkin.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.swing.JPanel;

public class Fractal extends JPanel {

    private static final Color X_HAIR_COLOR = Color.GREEN;

    private Mandelbrot fkt = new Mandelbrot();
    private Consumer<Stream<String>> status = s -> {};

    private int xMax = getWidth(); // 1500;
    private int yMax = getHeight(); // 1000;

    private long x0 = xMax / 2;
    private long y0 = yMax / 2;

    private double scalingFactor = 1.5;
    private double delta = 1 / 500.0;

    private boolean xhair = true;
    private int light = 0;

    public Fractal(Consumer<Stream<String>> status) {
        this.status = status;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Fractal.this.recenter(e.getX(), e.getY());
            }
        });

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                Fractal.this.rescale(e.getPreciseWheelRotation() > 0);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Fractal.this.xMax = e.getComponent().getWidth();
                Fractal.this.yMax = e.getComponent().getHeight();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        for (int x = 0; x < xMax; x += 2) {
            for (int y = 0; y < yMax; y += 2) {
                draw(g, x, y);
            }
        }
        if (xhair) {
            fadenkreuz(g);
        }
        status();
    }

    private void fadenkreuz(Graphics g) {
        g.setColor(X_HAIR_COLOR);
        g.drawLine(xMax / 2, 0, xMax / 2, yMax);
        g.drawLine(0, yMax / 2, xMax, yMax / 2);
    }

    private void draw(Graphics g, int x, int y) {
        g.setColor(toColor(fkt.speed(scaleX(x), scaleY(y))));
        g.drawRect(x, y, 1, 1);
    }

    private Color toColor(int woop) {
        woop *= 256 / Math.min(fkt.getIterations(), 256);
        int blue = woop % 256;
        int green = (woop / 256) % 256;
        int red = (woop / 256 / 256) % 256;

        return new Color(red, green, blue);
    }

    private double scaleX(long x) {
        return (x - x0) * delta;
    }

    private double scaleY(long y) {
        return (y0 - y) * delta;
    }

    public Panel buttonPanel() {
        Panel panel = new Panel();
        panel.add(new Button("reset", this::reset));
        panel.add(new Button("x-hair", this::toggleXhair));
        panel.add(new Button("it +5", () -> incIterations(5)));
        panel.add(new Button("it -5", () -> incIterations(-5)));
        panel.add(new Button("light +5", () -> inclight(5)));
        panel.add(new Button("light -5", () -> inclight(-5)));
        return panel;
    }

    private static String pt(double x, double y) {
        return String.format("( %5.2f, %5.2f )", x, y);
    }

    private void status() {
        status.accept(Stream.of( //
                "light " + light, //
                "iterations " + fkt.getIterations(), //
                "threshold " + fkt.getThreshold(), //
                "delta: 1/" + 1 / delta, //
                "top left " + pt(scaleX(0), scaleY(0)), //
                "center " + pt(scaleX(xMax / 2), scaleY(yMax / 2)), //
                "x0/y0 " + pt(x0, y0) + "->" + pt(scaleX(x0), scaleY(y0)) //
        ));
    }

    void rescale(boolean in) {
        if (in) {
            delta *= scalingFactor;
            x0 = (long) ((x0 + xMax / 4) / scalingFactor);
            y0 = (long) ((y0 + yMax / 4) / scalingFactor);
        } else {
            delta /= scalingFactor;
            x0 = (long) (scalingFactor * x0 - xMax / 4);
            y0 = (long) (scalingFactor * y0 - yMax / 4);
        }
        repaint();
    }

    void recenter(int newX, int newY) {
        x0 -= newX - xMax / 2;
        y0 -= newY - yMax / 2;
        repaint();
    }

    public void reset() {
        xMax = getWidth();
        yMax = getHeight();
        x0 = xMax / 2;
        y0 = yMax / 2;
        delta = 1 / (Math.min(xMax, yMax) / 2.0);
        repaint();
    }

    private void toggleXhair() {
        xhair = !xhair;
        repaint();
    }

    private void incIterations(int n) {
        fkt.setIterations(fkt.getIterations() + n);
        repaint();
    }

    private void inclight(int n) {
        light += n;
        repaint();
    }

}
