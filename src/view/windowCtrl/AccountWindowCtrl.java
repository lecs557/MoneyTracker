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
        Main.windowManager.openContent(Main.windows.Start);
    }

    public void newTransaction() {
        Main.currentAccount.addTransaction(new Transaction(LocalDate.of(2020,1,1) ,"test",1000));
        Main.currentAccount.addTransaction(new Transaction(LocalDate.of(2020,1,10) ,"test",100));
        Main.currentAccount.addTransaction(new Transaction(LocalDate.of(2020,1,7) ,"test",500));
    }
}
