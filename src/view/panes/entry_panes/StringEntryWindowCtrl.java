package view.panes.entry_panes;

import javafx.scene.control.TextField;
import model.Main;
import model.storeclasses.Transaction;
import view.panes.contentable;

import java.time.LocalDate;

public class StringEntryWindowCtrl implements contentable {

    public TextField tf_purpose;

    public void initialize() {

    }

    @Override
    public String getContent() {
        return tf_purpose.getText();
    }
}
