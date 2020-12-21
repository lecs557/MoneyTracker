package view.windows;

import controller.DatabaseController;
import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.Main;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.Profile;
import model.threads.PDFImporter;
import model.threads.Renamer;
import model.threads.Saver;
import view.simple_panes.CreateNew;

import java.util.ArrayList;

public class OverviewWindowCtrl extends BaseWindowCtrl{

    public ListView lv_groups;
    private Profile currentAccount;
    private BankAccount bankAccount;

    public Label lbl_account;
    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public ProgressBar pb_pdf, pb_year, pb_transa,pb_year1, pb_transa1;
    public Pane savePane;
    public Pane chPane;
    public Pane pdfPane;
    public ListView<BankAccount> lv_bankAccounts;

    private Saver saver;
    private PDFImporter pdfLoad;
    private Renamer renamer;

    public void initialize() {
        currentAccount = ProfileAccountManager.getCurrentAccount();
        bankAccount = new BankAccount();
        bankAccount.setForeignKeyProfile(currentAccount);
        lbl_account.setText(currentAccount.getName());

        ArrayList<BankAccount> allBankaccounts = DatabaseController.computeStoreClasses(bankAccount);
        lv_bankAccounts.getItems().addAll(allBankaccounts);
        lv_bankAccounts.setCellFactory(bankAccountListView -> {
            ListCell<BankAccount> cell = new ListCell<>() {
                @Override
                protected void updateItem(BankAccount bankAccount, boolean b) {
                    super.updateItem(bankAccount, b);
                    if (bankAccount != null) {
                        setText(bankAccount.getBankName());
                    } else {
                        setText("");
                    }
                }
            };
            return cell;
        });




        Group group = new Group();
        group.setForeignKeyBankAccount(allBankaccounts);
        lv_groups.getItems().setAll(DatabaseController.computeStoreClasses(group));

        ViewController.setBankAccount(bankAccount);
        ViewController.setLv_bankAccounts(lv_bankAccounts);
    }

    public void backToLogin(ActionEvent actionEvent) {
        ProfileAccountManager.setCurrentAccount(null);
        WindowManager.changeSceneTo(Main.windows.LogIn);
    }

    public void newBA(ActionEvent actionEvent) {
        CreateNew<BankAccount> createNew = new CreateNew<>(bankAccount);
        WindowManager.openStageOf(createNew);
    }

    public void refresh(){

    }

    public void newGroup(ActionEvent actionEvent) {

    }
}
