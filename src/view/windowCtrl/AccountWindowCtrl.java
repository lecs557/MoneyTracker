package view.windowCtrl;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.Main;
import model.Transaction;
import view.customized_Panes.SumTable;
import view.customized_Panes.TransactionChart;
import view.customized_Panes.TransactionTable;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public TabPane tabPane;
    public Tab diagramm_tab;


    public void initialize() {
        lbl_account.setText(Main.currentAccount.getName());

        diagramm_tab.setContent(new TransactionChart());

        Main.currentAccount.diagramm_tab = diagramm_tab;
        Main.currentAccount.tabPane = tabPane;

        tabPane.getTabs().add(new Tab("Hallo",new SumTable()));
        for(ObservableList<Transaction> year: Main.currentAccount.getYears_Transaction()){
            tabPane.getTabs().add(new Tab(year.get(0).getDate().getYear()+"", new TransactionTable(year)));
        }
    }

    public void back(){
        Main.windowManager.openWindpw(Main.windows.Start);
    }

    public void newTransaction() {
        Main.windowManager.showNewTransactionStage();
    }
}
