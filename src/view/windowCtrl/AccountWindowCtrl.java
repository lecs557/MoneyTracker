package view.windowCtrl;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.Main;
import model.Transaction;
import view.customized_Panes.TransactionTable;

import java.time.LocalDate;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public Pane pane_table;

    public void initialize() {
        lbl_account.setText(Main.currentAccount.getName());
        pane_table.getChildren().clear();
        pane_table.getChildren().add(new TransactionTable());
    }

    public void back(){
        Main.windowManager.openWindpw(Main.windows.Start);
    }

    public void newTransaction() {
        Main.windowManager.showNewTransactionStage();
    }
}
