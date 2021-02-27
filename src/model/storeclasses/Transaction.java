package model.storeclasses;

import controller.ProfileAccountManager;
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

    private ArrayList<String> groupsIds;
    private int balance;


    public Transaction() {
        tableName = "Transactions";
    }

    public static class Variables {
        public static FieldName id = FieldName.storeId();
        public static FieldName date = new FieldName("Date", "date","DATE", DateEntry.class);
        public static FieldName purpose = new FieldName("Purpose", "purpose","TEXT", StringEntry.class);
        public static FieldName amount = new FieldName("Amount", "amount","int", AmountEntry.class);
    }

    public static class ForeignKeys{
        public static ForeignKey<BankAccount> bankAccount = new ForeignKey<BankAccount>("BankAccountId","bankAccount_id", new BankAccount());
    }

    public int getIntAmount(){
        return Integer.parseInt(amount);
    }

    public Group getGroup(){
        return null;
    }

    public LocalDate getLocalDate(){
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setLocalDate(LocalDate date){
        this.date=date.format(DateTimeFormatter.ISO_LOCAL_DATE);
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
