package view.windows;

import controller.ProfileAccountManager;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.Main;
import model.threads.PDFImporter;
import model.threads.Renamer;
import model.threads.Saver;
import view.simple_panes.CreateNew;
import view.simple_panes.SumTable;
import view.simple_panes.TransactionChart;
import view.simple_panes.ViewUtils;

import java.io.File;
import java.util.List;

public class OverviewWindowCtrl extends BaseWindowCtrl{

    public Label lbl_account;
    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public ProgressBar pb_pdf, pb_year, pb_transa,pb_year1, pb_transa1;
    public Pane savePane;
    public Pane chPane;
    public Pane pdfPane;

    private Saver saver;
    private PDFImporter pdfLoad;
    private Renamer renamer;

    public void initialize() {

    }

    public void backToLogin(ActionEvent actionEvent) {
        ProfileAccountManager.setCurrentAccount(null);
        WindowManager.changeSceneTo(Main.windows.LogIn);
    }


    public void newBA(ActionEvent actionEvent) {
        CreateNew createNew = new CreateNew();
        createNew.setClassName("model.storeclasses.BankAccount");
        WindowManager.openStageOf(createNew);
    }
}
