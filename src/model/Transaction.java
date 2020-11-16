package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Transaction  {

    private DateTimeFormatter form = DateTimeFormatter.BASIC_ISO_DATE;
    private LocalDate date;
    private String purpose;
    private int amount;
    private int balance;
    private ArrayList<String> pdfPaths;

    public Transaction(String[] temp) {
        int i=0;
        for (String elem:temp){
            if(i==1){
                this.purpose = elem;
            }
            if(i==0){
                try {
                    this.date = LocalDate.parse(elem,form);
                } catch (Exception e) {
                    this.purpose +=" "+elem;
                    this.date = LocalDate.of(1995,9,17);
                }
            }
            if(i==2){
                try {
                    this.amount = Integer.parseInt(elem);
                } catch (NumberFormatException e) {
                    this.amount =0;
                    this.purpose +=" "+elem;
                }
            }
            i++;
        }
    }

    public Transaction(LocalDate date, String purpose, int amount) {
        this.date = date;
        this.purpose = purpose;
        this.amount = amount;
    }

    public void setThis(Transaction transaction) {
        this.date = transaction.getDate();
        this.purpose = transaction.getPurpose();
        this.amount = transaction.getAmount();
    }

    public String store(){
        return date.format(form) + Main.SEPARATOR +
                purpose + Main.SEPARATOR +
                amount + Main.SEPARATOR + Main.ENDSEPARATOR;
    }

    public void calculateBalance(int sum){
        balance =sum+ amount;

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

    public static Transaction transactionFromString(String string){
        String[] temp = string.split(Main.SEPARATOR);
        return new Transaction(temp);
    }
}
