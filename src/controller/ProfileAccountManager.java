package controller;

import model.storeclasses.*;

import java.util.ArrayList;

public class ProfileAccountManager {
    
    private static ProfileAccountManager singleton;
    
    private ProfileAccountManager(){}
    
    public static void initialize(){
        singleton = new ProfileAccountManager();
    }
    
    public static ProfileAccountManager getInstance(){
        if (singleton == null) {
            initialize();
        }
        return singleton;
    }
        

    //From DataBase
    private Profile currentAccount;
    private ArrayList<BankAccount> profilesBankAccounts;
    private ArrayList<Group> profilesGroups;
    private ArrayList<Transaction> profilesTransactions;
    private ArrayList<InvoiceFile> profilesInvoiceFiles;


    //From Software
    private final ArrayList<Transaction> importedTransactions = new ArrayList<>();



    public void setupProfile(Profile currentAccount) {
        DatabaseController db = DatabaseController.getInstance();
        ProfileAccountManager.getInstance().currentAccount = currentAccount;

        BankAccount.ForeignKeys.profile.set(currentAccount);
        profilesBankAccounts = db.computeStoreClasses(new BankAccount(),"");

        Group.ForeignKeys.bankAccount.setForeignObjects(profilesBankAccounts);
        profilesGroups = db.computeStoreClasses(new Group(),"");

        Transaction.ForeignKeys.bankAccount.setForeignObjects(profilesBankAccounts);
        Transaction.ForeignKeys.group.setForeignObjects(profilesGroups);
        profilesTransactions = db.computeStoreClasses(new Transaction(),"");

        profilesInvoiceFiles = db.computeStoreClasses(new InvoiceFile(),"");

        computeBalance(profilesTransactions);
        computeSum(profilesGroups, profilesTransactions);
    }

    public void computeBalance(ArrayList<Transaction> transactions){
        int sum=0;
        for(Transaction transaction:transactions){
            sum+=Integer.parseInt(transaction.getAmount());
            transaction.setBalance(sum);
        }
    }

    public void computeSum(ArrayList<Group> groups, ArrayList<Transaction> transactions){
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

    public void add(BankAccount bankAccount){
        profilesBankAccounts.add(bankAccount);
    }

    public Profile getCurrentAccount() {
        return currentAccount;
    }

    public ArrayList<BankAccount> getProfilesBankAccounts() {
        return profilesBankAccounts;
    }


    public ArrayList<Group> getProfilesGroups() {
        return profilesGroups;
    }

    public ArrayList<Transaction> getProfilesTransactions() {
        return profilesTransactions;
    }

    public ArrayList<InvoiceFile> getProfilesInvoiceFiles() {
        return profilesInvoiceFiles;
    }

    public void addNewAddedTransaction(Transaction transaction) {
        importedTransactions.add(transaction);
    }

    public ArrayList<Transaction> getImportedTransactions() {
        return importedTransactions;
    }

    public <T extends StoreClass> StoreClass getById(T storeClazz, int id){
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
