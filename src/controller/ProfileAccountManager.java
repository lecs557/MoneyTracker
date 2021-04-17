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


    public static void setupProfile(Profile currentAccount) {
        ProfileAccountManager.currentAccount = currentAccount;

        BankAccount.ForeignKeys.profile.set(currentAccount);
        bankAccounts = DatabaseController.computeStoreClasses(new BankAccount(),"");

        Group.ForeignKeys.bankAccount.setForeignObjects(bankAccounts);
        groups = DatabaseController.computeStoreClasses(new Group(),"");

        Transaction.ForeignKeys.bankAccount.setForeignObjects(bankAccounts);
        transactions = DatabaseController.computeStoreClasses(new Transaction(),"");
        computeBalance(transactions);
        computeSum(groups,transactions);
    }

    public static void computeBalance(ArrayList<Transaction> transactions){
        int sum=0;
        for(Transaction transaction:transactions){
            sum+=Integer.parseInt(transaction.getAmount());
            transaction.setBalance(sum);
        }
    }

    public static void computeSum(ArrayList<Group> groups, ArrayList<Transaction> transactions){
        int curYear=0;
        for (Transaction tra:transactions){
            int year=tra.getLocalDate().getYear();
            if(year!=curYear){
                for(Group g:groups){
                    g.addSum(year,0);
                }
                curYear=year;
            }
            if (tra.getGroupId()==0) continue;;
            tra.findGroup(groups).addSum(year,Integer.parseInt(tra.getAmount()));
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


    public static ArrayList<Group> getGroups() {
        return groups;
    }

    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public static void setTransactions(ArrayList<Transaction> transactions) {
        ProfileAccountManager.transactions = transactions;
    }
}
