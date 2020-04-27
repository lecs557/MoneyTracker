package view.windowCtrl;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import model.Main;
import model.Transaction;
import view.customized_Panes.TransactionChart;
import view.customized_Panes.TransactionTable;

import java.time.LocalDate;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public TabPane tabPane;
    public Tab diagramm_tab;


    public void initialize() {
        lbl_account.setText(Main.currentAccount.getName());

        diagramm_tab.setContent(new TransactionChart());

        tabPane.getTabs().add(new Tab("Werte", new TransactionTable()));
    }

    public void back(){
        Main.windowManager.openWindpw(Main.windows.Start);
    }

    public void newTransaction() {
        Main.windowManager.showNewTransactionStage();
    }
}
