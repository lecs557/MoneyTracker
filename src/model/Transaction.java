package model;

import java.time.LocalDate;

public class Transaction {

    private LocalDate date;
    private String dateString;
    private String reason;
    private int betrag;

    public Transaction(String date, String reason, int betrag) {
        this.dateString = date;
        this.reason = reason;
        this.betrag = betrag;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateString() {
        return dateString;
    }

    public String getReason() {
        return reason;
    }

    public int getBetrag() {
        return betrag;
    }
}
