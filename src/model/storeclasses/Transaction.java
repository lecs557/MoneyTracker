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
    private String bankAccountId;
    private String groupId;
    private int balance;

    public Transaction() {
        tableName = "Transactions";
        fieldNames.add(new FieldName("Date", "date","DATE", DateEntry.class));
        fieldNames.add(new FieldName("Purpose", "purpose","TEXT", StringEntry.class));
        fieldNames.add(new FieldName("Amount", "amount","int", AmountEntry.class));
        foreignKeys.add(new ForeignKey<BankAccount>("BankAccountId","bankAccount_id", new BankAccount()));
        foreignKeys.add(new ForeignKey<Group>("GroupId","group_id", new Group()));
    }

    public void setForeignKeyBankAccount(ArrayList<BankAccount> bankAccounts){
        ((ForeignKey<BankAccount>) foreignKeys.get(0)).setForeignObjects(bankAccounts);
    }

    public void setForeignKeyGroup(ArrayList<Group> groups){
        ((ForeignKey<Group>) foreignKeys.get(1)).setForeignObjects(groups);
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

    public String getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
