package controller;

import javafx.scene.control.ListView;
import model.storeclasses.BankAccount;
import view.windows.LogInWindowCtrl;

public class ViewController {

    private static ListView<BankAccount> lv_bankAccounts;
    private static BankAccount bankAccount;

    public static void refresh(){
        lv_bankAccounts.getItems().addAll(DatabaseController.computeStoreClasses(bankAccount));
    }

    public static void setLv_bankAccounts(ListView<BankAccount> lv_bankAccounts) {
        ViewController.lv_bankAccounts = lv_bankAccounts;
    }

    public static void setBankAccount(BankAccount bankAccount) {
        ViewController.bankAccount = bankAccount;
    }
}
