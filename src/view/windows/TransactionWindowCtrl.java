package view.windows;

import javafx.scene.control.TextField;
import model.Main;
import model.Transaction;

import java.time.format.DateTimeFormatter;

public class TransactionWindowCtrl {

    public TextField tf_date, tf_reason, tf_betrag;

    public void initialize() {
        Transaction edit = Main.editController.getEditTransaction();
        tf_date.setText(edit.getDate().format(DateTimeFormatter.ofPattern("dd LLLL yyyy")));
        tf_date.setEditable(false);
        tf_reason.setText(edit.getReason());
        tf_reason.setEditable(false);
        tf_betrag.setText(""+(double)edit.getBetrag()/100+" €");
        tf_betrag.setEditable(false);
    }

    public void ok(){

    }

    public void edit(){

    }
}

