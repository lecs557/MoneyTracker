package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Account {

    private boolean err;
    private String name;
    private String iban;
    private String bic;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public Account(String name, String iban, String bic)  {
        this.name = name;
        this.iban = iban;
        this.bic = bic;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public String getName() {
        return name;
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public boolean isErr() {
        return err;
    }

    public void setErr(boolean err) {
        this.err = err;
    }
}
