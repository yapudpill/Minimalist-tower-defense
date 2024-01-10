package src.controller;

import static src.util.Status.EXIT;
import static src.util.Status.VICTORY;

import src.model.GameStats;
import src.model.GlobalInfos;
import src.util.Difficulty;
import src.util.Status;
import src.view.LvlEnd;
import src.view.LvlMenu;
import src.view.MainFrame;
import src.view.MarathonEnd;
import src.view.MarathonMenu;
import src.view.StartMenu;

/**
 * Main controller of the project. This controller is in change of creating the
 * main frame and swapping menus.
 */
public class MainControl {
    private final GlobalInfos infos;
    private final MainFrame mainFrame;

    /**
     * Creates a new main controller for this project.
     *
     * <p> Internally, it creates a new <code>MainFrame</code> and load a new
     * <code>StartMenu</code> in it.
     */
    public MainControl() {
        infos = new GlobalInfos();
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
     * Closes the main window of this controller.
     */
    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
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
    public void loadMarathonEnd(Status status, GameStats stats) {
        if (status == EXIT) {
            loadMarathonMenu();
        } else {
            mainFrame.loadMenu(new MarathonEnd(this, stats));
        }
    }

    public void loadLvlMenu() {
        mainFrame.loadMenu(new LvlMenu(this, infos));
    }

    public void loadLvlGame(int lvl, String mapName) {
        LvlControl gameControl = new LvlControl(this, lvl, mapName);
        mainFrame.loadMenu(gameControl.view);
        gameControl.startGame();
    }

    public void loadLvlEnd(Status status, GameStats stats, int lvl, int nbWaves, int stars) {
        switch (status) {
            case PLAYING: break;
            case EXIT: loadLvlMenu(); break;

            case VICTORY:
                if (lvl < infos.lvlCompletion.size()) {
                    infos.lvlCompletion.set(lvl, stars);
                } else {
                    infos.lvlCompletion.add(stars);
                }
            case DEFEAT:
                mainFrame.loadMenu(new LvlEnd(this, stats, nbWaves, stars, status == VICTORY));
                break;
        }
    }

    /**
     * Entry point of this project.
     */
    public static void main(String[] args) {
        new MainControl();
    }
}
