package src.controller;

import src.util.Difficulty;
import src.view.MainFrame;
import src.view.MarathonMenu;
import src.view.StartMenu;

public class MainControl {
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

    public void loadMarathonGame(Difficulty diff, String mapName) {
        MarathonControl gameControl = new MarathonControl(this, diff, mapName);
        mainFrame.loadMenu(gameControl.panel);
    }

    public void closeWindow() {
        mainFrame.dispose();
    }

    public static void main(String[] args) {
        new MainControl();
    }
}
