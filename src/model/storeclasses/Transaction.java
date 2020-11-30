package model.storeclasses;

import java.time.LocalDate;
import java.util.ArrayList;

public class Transaction extends StoreClass {

    private int id;
    private LocalDate date;
    private String purpose;
    private int amount;
    private int balance;

    public Transaction() {
        setTableName("Transactions");
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"));
        fieldNames.add(new FieldName("date", "date","DATE"));
        fieldNames.add(new FieldName("purpose", "purpose","TEXT"));
        fieldNames.add(new FieldName("amount", "amount","int"));
        setFieldNames(fieldNames);
        ArrayList<FieldName> foreignKeys = new ArrayList<FieldName>();
        foreignKeys.add(new FieldName("BankAccount","bank_account_id","INTEGER"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public ArrayList<FieldName> getFieldNames(){
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"));
        fieldNames.add(new FieldName("date", "date","DATE"));
        fieldNames.add(new FieldName("purpose", "purpose","TEXT"));
        fieldNames.add(new FieldName("amount", "amount","int"));
        return fieldNames;
    }

    @Override
    public String getTableName() {
        return "Transaction";
    }
}
