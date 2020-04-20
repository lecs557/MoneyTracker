package view.windowCtrl;

import javafx.scene.control.Label;
import model.Main;

public class AccountWindowCtrl {

    public Label lbl_account;

    public void initialize() {
        lbl_account.setText(Main.getCurrentAccount().getName());
    }
}
