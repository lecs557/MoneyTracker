package view.panes;

import javafx.scene.control.TextField;

public class AmountEntryWindowCtrl {
    public TextField tf_money;
    public TextField tf_cents;

    public void initialize() {
        tf_cents.setText("00");

    }
}
