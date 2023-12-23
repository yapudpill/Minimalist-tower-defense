package src.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * The main <code>JFrame</code> object of this project, where everything is
 * displayed.
 */
public class MainFrame extends JFrame {

    /**
     * Creates a new <code>MainFrame</code>, with size to 900x900 that uses the
     * default look and feel. Then make it visible.
     */
    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        setSize(900,900);
        setTitle("Tower Defence");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        EventQueue.invokeLater(() -> setVisible(true));
    }

    /**
     * Swap the <code>contentPane</code> of this frame to the specified menu.
     *
     * @param menu - The menu that will be displayed
     */
    public void loadMenu(JPanel menu) {
        EventQueue.invokeLater(() -> {
            setContentPane(menu);
            validate();
        });
    }
}
