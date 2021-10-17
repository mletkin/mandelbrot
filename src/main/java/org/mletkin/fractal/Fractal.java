package org.mletkin.fractal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
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

    private long x0 = xMitte();
    private long y0 = yMitte();

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

    }

    @Override
    public void paint(Graphics g) {
        drawImage(g, getWidth(), getHeight());
        if (xhair) {
            fadenkreuz(g);
        }
        status();
    }

    private void drawImage(Graphics g, int xMax, int yMax) {
        for (int x = 0; x < xMax; x += 2) {
            for (int y = 0; y < yMax; y += 2) {
                draw(g, x, y);
            }
        }
    }

    private void fadenkreuz(Graphics g) {
        g.setColor(X_HAIR_COLOR);
        g.drawLine(xMitte(), 0, xMitte(), yMitte() * 2);
        g.drawLine(0, yMitte(), xMitte() * 2, yMitte());
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
        panel.add(new Button("save", this::savePng));
        return panel;
    }

    private void status() {
        status.accept(Stream.of( //
                "iterations " + fkt.getIterations(), //
                "threshold " + fkt.getThreshold(), //
                "delta: " + delta, //
                "top left " + Util.pt(scaleX(0), scaleY(0)), //
                "center " + Util.pt(scaleX(xMitte()), scaleY(yMitte())), //
                "x0/y0 " + Util.pt(x0, y0) + "->" + Util.pt(scaleX(x0), scaleY(y0)) //
        ));
    }

    public void rescale(boolean in, double scalingFactor) {
        if (in) {
            delta *= scalingFactor;
            x0 = (long) ((x0 + xMitte() * (scalingFactor - 1)) / scalingFactor);
            y0 = (long) ((y0 + yMitte() * (scalingFactor - 1)) / scalingFactor);
        } else {
            delta /= scalingFactor;
            x0 = (long) (scalingFactor * x0 - xMitte() * (scalingFactor - 1));
            y0 = (long) (scalingFactor * y0 - yMitte() * (scalingFactor - 1));
        }
        repaint();
    }

    private int yMitte() {
        return getHeight() / 2;
    }

    private int xMitte() {
        return getWidth() / 2;
    }

    void recenter(int newX, int newY) {
        x0 -= newX - xMitte();
        y0 -= newY - yMitte();
        repaint();
    }

    public void reset() {
        x0 = xMitte();
        y0 = yMitte();
        delta = 1.0 / (Math.min(xMitte(), yMitte()));
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

    private void savePng() {
        Util.savePngFile(this, file -> {
            try {
                ImageIO.write(createPicture(), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private BufferedImage createPicture() {
        BufferedImage bufferedImage = new BufferedImage(xMitte() * 2, yMitte() * 2, BufferedImage.TYPE_INT_RGB);
        drawImage(bufferedImage.getGraphics(), xMitte() * 2, yMitte() * 2);
        return bufferedImage;
    }
}
