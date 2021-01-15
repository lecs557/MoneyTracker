package controller;

import javafx.scene.control.ListView;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import view.simple_panes.StoreClassTable;

public class ViewController {

    private static ListView<BankAccount> lv_bankAccounts;
    private static ListView<Group> lv_group;
    private static StoreClassTable sct_transaction;

    public static void refreshBankAccounts() {
        lv_bankAccounts.getItems().addAll(ProfileAccountManager.getBankAccounts());
    }

    public static void refreshGroups() {
        lv_group.getItems().addAll(ProfileAccountManager.getGroups());
    }

    public static void refreshTransactions() {
        sct_transaction.getItems().addAll(ProfileAccountManager.getTransactions());
    }

    public static void setLv_bankAccounts(ListView<BankAccount> lv_bankAccounts) {
        ViewController.lv_bankAccounts = lv_bankAccounts;
    }

    public static void setLv_group(ListView<Group> lv_group) {
        ViewController.lv_group = lv_group;
    }

    public static void setLv_transaction(StoreClassTable sct_transaction) {
        ViewController.sct_transaction = sct_transaction;
    }
}
