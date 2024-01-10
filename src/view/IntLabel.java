package src.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.function.IntSupplier;

import javax.swing.JLabel;

/**
 * A modified type of <code>JLabel</code> with a background color that
 * displays an integer information.
 */
public class IntLabel extends JLabel {
    private final String text;
    private final IntSupplier getter;

    /**
     * Creates a new <code>IntLabel</code> with the specified background color
     * that displays the specified text followed by the int property.
     *
     * @param color  the background color of this label
     * @param text   a fixed text displayed before the int
     * @param getter the function to get the int property
     */
    public IntLabel(Color color, String text, IntSupplier getter) {
        this.text = text;
        this.getter = getter;

        setText(text + getter.getAsInt());
        setFont(Palette.PLAIN_SANS);
        setBackground(color);
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setText(text + getter.getAsInt());
    }
}
