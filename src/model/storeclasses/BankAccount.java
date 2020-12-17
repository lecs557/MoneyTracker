package model.storeclasses;

import controller.ProfileAccountManager;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class BankAccount extends StoreClass {

    private String bankName;
    private ArrayList<ArrayList<Transaction>> years_transactions;
    private Profile profile;

    public BankAccount() {
        setTableName("BankAccount");
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(FieldName.storeId());
        fieldNames.add(new FieldName("BankName", "bank_name","TEXT", StringEntry.class));
        setFieldNames(fieldNames);
        ArrayList<ForeignKey<? extends StoreClass>> foreignKeys = new ArrayList<>();
        foreignKeys.add(new ForeignKey<Profile>("profile_id", new Profile()));
        ArrayList<ForeignKey<? extends StoreClass>> foreignObjects = new ArrayList<>();
        foreignObjects.add(new ForeignKey<Profile>("profile_id", new Profile()));
        setForeignKeys(foreignKeys);
        setForeignObjects(foreignObjects);
    }

    public void setForeignKeyProfile(Profile profile){
        ((ForeignKey<Profile>)getForeignKeys().get(0)).setForeign(profile);
    }

    public void processTransactions(ArrayList<Transaction> transactions){
        int year=0;
        ArrayList<Transaction> yearTraList = null;
        for(Transaction curTransaction: transactions){
            if(curTransaction.getLocalDate().getYear()==year){
                yearTraList.add(curTransaction);
            } else {
                year= curTransaction.getLocalDate().getYear();
                years_transactions.add(yearTraList);
                yearTraList=new ArrayList<>();
            }
        }



    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
