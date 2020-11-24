package model.storeclasses;

import model.FieldName;
import model.MyDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transaction extends StoreClass {

    private DateTimeFormatter form = DateTimeFormatter.BASIC_ISO_DATE;
    private int id;
    private LocalDate date;
    private String purpose;
    private int amount;
    private int balance;
    private ArrayList<String> pdfPaths;

    public Transaction() {

    }

    public Transaction(LocalDate date, String purpose, int amount) {
        this.date = date;
        this.purpose = purpose;
        this.amount = amount;
    }

    public void calculateBalance(int sum){
        balance =sum+ amount;
    }

    /* GETTER & SETTER */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public ArrayList<String> getPdfPaths() {
        return pdfPaths;
    }

    public void setPdfPaths(ArrayList<String> pdfPaths) {
        this.pdfPaths = pdfPaths;
    }

    public String getDate_S() {
        return date.format(form);
    }

    public LocalDate getDate() {
        return date;
    }

    public MyDate getMyDate() {
        return new MyDate(date);
    }

    public String getPurpose() {
        return purpose;
    }

    public int getAmount() {
        return amount;
    }

    public int getBalance() {
        return balance;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    @Override
    public ArrayList<FieldName> getFieldNames(){
        ArrayList<FieldName> fieldNames = new ArrayList<FieldName>();
        fieldNames.add(new FieldName("id", "id","INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT"));
        fieldNames.add(new FieldName("date", "date","DATE"));
        fieldNames.add(new FieldName("purpose", "purpose","TEXT"));
        fieldNames.add(new FieldName("amount", "amount","int"));
        fieldNames.add(new FieldName("", "profile_id","int"));
        fieldNames.add(new FieldName("", "pdf_id","int"));
        return fieldNames;
    }

    @Override
    public String getTableName() {
        return "Transaction";
    }
}
