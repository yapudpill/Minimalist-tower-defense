package controller;

import static util.Status.EXIT;
import static util.Status.VICTORY;

import model.GameStats;
import model.GlobalModel;
import util.Difficulty;
import util.Status;
import view.MainFrame;
import view.menus.LevelEnd;
import view.menus.LevelMenu;
import view.menus.MarathonEnd;
import view.menus.MarathonMenu;
import view.menus.ScoreMenu;
import view.menus.StartMenu;

/**
 * Main controller of the project. This controller is in change of creating the
 * main frame and swapping menus.
 */
public class MainControl {
    private final GlobalModel globalModel;
    private final MainFrame mainFrame;

    /**
     * Creates a new main controller for this project.
     *
     * <p> Internally, it creates a new <code>MainFrame</code> and load a new
     * <code>StartMenu</code> in it.
     */
    public MainControl() {
        globalModel = new GlobalModel();
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

    public void loadScoresMenu() {
        mainFrame.loadMenu(new ScoreMenu(this, globalModel.stats));
    }

    /**
     * Loads a new <code>LevelMenu</code> in the <code>mainFrame</code> of this
     * controller.
     */
    public void loadLevelMenu() {
        mainFrame.loadMenu(new LevelMenu(this, globalModel));
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
     * Creates a new <code>LevelControl</code> object and load its
     * <code>view</code> in the <code>mainFrame</code> of this controller.
     *
     * @param int     the level of the game being loaded
     * @param mapName the name of the file in which the map to load is saved
     */
    public void loadLevelGame(int level, String mapName) {
        LevelControl gameControl = new LevelControl(this, level, mapName);
        mainFrame.loadMenu(gameControl.view);
        gameControl.startGame();
    }

    /**
     * Loads a new <code>MarathonEnd</code> in the <code>mainFrame</code> of this
     * controller.
     *
     * @param status the status at the end of the game
     * @param stats  the game statistics
     */
    public void loadMarathonEnd(Status status, GameStats stats, Difficulty diff) {
        if (status == EXIT) {
            loadMarathonMenu();
        } else {
            switch (diff) {
                case EASY:   globalModel.stats[0].add(stats); break;
                case MEDIUM: globalModel.stats[1].add(stats); break;
                case HARD:   globalModel.stats[2].add(stats); break;
            }
            mainFrame.loadMenu(new MarathonEnd(this, stats));
        }
    }

    /**
     * Loads a new <code>LevelEnd</code> in the <code>mainFrame</code> of this
     * controller.
     *
     * @param status  the status at the end of the game
     * @param stats   the game statistics
     * @param level   the level that was playing
     * @param nbWaves the total number of waves of the game
     * @param stars   the stars to earn un case of a win
     */
    public void loadLevelEnd(Status status, GameStats stats, int level, int nbWaves, int stars) {
        switch (status) {
            case PLAYING: break;
            case EXIT: loadLevelMenu(); break;

            case VICTORY:
                if (level < globalModel.levelCompletion.size()) {
                    globalModel.levelCompletion.set(level, stars);
                } else {
                    globalModel.levelCompletion.add(stars);
                }
            case DEFEAT:
                mainFrame.loadMenu(new LevelEnd(this, stats, nbWaves, stars, status == VICTORY));
                break;
        }
    }

    /**
     * Closes the main window of this controller.
     */
    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
    }

    /**
     * Entry point of this project.
     */
    public static void main(String[] args) {
        new MainControl();
    }
}
