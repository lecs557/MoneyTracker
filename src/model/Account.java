package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Account {

    private boolean err;
    private String name;
    private String iban;
    private String bic;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private int startKapital=0;

    public Account(String name, String iban, String bic)  {
        this.name = name;
        this.iban = iban;
        this.bic = bic;
    }

    public void addTransaction(Transaction transaction) {
        int i=transactions.size();
        if (!transactions.isEmpty()) {
            while (transaction.getDate().isBefore(transactions.get(i-1).getDate())) {
                i--;
                if (i==0){
                    break;
                }
            }
        }
        transactions.add(i,transaction);
        while(i < transactions.size()){
            if(i==0){
                transactions.get(i).berechneKontoStand(startKapital);
            } else {
                transactions.get(i).berechneKontoStand(transactions.get(i-1).getKonto());
            }
            i++;
        }
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
