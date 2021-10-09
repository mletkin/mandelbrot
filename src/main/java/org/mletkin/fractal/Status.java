package org.mletkin.fractal;

import java.util.stream.Stream;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Status extends JPanel {

    private JTextArea text = new JTextArea();

    public Status() {
        add(text);
    }

    public void setText(Stream<String> list) {
        text.setText("");
        list.map(s -> s += "\n").forEach(text::append);
    }

    public void add(String s) {
        text.append(s + "\n");
    }

    public void clear() {
        text.setText("");
    }
}
