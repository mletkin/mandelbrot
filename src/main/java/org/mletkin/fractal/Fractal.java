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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.mletkin.fractal.color.Blue;
import org.mletkin.fractal.color.ColorMapper;
import org.mletkin.fractal.front.Button;
import org.mletkin.fractal.front.ColorizePanel;
import org.mletkin.fractal.front.UpDownPanel;

public class Fractal extends JPanel {

    private static final Color X_HAIR_COLOR = Color.GREEN;

    private Mandelbrot fkt = new Mandelbrot();
    private Consumer<Stream<String>> status = s -> {};

    private UpDownPanel iterationPanel = new UpDownPanel(this::incIterations);
    private JSpinner scaleSpinner = new JSpinner(new SpinnerNumberModel(1.1, 0, 5, 0.01));
    private ColorMapper cm = new Blue(fkt.getIterations());

    private int xMax = getWidth();
    private int yMax = getHeight();

    private long x0 = xMax / 2;
    private long y0 = yMax / 2;

    private double delta = 1 / 500.0;

    private boolean xhair = true;

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
                Fractal.this.rescale(e.getPreciseWheelRotation() > 0, //
                        (Double) Fractal.this.scaleSpinner.getValue());
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

    /**
     * Draw a single point.
     */
    private void draw(Graphics g, int x, int y) {
        g.setColor(cm.toColor(fkt.speed(scaleX(x), scaleY(y))));
        g.drawRect(x, y, 1, 1);
    }

    private double scaleX(long x) {
        return (x - x0) * delta;
    }

    private double scaleY(long y) {
        return (y0 - y) * delta;
    }

    public Panel buttonPanel() {
        Panel panel = new Panel();
        panel.add(new ColorizePanel(fkt::getIterations, this::setColorizer));
        panel.add(iterationPanel);
        panel.add(new Button("reset", this::reset));
        panel.add(new Button("x-hair", this::toggleXhair));
        panel.add(scaleSpinner);
        return panel;
    }

    private static String pt(double x, double y) {
        return String.format("( %5.2f, %5.2f )", x, y);
    }

    private void status() {
        status.accept(Stream.of( //
                "iterations " + fkt.getIterations(), //
                "threshold " + fkt.getThreshold(), //
                "delta: 1/" + 1 / delta, //
                "top left " + pt(scaleX(0), scaleY(0)), //
                "center " + pt(scaleX(xMax / 2), scaleY(yMax / 2)), //
                "x0/y0 " + pt(x0, y0) + "->" + pt(scaleX(x0), scaleY(y0)) //
        ));
    }

    public void rescale(boolean in, double scalingFactor) {
        if (in) {
            delta *= scalingFactor;
            x0 = (long) ((x0 + xMax / 2 * (scalingFactor - 1)) / scalingFactor);
            y0 = (long) ((y0 + yMax / 2 * (scalingFactor - 1)) / scalingFactor);
        } else {
            delta /= scalingFactor;
            x0 = (long) (scalingFactor * x0 - xMax / 2 * (scalingFactor - 1));
            y0 = (long) (scalingFactor * y0 - yMax / 2 * (scalingFactor - 1));
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

    public void incIterations(int n) {
        fkt.setIterations(fkt.getIterations() + n);
        cm.setIterations(fkt.getIterations());
        repaint();
    }

    private void setColorizer(ColorMapper mapper) {
        this.cm = mapper;
        repaint();
    }

}
