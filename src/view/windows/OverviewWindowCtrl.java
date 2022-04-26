package view.windows;

import controller.IOController;
import controller.ProfileAccountManager;
import controller.ViewController;
import controller.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.AppStart;
import model.storeclasses.Transaction;
import view.panes.elements.*;
import view.simple_panes.*;

public class OverviewWindowCtrl extends BaseWindowCtrl{
    public Label lbl_account;
    public ProgressBar pb_pdf;
    public TransactionTabPane tp_transactions;
    public BankAccountList vb_bankAccounts;
    public GroupList vb_groups;
    public TransactionChart ch_transaction;
    public SumTable tl_groupSums;
    public void initialize() {
        ViewController.getInstance().setOverviewWindowCtrl(this);

        IOController io = IOController.getInstance();
        pb_pdf.visibleProperty().bind(io.pdfRunningProperty());
        pb_pdf.progressProperty().bind(io.pdfPogressProperty());

        ProfileAccountManager pf = ProfileAccountManager.getInstance();
        lbl_account.setText(pf.getCurrentAccount().getName());
        vb_bankAccounts.getListView().getItems().setAll(pf.getProfilesBankAccounts());
        vb_groups.getListView().getItems().setAll(pf.getProfilesGroups());
        tp_transactions.applyTransactions(pf.getProfilesTransactions(),pf.getProfilesGroups());
        tl_groupSums.applyGroups(pf.getProfilesGroups());
        ch_transaction.applyTransactions(pf.getProfilesTransactions());

        for(TransactionTable t: tp_transactions.getTransactionTables()){
            t.connect(ch_transaction);
        }
        ch_transaction.connect(tp_transactions);
    }
    public void backToLogin(ActionEvent actionEvent) {
        WindowManager.getInstance().changeSceneTo(AppStart.windows.LogIn);
    }
    public void newTransaction(ActionEvent actionEvent) {
        CreateNew<Transaction> createNew = new CreateNew<>(new Transaction(), false);
        WindowManager.getInstance().openStageOf(createNew);
    }
    public void importTransactionViaPDF(ActionEvent actionEvent) {
        BankAccountChooser ifb = new BankAccountChooser(ProfileAccountManager.getInstance().getProfilesBankAccounts());
        WindowManager.getInstance().openStageOf(ifb);
    }
    public void setGroupByPattern(){
        PatternGroup pg = new PatternGroup();
        WindowManager.getInstance().openStageOf(pg);
    }
}
