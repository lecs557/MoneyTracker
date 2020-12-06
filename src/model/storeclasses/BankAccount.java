package model.storeclasses;

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
        foreignKeys.add(new ForeignKey<Profile>("Profiles", new Profile()));
        setForeignKeys(foreignKeys);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
