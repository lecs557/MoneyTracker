package view.windows;

import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Main;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.Transaction;
import view.simple_panes.*;

public class OverviewWindowCtrl extends BaseWindowCtrl{

    public AnchorPane pane;
    public Label lbl_account;
    public ProgressBar pb_pdf, pb_year, pb_transa,pb_year1, pb_transa1;
    public Pane savePane;
    public Pane chPane;
    public Pane pdfPane;
    public TransactionTabPane tp_transactions;
    public BankAccountList vb_bankAccounts;
    public GroupList  vb_groups;
    public TransactionChart ch_transaction;
    public SumTable tl_groupSums;

    public void initialize() {
        lbl_account.setText(ProfileAccountManager.getCurrentAccount().getName());
        vb_bankAccounts.listView.getItems().setAll(ProfileAccountManager.getBankAccounts());
        vb_groups.listView.getItems().setAll(ProfileAccountManager.getGroups());
        tp_transactions.setTransactions(ProfileAccountManager.getTransactions(),ProfileAccountManager.getGroups());

        ViewController.setLv_bankAccounts(vb_bankAccounts.listView);
        ViewController.setTp_transaction(tp_transactions);
        ViewController.setLv_group(vb_groups.listView);

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
