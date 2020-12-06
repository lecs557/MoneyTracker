package model.storeclasses;

import view.panes.entry_panes.AmountEntry;
import view.panes.entry_panes.DateEntry;
import view.panes.entry_panes.StringEntry;

import java.util.ArrayList;

public class Transaction extends StoreClass {

    private String date;
    private String purpose;
    private String amount;
    private int balance;

    public Transaction() {
        setTableName("Transactions");
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(FieldName.storeId());
        fieldNames.add(new FieldName("Date", "date","DATE", DateEntry.class));
        fieldNames.add(new FieldName("Purpose", "purpose","TEXT", StringEntry.class));
        fieldNames.add(new FieldName("Amount", "amount","int", AmountEntry.class));
        setFieldNames(fieldNames);
        ArrayList<ForeignKey<? extends StoreClass>> foreignKeys = new ArrayList<>();
        foreignKeys.add(new ForeignKey<BankAccount>("Bank_Account", new BankAccount()));
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
