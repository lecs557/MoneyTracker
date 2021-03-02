package controller;

import model.storeclasses.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ProfileAccountManager {

    //From DataBase
    private static Profile currentAccount;
    private static ArrayList<BankAccount> bankAccounts;
    private static ArrayList<Group> groups;
    private static ArrayList<Transaction> transactions;

    //From Software
    private static Map<Integer, ArrayList<Transaction>> transactionMap;
    private static int sum=0 ;

    //SQL-Objects
    private static BankAccount sqlBankAccount;
    private static Group sqlGroup;
    private static Transaction sqlTransaction;


    public static void setupProfile(Profile currentAccount) {
        ProfileAccountManager.currentAccount = currentAccount;

        BankAccount.ForeignKeys.profile.set(currentAccount);
        sqlBankAccount = new BankAccount();
        bankAccounts = DatabaseController.computeStoreClasses(sqlBankAccount,"");

        sqlGroup = new Group();
        Group.ForeignKeys.bankAccount.setForeignObjects(bankAccounts);
        groups = DatabaseController.computeStoreClasses(sqlGroup,"");

        sqlTransaction = new Transaction();
        Transaction.ForeignKeys.bankAccount.setForeignObjects(bankAccounts);
        transactions = DatabaseController.computeStoreClasses(sqlTransaction,"");


        for(Transaction transaction:transactions){
            sum+=Integer.parseInt(transaction.getAmount());
            transaction.setBalance(sum);
            Group g = transaction.getGroup();
            if (g != null) {
                g.addSum(Integer.parseInt(transaction.getAmount()));
            }
        }
    }

    public static void add(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }

    public static Profile getCurrentAccount() {
        return currentAccount;
    }

    public static ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public static BankAccount getSqlBankAccount() {
        return sqlBankAccount;
    }

    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static Group getSqlGroup() {
        return sqlGroup;
    }

    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(ArrayList<Transaction> transactions) {
        ProfileAccountManager.transactions = transactions;
    }

    public static Transaction getSqlTransaction() {
        return sqlTransaction;
    }
}
