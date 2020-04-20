package view.windowCtrl;

import javafx.scene.control.Label;
import model.Main;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;

    public void initialize() {
        lbl_account.setText(Main.getCurrentAccount().getName());
    }

    public void back(){
        Main.windowManager.openContent(Main.windows.Start);
    }
}
