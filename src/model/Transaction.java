package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction  {

    private DateTimeFormatter form = DateTimeFormatter.BASIC_ISO_DATE;
    private LocalDate date;

    private String reason;
    private int betrag;
    private int konto;

    public Transaction(String[] temp) {
        int i=0;
        for (String elem:temp){
            if(i==1){
                this.reason = elem;
            }
            if(i==0){
                try {
                    this.date = LocalDate.parse(elem,form);
                } catch (Exception e) {
                    this.reason+=" "+elem;
                    this.date = LocalDate.of(1995,9,17);
                }
            }
            if(i==2){
                try {
                    this.betrag = Integer.parseInt(elem);
                } catch (NumberFormatException e) {
                    this.betrag=0;
                    this.reason+=" "+elem;
                }
            }
            i++;
        }
    }

    public Transaction(LocalDate date, String reason, int betrag) {
        this.date = date;
        this.reason = reason;
        this.betrag = betrag;
    }

    public void setThis(Transaction transaction) {
        this.date = transaction.getDate();
        this.reason = transaction.getReason();
        this.betrag = transaction.getBetrag();
    }

    public String store(){
        return date.format(form) + Main.SEPARATOR +
                reason + Main.SEPARATOR +
                betrag + Main.SEPARATOR + Main.ENDSEPARATOR;
    }

    public void berechneKontoStand(int sum){
        konto =sum+betrag;

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

    public String getReason() {
        return reason;
    }

    public int getBetrag() {
        return betrag;
    }

    public int getKonto() {
        return konto;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static Transaction transactionFromString(String string){
        String[] temp = string.split(Main.SEPARATOR);
        return new Transaction(temp);
    }
}
