package view.panes.entry_panes;

import javafx.scene.control.TextField;
import view.panes.contentable;

public class AmountEntryWindowCtrl implements contentable {
    public TextField tf_money;
    public TextField tf_cents;

    public void initialize() {
        tf_cents.setText("00");
    }

    @Override
    public String getContent() {
        return tf_money.getText()+tf_cents.getText();
    }
}
