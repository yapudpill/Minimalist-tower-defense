package src.controller;

import java.io.File;

import src.util.Difficulty;
import src.view.MainFrame;
import src.view.MarathonMenu;
import src.view.StartMenu;

public class MainControl {
    public final File mapDir = new File("src/resources/maps");
    private final MainFrame mainFrame;

    public MainControl() {
        mainFrame = new MainFrame();
        loadStartMenu();
    }

    public void loadStartMenu() {
        mainFrame.loadMenu(new StartMenu(this));
    }

    public void loadMarathonMenu() {
        mainFrame.loadMenu(new MarathonMenu(this));
    }

    public void loadMarathonGame(Difficulty diff, File map) {
        MarathonControl gameControl = new MarathonControl(this, map, diff);
        mainFrame.loadMenu(gameControl.panel);
    }

    public void closeWindow() {
        mainFrame.dispose();
    }

    public static void main(String[] args) {
        new MainControl();
    }
}
