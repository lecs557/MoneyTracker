package view.panes.entry_panes;

import javafx.scene.control.TextField;
import view.panes.ContentRegion;

public class AmountEntryWindowCtrl extends ContentRegion {

    public TextField tf_money;
    public TextField tf_cents;

    public void initialize() {
        tf_cents.setText("00");
    }

    @Override
    public String getContent() {
        return tf_money.getText()+tf_cents.getText();
    }

    @Override
    public void setContent(String content) {
        int length = content.length();
        tf_money.setText(content.substring(0, length-1));
        tf_cents.setText(content.substring(length-2, length));
    }
}
