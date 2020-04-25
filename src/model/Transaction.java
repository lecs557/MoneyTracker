package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private DateTimeFormatter form = DateTimeFormatter.BASIC_ISO_DATE;
    private LocalDate date;
    private String reason;
    private int betrag;
    private int konto;

    public Transaction(String date, String reason, String betrag) {
        this.reason = reason;

        try {
            this.date = LocalDate.parse(date,form);
        } catch (Exception e) {
            this.reason+=" "+date;
            this.date = LocalDate.of(1995,9,17);
        }

        try {
            this.betrag = Integer.parseInt(betrag);
        } catch (NumberFormatException e) {
            this.betrag=0;
            this.reason+=" "+betrag;
        }
    }

    public Transaction(LocalDate date, String reason, int betrag) {
        this.date = date;
        this.reason = reason;
        this.betrag = betrag;
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

    public String getReason() {
        return reason;
    }

    public int getBetrag() {
        return betrag;
    }

    public int getKonto() {
        return konto;
    }
}
