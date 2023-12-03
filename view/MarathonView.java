package view;

import javax.swing.JPanel;

import controller.MainControl;
import controller.MarathonControl;
import model.MarathonModel;

public class MarathonView extends JPanel {

    public MarathonView(MarathonModel model, MainControl mainControl, MarathonControl gameControl) {
        add(new GridView(model.grid));
    }
}
