package view.windows;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Main;
import model.Transaction;
import model.threads.PDFLoader;
import model.threads.Renamer;
import model.threads.Saver;
import view.simple_panes.SumTable;
import view.simple_panes.TransactionChart;
import view.simple_panes.TransactionTable;
import view.simple_panes.ViewUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public ProgressBar pb_pdf, pb_year, pb_transa,pb_year1, pb_transa1;
    public Pane savePane;
    public Pane chPane;
    public Pane pdfPane;

    private Saver saver;
    private PDFLoader pdfLoad;
    private Renamer renamer;

    public void initialize() {
        // ** Ansicht initialisieren ***
        Main.currentAccount.tabPane = tabPane;
        Main.currentAccount.diagrammContainer = diagrammContainer;
        Main.currentAccount.sumContainer = sumContainer;
        Main.currentAccount.mi_save = mi_save;
        mi_save.setDisable(true);
        lbl_account.setText(Main.currentAccount.getName());
        new TransactionChart().putInto(diagrammContainer);
        tabPane.getTabs().clear();
        for(ArrayList<Transaction> year: Main.currentAccount.getYears_Transaction()){
            tabPane.getTabs().add(new Tab(year.get(0).getDate().getYear()+"", new TransactionTable(year)));
        }
        new SumTable().putInto(sumContainer);
        // ******
        // *** Threads tracken ***
        Main.editController.renameRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                renamer = Main.editController.getRenamer();
                pb_year1.progressProperty().bind(renamer.progressYearProperty());
                pb_transa1.progressProperty().bind(renamer.progressTransactionProperty());
                chPane.setVisible(true);
            }else {
                renamer =null;
                chPane.setVisible(false);
            }
        });
        Main.ioController.saveRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                saver = Main.ioController.getSaver();
                pb_year.progressProperty().bind(saver.progressYearProperty());
                pb_transa.progressProperty().bind(saver.progressTransactionProperty());
                savePane.setVisible(true);
            }else {
                saver =null;
                savePane.setVisible(false);
            }
        });
        Main.ioController.pdfLoadRunningProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1){
                pdfLoad = Main.ioController.getPdfLoader();
                pb_pdf.progressProperty().bind(pdfLoad.progressProperty());
                pdfPane.setVisible(true);
            }else {
                pdfLoad =null;
                pdfPane.setVisible(false);
            }
        });
        // ******
    }



    public void back(){
        Main.windowManager.changeSceneTo(Main.windows.Start);
    }

    public void newTransaction() {
        Main.windowManager.openStageOf(Main.windows.NewTransaction);
    }

    public void save() {
        if (Main.currentAccount.getPath() == null) {
           ViewUtils.setPath();
        }
        Main.ioController.startSave();
        mi_save.setDisable(true);
    }

    public void saveUnder() {
        ViewUtils.setPath();
        Main.ioController.startSave();
        mi_save.setDisable(true);
    }


    public void readPDF()  {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("PDF auswählen");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDFFiles", "*.pdf"));
        List<File> files = chooser.showOpenMultipleDialog(Main.primaryStage);
        Main.ioController.startPDFLoad(files);
    }

    public void renameReason(){
        Main.windowManager.openStageOf(Main.windows.RenameWindow);
        mi_save.setDisable(false);
    }
}
