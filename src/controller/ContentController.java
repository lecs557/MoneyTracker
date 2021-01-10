package controller;

import model.storeclasses.BankAccount;

public class ContentController {

    private static BankAccount bankAccount;

    public static BankAccount getBankAccount() {
        return bankAccount;
    }

    public static void setBankAccount(BankAccount bankAccount) {
        ContentController.bankAccount = bankAccount;
    }
}
