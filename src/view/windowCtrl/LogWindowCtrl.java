package view.windowCtrl;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Main;

public class LogWindowCtrl {

    public ListView lv_logs;
    public Button btn_ok;

    public void initialize() {
        lv_logs.setItems(Main.logController.getLogs());
    }

    public void ok() {

    }
}
