package src.controller;

import src.util.Difficulty;
import src.view.MainFrame;
import src.view.MarathonMenu;
import src.view.StartMenu;

/**
 * Main controller of the project. This controller is in change of creating the
 * main frame and swapping menus.
 */
public class MainControl {
    private final MainFrame mainFrame;

    /**
     * Creates a new main controller for this project.
     *
     * <p> Internally, it creates a new <code>MainFrame</code> and load a new
     * <code>StartMenu</code> in it.
     */
    public MainControl() {
        mainFrame = new MainFrame();
        loadStartMenu();
    }

    /**
     * Loads a new <code>StartMenu</code> in the <code>mainFrame</code> of this
     * controller.
     */
    public void loadStartMenu() {
        mainFrame.loadMenu(new StartMenu(this));
    }

    /**
     * Loads a new <code>MarathonMenu</code> in the <code>mainFrame</code> of this
     * controller.
     */
    public void loadMarathonMenu() {
        mainFrame.loadMenu(new MarathonMenu(this));
    }

    /**
     * Creates a new <code>MarathonControl</code> object and load its
     * <code>view</code> in the <code>mainFrame</code> of this controller.
     *
     * @param diff     the difficulty of the game being loaded
     * @param mapName  the name of the file in which the map to load is saved
     */
    public void loadMarathonGame(Difficulty diff, String mapName) {
        MarathonControl gameControl = new MarathonControl(this, diff, mapName);
        mainFrame.loadMenu(gameControl.view);
        gameControl.startGame();
    }

    /**
     * Loads a new <code>MarathonEnd</code> in the <code>mainFrame</code> of this
     * controller.
     */
    public void loadMarathonEnd() {

    }

    /**
     * Closes the main window of this controller.
     */
    public void closeWindow() {
        mainFrame.dispose();
    }

    /**
     * Entry point of this project.
     */
    public static void main(String[] args) {
        new MainControl();
    }
}
