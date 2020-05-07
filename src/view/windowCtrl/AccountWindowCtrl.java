package view.windowCtrl;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import model.Main;
import model.Transaction;
import view.customized_Panes.SumTable;
import view.customized_Panes.TransactionChart;
import view.customized_Panes.TransactionTable;

import java.io.File;
import java.io.IOException;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public Button btn_save;

    public void initialize() {
        Main.currentAccount.tabPane = tabPane;
        Main.currentAccount.diagrammContainer = diagrammContainer;
        Main.currentAccount.sumContainer = sumContainer;
        Main.currentAccount.btn_save = btn_save;
        btn_save.setDisable(true);
        lbl_account.setText(Main.currentAccount.getName());

        new TransactionChart().putInto(diagrammContainer);
        new SumTable().putInto(sumContainer);
        tabPane.getTabs().clear();
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

    public void save() throws IOException {
        if (Main.currentAccount.getPath() == null) {
           setPath();
        }
        Main.ioController.save();
        btn_save.setDisable(true);
    }

    public void saveUnder() throws IOException {
        setPath();
        Main.ioController.save();
        btn_save.setDisable(true);
    }

    public void setPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(Main.stage);
        Main.currentAccount.setPath(file.getAbsolutePath());
    }

    public void readPDF(){

    }
}
