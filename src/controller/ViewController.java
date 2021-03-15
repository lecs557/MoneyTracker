package controller;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import model.storeclasses.*;
import view.simple_panes.StoreClassTable;
import view.simple_panes.TransactionTabPane;
import view.simple_panes.TransactionTable;

import java.util.Collection;

public class ViewController {

    private static ChoiceBox<Profile> chb_profiles;
    private static ListView<BankAccount> lv_bankAccounts;
    private static ListView<Group> lv_group;
    private static TransactionTabPane tp_transaction;

    public static <T extends StoreClass> void refresh(T storeClass) {
        Platform.runLater(() -> {
            if (storeClass instanceof BankAccount){
                lv_bankAccounts.getItems().setAll(DatabaseController.computeStoreClasses(new BankAccount(),""));
            } else if (storeClass instanceof Profile){
                chb_profiles.getItems().setAll(DatabaseController.computeStoreClasses(new Profile(),""));
            } else if (storeClass instanceof Group){
                lv_group.getItems().setAll(DatabaseController.computeStoreClasses(new Group(),""));
            } else if (storeClass instanceof Transaction){
                tp_transaction.setTransactions(DatabaseController.computeStoreClasses(new Transaction(),Transaction.Variables.date.getSqlName()));
            }
        });
    }

    public static void refreshTransactions() {

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

    public static ChoiceBox<Profile> getChb_profiles() {
        return chb_profiles;
    }

    public static void setChb_profiles(ChoiceBox<Profile> chb_profiles) {
        ViewController.chb_profiles = chb_profiles;
    }
}
