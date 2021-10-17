package org.mletkin.fractal;

import java.awt.Component;
import java.io.File;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * What ever may be used anywhere.
 */
public final class Util {

    private Util() {
        // prevent instantiation
    }

    public static void savePngFile(Component owner, Consumer<File> saveAction) {
        JFileChooser jfc = pngFileSaver();
        if (jfc.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
            File outputfile = new File(jfc.getSelectedFile().getAbsolutePath());
            saveAction.accept(outputfile);
        }
    }

    private static JFileChooser pngFileSaver() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save picture as .png file");
        jfc.setAcceptAllFileFilterUsed(false);
        FileFilter filter = new FileNameExtensionFilter(".png files", "png");
        jfc.addChoosableFileFilter(filter);
        return jfc;
    }

    public static String pt(double x, double y) {
        return String.format("( %5.2f, %5.2f )", x, y);
    }
}
