package controller;

import model.storeclasses.*;

import java.util.ArrayList;

public class ProfileAccountManager {

    private static Profile currentAccount;
    private static ArrayList<BankAccount> bankAccounts;
    private static BankAccount profilesBankAccount;
    private static ArrayList<Group> groups;
    private static Group profilesGroup;
    private static ArrayList<Transaction> transactions;
    private static Transaction profilesTransaction;


    public static void setupProfile(Profile currentAccount) {
        ProfileAccountManager.currentAccount = currentAccount;

        profilesBankAccount = new BankAccount();
        profilesBankAccount.setForeignKeyProfile(currentAccount);
        bankAccounts = DatabaseController.computeStoreClasses(profilesBankAccount,"");

        profilesGroup = new Group();
        profilesGroup.setForeignKeyBankAccount(bankAccounts);
        groups = DatabaseController.computeStoreClasses(profilesGroup,"");

        profilesTransaction = new Transaction();
        profilesTransaction.setForeignKeyBankAccount(bankAccounts);
        transactions = DatabaseController.computeStoreClasses(profilesTransaction,"");
    }

    public static Profile getCurrentAccount() {
        return currentAccount;
    }

    public static ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public static BankAccount getProfilesBankAccount() {
        return profilesBankAccount;
    }

    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static Group getProfilesGroup() {
        return profilesGroup;
    }

    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public static Transaction getProfilesTransaction() {
        return profilesTransaction;
    }
}
