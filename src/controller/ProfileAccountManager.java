package controller;

import model.storeclasses.*;

import java.util.ArrayList;
import java.util.Map;

public class ProfileAccountManager {

    //From DataBase
    private static Profile currentAccount;
    private static ArrayList<BankAccount> profilesBankAccounts;
    private static ArrayList<Group> profilesGroups;
    private static ArrayList<Transaction> profilesTransactions;
    private static ArrayList<InvoiceFile> profilesInvoiceFiles;


    //From Software



    public static void setupProfile(Profile currentAccount) {
        ProfileAccountManager.currentAccount = currentAccount;

        BankAccount.ForeignKeys.profile.set(currentAccount);
        profilesBankAccounts = DatabaseController.computeStoreClasses(new BankAccount(),"");

        Group.ForeignKeys.bankAccount.setForeignObjects(profilesBankAccounts);
        profilesGroups = DatabaseController.computeStoreClasses(new Group(),"");

        Transaction.ForeignKeys.bankAccount.setForeignObjects(profilesBankAccounts);
        Transaction.ForeignKeys.group.setForeignObjects(profilesGroups);
        profilesTransactions = DatabaseController.computeStoreClasses(new Transaction(),"");

        profilesInvoiceFiles = DatabaseController.computeStoreClasses(new InvoiceFile(),"");

        computeBalance(profilesTransactions);
        computeSum(profilesGroups, profilesTransactions);
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
                    g.reset(year);
                }
                curYear=year;
            }
            if (tra.getGroupId()==0) continue;
            tra.findGroup(groups).addSum(year,Integer.parseInt(tra.getAmount()));
        }
    }

    public static void add(BankAccount bankAccount){
        profilesBankAccounts.add(bankAccount);
    }

    public static Profile getCurrentAccount() {
        return currentAccount;
    }

    public static ArrayList<BankAccount> getProfilesBankAccounts() {
        return profilesBankAccounts;
    }


    public static ArrayList<Group> getProfilesGroups() {
        return profilesGroups;
    }

    public static ArrayList<Transaction> getProfilesTransactions() {
        return profilesTransactions;
    }

    public static ArrayList<InvoiceFile> getProfilesInvoiceFiles() {
        return profilesInvoiceFiles;
    }

    public static void setProfilesTransactions(ArrayList<Transaction> profilesTransactions) {
        ProfileAccountManager.profilesTransactions = profilesTransactions;
    }

    public static <T extends StoreClass> StoreClass getById(T storeClazz, int id){
        if(storeClazz instanceof InvoiceFile){
            for(InvoiceFile invoiceFile: profilesInvoiceFiles){
                if(invoiceFile.getId()==id){
                    return invoiceFile;
                }
            }
        }
        if(storeClazz instanceof BankAccount){
            for(BankAccount bankAccount: profilesBankAccounts){
                if(bankAccount.getId()==id){
                    return bankAccount;
                }
            }
        }
        return null;

    }
}
