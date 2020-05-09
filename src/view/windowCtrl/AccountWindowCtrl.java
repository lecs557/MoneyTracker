package view.windowCtrl;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import model.Account;
import model.Main;
import model.Transaction;
import view.customized_Panes.SumTable;
import view.customized_Panes.TransactionChart;
import view.customized_Panes.TransactionTable;
import view.customized_Panes.ViewUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public Button btn_save;
    public ProgressBar pb_pdf;

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
           ViewUtils.setPath();
        }
        Main.ioController.save();
        btn_save.setDisable(true);
    }

    public void saveUnder() throws IOException {
        ViewUtils.setPath();
        Main.ioController.save();
        btn_save.setDisable(true);
    }

    public void readPDF()  {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("PDF auswählen");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDFFiles", "*.pdf"));
        List<File> temps = chooser.showOpenMultipleDialog(Main.stage);
        SimpleDoubleProperty progress = new SimpleDoubleProperty(0);
        pb_pdf.progressProperty().bind(progress);

        new Thread(() -> {
            int i=0;
            int size = temps.size();
            for(File temp:temps) {
                if (temp != null) {
                    String path = temp.getAbsolutePath();
                    try {
                        Main.pdfController.readPDF(path);
                    } catch (IOException e) {
                        System.out.println("Problem bei: "+path);
                        e.printStackTrace();
                    }
                }
                i++;
                progress.set((double)i/size);
                System.out.println(i);
                System.out.println(progress.get());
            }
            Main.currentAccount.reload();
        }).start();
    }

    public void renameReason(){
        Main.windowManager.showRename();
    }
}
