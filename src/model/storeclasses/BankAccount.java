package model.storeclasses;

import controller.ProfileAccountManager;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class BankAccount extends StoreClass {

    private int id;
    private String bankName;
    private ArrayList<Transaction> transactions;

    public BankAccount() {
        setTableName("BankAccount");
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(FieldName.storeId());
        fieldNames.add(new FieldName("BankName", "bank_name","TEXT", StringEntry.class));
        setFieldNames(fieldNames);
        ArrayList<ForeignKey<? extends StoreClass>> foreignKeys = new ArrayList<>();
        foreignKeys.add(new ForeignKey<Profile>("profile_id", new Profile()));
        setForeignKeys(foreignKeys);
    }

    public void setForeignKeyProfile(Profile profile){
        ((ForeignKey<Profile>)getForeignKeys().get(0)).setForeign(profile);
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
