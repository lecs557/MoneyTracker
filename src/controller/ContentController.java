package controller;

import model.storeclasses.BankAccount;

public class ContentController {

    private static ContentController singleton;

    private ContentController(){}

    public static void initialize(){
        singleton = new ContentController();
    }

    public static ContentController getInstance(){
        if (singleton == null) {
            initialize();
        }
        return singleton;
    }


    private BankAccount bankAccount;

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
