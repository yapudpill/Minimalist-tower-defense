package src.view;

import java.awt.Color;
import java.awt.Font;

/**
 * The colors used in this project.
 */
public class Palette {

    // Cell colors
    public static final Color BACKGROUND = new Color(0x171717);
    public static final Color TOWER_CELL = new Color(0x3f3f3f);
    public static final Color PATH = new Color(0x898989);
    public static final Color ARROW = Color.WHITE;

    // Enemy colors
    public static final Color BASIC_ENEMY = new Color(0x4bae4f);
    public static final Color TANK_ENEMY = new Color(0xff1700);
    public static final Color FAST_ENEMY = new Color(0xfdca00);

    // Tower colors
    public static final Color BASIC_TOWER_OUTER = new Color(0x00897b);
    public static final Color BASIC_TOWER_INNER = new Color(0x4db6ac);
    public static final Color CANON_TOWER_OUTER = new Color(0xe03834);
    public static final Color CANON_TOWER_INNER = new Color(0xe27171);
    public static final Color SNIPER_TOWER_OUTER = new Color(0x44a048);
    public static final Color SNIPER_TOWER_INNER = new Color(0x7ec381);

    // Other colors
    public static final Color LIFE = new Color(0xff5d5d);
    public static final Color GOLD = new Color(0xffd100);
    public static final Color WAVE = new Color(0x87e0ff);

    // Fonts
    public static final Font PLAIN_SANS = new Font(Font.SANS_SERIF, Font.PLAIN, 23);
    public static final Font PLAIN_SANS_BIG = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
}
