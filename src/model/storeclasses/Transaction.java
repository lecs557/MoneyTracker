package model.storeclasses;

import view.panes.entry_panes.AmountEntry;
import view.panes.entry_panes.DateEntry;
import view.panes.entry_panes.StringEntry;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transaction extends StoreClass {

    private String date;
    private String purpose;
    private String amount;
    private String bankackountId;
    private String groupId;
    private int balance;

    public Transaction() {
        tableName = "Transactions";
        foreignName = new FieldName("TransactionId","transaction_id","",null);
        fieldNames.add(new FieldName("Date", "date","DATE", DateEntry.class));
        fieldNames.add(new FieldName("Purpose", "purpose","TEXT", StringEntry.class));
        fieldNames.add(new FieldName("Amount", "amount","int", AmountEntry.class));
        foreignKeys.add(new ArrayList<BankAccount>());
        foreignKeys.add(new ArrayList<Group>());
    }

    public void setForeignKeyBankAccount(ArrayList<BankAccount> bankAccounts){
        foreignKeys.get(0).clear();
        ((ArrayList<BankAccount>) foreignKeys.get(0)).addAll(bankAccounts);
    }

    public void setForeignKeyGroup(ArrayList<Group> groups){
        foreignKeys.get(1).clear();
        ((ArrayList<Group>) foreignKeys.get(1)).addAll(groups);
    }

    public LocalDate getLocalDate(){
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
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
