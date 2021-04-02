package model.storeclasses;

import view.panes.entry_panes.AmountEntry;
import view.panes.entry_panes.DateEntry;
import view.panes.entry_panes.StringEntry;
import view.simple_panes.SampleClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class Transaction extends StoreClass {

    private static int sampleId=1;

    private static int defaultBankAccountId;

    private String date;
    private String purpose;
    private String amount;
    private final ArrayList<String> invoiceFileIds = new ArrayList<>();

    private int bankAccountId;
    private int groupId;

    private int balance;


    public Transaction() {
        tableName = "Transactions";
        bankAccountId=defaultBankAccountId;
    }

    public static void setDefaultBankAccountId(int id) {
        defaultBankAccountId=id;
    }

    public static class Variables {
        public static FieldName id = FieldName.storeId();
        public static FieldName date = new FieldName("Date", "date","DATE", DateEntry.class);
        public static FieldName purpose = new FieldName("Purpose", "purpose","TEXT", StringEntry.class);
        public static FieldName amount = new FieldName("Amount", "amount","int", StringEntry.class);
        public static FieldName invoiceFileIds = new FieldName("InvoiceFileIds", "invoiceFileIds","Text", StringEntry.class);

    }

    public static class ForeignKeys{
        public static ForeignKey<BankAccount> bankAccount = new ForeignKey<BankAccount>("BankAccountId","bankAccount_id", new BankAccount());
        public static ForeignKey<Group> group = new ForeignKey("GroupId", "group_id",new Group());
    }

    public String getInvoiceFileIds() {
        String temp = "";
        Iterator<String> iterator = invoiceFileIds.iterator();
        while (iterator.hasNext()){
            temp+=iterator.next();
            if (iterator.hasNext()){
                temp+="; ";
            }
        }
        return temp;
    }

    public Group findGroup(ArrayList<Group> allGroups){
        if (getGroupId()==0) return null;
        for (Group g:allGroups){
            if (groupId==g.getId()) return g;
        }
        return null;
    }

    public void setInvoiceFileIds(String temp) {
        this.invoiceFileIds.addAll(Arrays.asList(temp.split("; ")));
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public static Transaction sampleTransaction(){
        Transaction sample = new Transaction();
        sample.setId(sampleId);
        sample.setLocalDate(LocalDate.of(SampleClass.random(2014,2020),SampleClass.random(1,12),SampleClass.random(1,27)));
        sample.setPurpose("Test");
        sample.setAmount(""+SampleClass.random(-50000,50000));
        sample.setGroupId(SampleClass.random(0,2));
        sampleId++;
        return sample;
    }
}
