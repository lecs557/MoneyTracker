package view.windows;

import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Main;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.Transaction;
import view.simple_panes.CreateNew;
import view.simple_panes.BankAccountChooser;
import view.simple_panes.TransactionTabPane;

public class OverviewWindowCtrl extends BaseWindowCtrl{

    public AnchorPane pane;
    public Label lbl_account;
    public ListView<BankAccount> lv_bankAccounts;
    public ListView<Group> lv_groups;
    public ProgressBar pb_pdf, pb_year, pb_transa,pb_year1, pb_transa1;
    public Pane savePane;
    public Pane chPane;
    public Pane pdfPane;
    public TransactionTabPane tp_transactions;

    public void initialize() {
        lbl_account.setText(ProfileAccountManager.getCurrentAccount().getName());

        lv_bankAccounts.getItems().setAll(ProfileAccountManager.getBankAccounts());

        lv_groups.getItems().setAll(ProfileAccountManager.getGroups());


        tp_transactions.setTransactions(ProfileAccountManager.getTransactions(),ProfileAccountManager.getGroups());

        ViewController.setLv_bankAccounts(lv_bankAccounts);
        ViewController.setTp_transaction(tp_transactions);
        ViewController.setLv_group(lv_groups);

    }

    public void backToLogin(ActionEvent actionEvent) {
        WindowManager.changeSceneTo(Main.windows.LogIn);
    }

    public void newBA(ActionEvent actionEvent) {
        CreateNew<BankAccount> createNew = new CreateNew<>(ProfileAccountManager.getSqlBankAccount(), false);
        WindowManager.openStageOf(createNew);
    }

    public void refresh(){

    }

    public void newGroup(ActionEvent actionEvent) {
        CreateNew<Group> createNew = new CreateNew<>(ProfileAccountManager.getSqlGroup(), false);
        WindowManager.openStageOf(createNew);
    }

    public void newTransaction(ActionEvent actionEvent) {
        CreateNew<Transaction> createNew = new CreateNew<>(ProfileAccountManager.getSqlTransaction(), false);
        WindowManager.openStageOf(createNew);
    }

    public void importTransactionViaPDF(ActionEvent actionEvent) {
        BankAccountChooser ifb = new BankAccountChooser(ProfileAccountManager.getBankAccounts());
        WindowManager.openStageOf(ifb);

    }
}
