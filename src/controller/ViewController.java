package controller;

import javafx.scene.control.ListView;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.Transaction;
import view.simple_panes.StoreClassTable;
import view.simple_panes.TransactionTabPane;
import view.simple_panes.TransactionTable;

public class ViewController {

    private static ListView<BankAccount> lv_bankAccounts;
    private static ListView<Group> lv_group;
    private static TransactionTabPane tp_transaction;

    public static void refresh() {
        lv_bankAccounts.getItems().setAll(ProfileAccountManager.getBankAccounts());
        lv_group.getItems().setAll(ProfileAccountManager.getGroups());
    }

    public static void refreshTransactions() {
        Transaction.ForeignKeys.bankAccount.setForeignObjects(ProfileAccountManager.getBankAccounts());
        ProfileAccountManager.setTransactions(DatabaseController.computeStoreClasses(new Transaction(),Transaction.Variables.date.getSqlName()));
        tp_transaction.setTransactions(ProfileAccountManager.getTransactions());
    }

    public static void setLv_bankAccounts(ListView<BankAccount> lv_bankAccounts) {
        ViewController.lv_bankAccounts = lv_bankAccounts;
    }

    public static void setLv_group(ListView<Group> lv_group) {
        ViewController.lv_group = lv_group;
    }

    public static void setTp_transaction(TransactionTabPane sct_transaction) {
        ViewController.tp_transaction = sct_transaction;
    }
}
