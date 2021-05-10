package view.simple_panes;

import controller.ProfileAccountManager;
import model.storeclasses.BankAccount;
import model.storeclasses.Group;
import model.storeclasses.StoreClass;
import model.storeclasses.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class SampleClass {

    private static ArrayList<Transaction> sampleTransactions;
    private static ArrayList<BankAccount> sampleBankAccounts;
    private static ArrayList<Group> sampleGroups;

    public static <T extends StoreClass> ArrayList<T> getSampleData(T storeclass){
        if (storeclass instanceof BankAccount){
            return (ArrayList<T>) getSampleBankAccounts();
        }
        if(storeclass instanceof Group){
            return (ArrayList<T>) getSampleGroups();
        }
        return null;
    }

    public static ArrayList<Transaction> getSampleTransactions(){
        if (sampleTransactions != null) {
            return sampleTransactions;
        }
        sampleTransactions = new ArrayList<>();
        sampleTransactions.add(Transaction.sampleTransaction());
        sampleTransactions.add(Transaction.sampleTransaction());
        sampleTransactions.add(Transaction.sampleTransaction());
        sampleTransactions.add(Transaction.sampleTransaction());
        sampleTransactions.add(Transaction.sampleTransaction());
        sampleTransactions.add(Transaction.sampleTransaction());
        sampleTransactions.sort(Comparator.comparing(Transaction::getLocalDate));
        ProfileAccountManager.computeBalance(sampleTransactions);
        return sampleTransactions;
    }

    public static ArrayList<BankAccount> getSampleBankAccounts() {
        if (sampleBankAccounts != null) {
            return sampleBankAccounts;
        }
        sampleBankAccounts = new ArrayList<>();
        sampleBankAccounts.add(BankAccount.sampleBankAccount());
        sampleBankAccounts.add(BankAccount.sampleBankAccount());
        return sampleBankAccounts;
    }

    public static ArrayList<Group> getSampleGroups() {
        if (sampleGroups != null) {
            return sampleGroups;
        }
        sampleGroups = new ArrayList<>();
        sampleGroups.add(Group.sampleGroup());
        sampleGroups.add(Group.sampleGroup());
        ProfileAccountManager.computeSum(sampleGroups,getSampleTransactions());
        return sampleGroups;
    }

    public static int random(int min, int max) {
        Random ran = new Random();
        int x = ran.nextInt(max - min) + min;
        return x;
    }
}
