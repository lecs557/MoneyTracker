package model;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Account {

    private String name;
    private String iban;
    private String bic;
    private ObservableList<Transaction> transactions;

    public Account(String name, String iban, String bic) {
        this.name = name;
        this.iban = iban;
        this.bic = bic;
    }

    public void createTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public String getName() {
        return name;
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }
}
