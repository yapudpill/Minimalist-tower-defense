package src.view;

import javax.swing.JPanel;

import src.controller.MainControl;
import src.controller.MarathonControl;
import src.model.MarathonModel;

public class MarathonView extends JPanel {

    public MarathonView(MarathonModel model, MainControl mainControl, MarathonControl gameControl) {
        add(new GridView(model.grid));
    }
}
