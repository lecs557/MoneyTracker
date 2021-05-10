package view.windows;

import controller.IOController;
import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.AppStart;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.Transaction;
import view.simple_panes.*;

public class OverviewWindowCtrl extends BaseWindowCtrl{

    public AnchorPane pane;
    public Label lbl_account;
    public ProgressBar pb_pdf;
    public TransactionTabPane tp_transactions;
    public StoreClassList<BankAccount> vb_bankAccounts;
    public GroupList  vb_groups;
    public TransactionChart ch_transaction;
    public SumTable tl_groupSums;

    public void initialize() {
        ViewController.setOverviewWindowCtrl(this);

        pb_pdf.visibleProperty().bind(IOController.runningProperty());
        pb_pdf.progressProperty().bind(IOController.progressProperty());

        lbl_account.setText(ProfileAccountManager.getCurrentAccount().getName());
        vb_bankAccounts.getListView().getItems().setAll(ProfileAccountManager.getProfilesBankAccounts());
        vb_groups.getListView().getItems().setAll(ProfileAccountManager.getProfilesGroups());
        tp_transactions.applyTransactions(ProfileAccountManager.getProfilesTransactions(),ProfileAccountManager.getProfilesGroups());
        tl_groupSums.applyGroups(ProfileAccountManager.getProfilesGroups());
        ch_transaction.applyTransactions(ProfileAccountManager.getProfilesTransactions());

    }

    public void backToLogin(ActionEvent actionEvent) {
        WindowManager.changeSceneTo(AppStart.windows.LogIn);
    }

    public void newBA(ActionEvent actionEvent) {
        CreateNew<BankAccount> createNew = new CreateNew<>(new BankAccount(), false);
        WindowManager.openStageOf(createNew);
    }

    public void refresh(){

    }

    public void newGroup(ActionEvent actionEvent) {
        CreateNew<Group> createNew = new CreateNew<>(new Group(), false);
        WindowManager.openStageOf(createNew);
    }

    public void newTransaction(ActionEvent actionEvent) {
        CreateNew<Transaction> createNew = new CreateNew<>(new Transaction(), false);
        WindowManager.openStageOf(createNew);
    }

    public void importTransactionViaPDF(ActionEvent actionEvent) {
        BankAccountChooser ifb = new BankAccountChooser(ProfileAccountManager.getProfilesBankAccounts());
        WindowManager.openStageOf(ifb);

    }
}
