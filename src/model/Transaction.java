package model;

import java.time.LocalDate;

public class Transaction {

    private LocalDate date;
    private String reason;
    private int betrag;

    public Transaction(LocalDate date, String reason, int betrag) {
        this.date = date;
        this.reason = reason;
        this.betrag = betrag;
    }
}
