package view.windowCtrl;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Main;
import model.Transaction;
import model.threads.PDFLoader;
import model.threads.Saver;
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
    public ProgressBar pb_pdf, pb_year, pb_transa;
    public Pane savePane;

    private Saver save;
    private PDFLoader pdfLoad;

    public void initialize() {
        Main.currentAccount.tabPane = tabPane;
        Main.currentAccount.diagrammContainer = diagrammContainer;
        Main.currentAccount.sumContainer = sumContainer;
        Main.currentAccount.btn_save = btn_save;
        btn_save.setDisable(true);
        lbl_account.setText(Main.currentAccount.getName());

        new TransactionChart().putInto(diagrammContainer);
        tabPane.getTabs().clear();
        for(ObservableList<Transaction> year: Main.currentAccount.getYears_Transaction()){
            tabPane.getTabs().add(new Tab(year.get(0).getDate().getYear()+"", new TransactionTable(year)));
        }
        new SumTable().putInto(sumContainer);

        Main.ioController.saveRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                save = Main.ioController.getSave();
                pb_year.progressProperty().bind(save.progressYearProperty());
                pb_transa.progressProperty().bind(save.progressTransactionProperty());
                savePane.setVisible(true);
            }else {
                save=null;
                savePane.setVisible(false);
            }
        });

        Main.ioController.pdfLoadRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                pdfLoad = Main.ioController.getPdfLoad();
                pb_pdf.progressProperty().bind(pdfLoad.progressProperty());
                savePane.setVisible(true);
            }else {
                save=null;
                savePane.setVisible(false);
            }
        });
    }

    public void back(){
        Main.windowManager.openWindow(Main.windows.Start);
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
        List<File> files = chooser.showOpenMultipleDialog(Main.stage);
        Main.ioController.pdfLoad(files);
    }

    public void renameReason(){
        Main.windowManager.showRename();
    }
}
