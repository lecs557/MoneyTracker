package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.HashMap;

public class Account {

    private boolean err;
    private String name;
    private String iban;
    private String bic;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private ObservableList<XYChart.Data<MyDate,Number>> data = FXCollections.observableArrayList();
    private int startKapital=1000;

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
        data.add(new XYChart.Data<>(transaction.getMyDate(),transaction.getKonto()/100));
        while(i < transactions.size()){
            if(i==0){
                transactions.get(i).berechneKontoStand(startKapital);
            } else {
                transactions.get(i).berechneKontoStand(transactions.get(i-1).getKonto());
            }
                data.get(i).setYValue(transactions.get(i).getKonto()/100);
            i++;
        }
    }

    public void deleteTransaction(Transaction transaction){
        int i = transactions.indexOf(transaction);
        transactions.remove(i);
        data.remove(i);
        while(i < transactions.size()){
            if(i==0){
                transactions.get(i).berechneKontoStand(startKapital);
            } else {
                transactions.get(i).berechneKontoStand(transactions.get(i-1).getKonto());
            }
            data.get(i).setYValue(transactions.get(i).getKonto()/100);
            i++;
        }

    }

    public String getName() {
        return name;
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public ObservableList<XYChart.Data<MyDate, Number>> getData() {
        return data;
    }

    public void setErr(boolean err) {
        this.err = err;
    }
}
