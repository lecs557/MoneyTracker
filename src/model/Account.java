package model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import view.simple_panes.SumTable;
import view.simple_panes.TransactionChart;
import view.simple_panes.TransactionTable;

import java.util.ArrayList;

public class Account {

    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public MenuItem mi_save;
    private String name;
    private String path;
    private ObservableList<ArrayList<Transaction>> years_Transaction = FXCollections.observableArrayList();
    private ArrayList<Sum> sums = new ArrayList<>();
    private ObservableList<ArrayList<XYChart.Data<MyDate,Number>>> years_data = FXCollections.observableArrayList();
    private int startKapital=0;

    public Account(String name)  {
        this.name = name;

    }

    public void addTransaction(Transaction transaction) {
        int yearIndex = getYearIndex(transaction);
        ArrayList<Transaction> transactions = years_Transaction.get((yearIndex));
        int sumIndex = getSumIndex(transaction);

        sums.get(sumIndex).addToSum(transaction);

        // add transaction in the right place
        int transactionIndex = transactions.size();
        if (!transactions.isEmpty()) {
            while (transaction.getDate().isBefore(transactions.get(transactionIndex-1).getDate())) {
                transactionIndex--;
                if (transactionIndex==0){
                    break;
                }
            }
        }
        transactions.add(transactionIndex,transaction);
        years_data.get(yearIndex).add(new XYChart.Data<>(transaction.getMyDate(),transaction.getKonto()/100));


        // update Kontostand of the transaction after it
        while (yearIndex< years_Transaction.size()) {
            transactions = years_Transaction.get(yearIndex);
            while (transactionIndex < transactions.size()) {
                if (transactionIndex == 0) {
                    if (yearIndex == 0) {
                        transactions.get(transactionIndex).berechneKontoStand(startKapital);
                    } else {
                        ArrayList<Transaction> prevYear = years_Transaction.get(yearIndex - 1);
                        transactions.get(transactionIndex).berechneKontoStand(prevYear.get(prevYear.size() - 1).getKonto());
                    }
                    years_data.get(yearIndex).get(transactionIndex).setYValue(transactions.get(transactionIndex).getKonto() / 100);
                } else {
                    transactions.get(transactionIndex).berechneKontoStand(transactions.get(transactionIndex - 1).getKonto());
                }
                years_data.get(yearIndex).get(transactionIndex).setYValue(transactions.get(transactionIndex).getKonto() / 100);
                transactionIndex++;
            }
            yearIndex++;
            transactionIndex=0;
        }
    }

    public void deleteTransaction(Transaction transaction){
        int index = getYearIndex(transaction);
        ArrayList<Transaction> transactions = years_Transaction.get(index);
        int sumIndex = getSumIndex(transaction);

        sums.get(sumIndex).removeFromSum(transaction);

        // remove transaction
        int i = transactions.indexOf(transaction);
        transactions.remove(i);
        years_data.get(index).remove(i);
        if(transactions.isEmpty()){
            years_Transaction.remove(index);
            years_data.remove(index);
        }


        // update Kontostand of the transaction after it
        while (index< years_Transaction.size()) {
            transactions = years_Transaction.get(index);
            while (i < transactions.size()) {
                if (i == 0) {
                    if (index == 0) {
                        transactions.get(i).berechneKontoStand(startKapital);
                    } else {
                        ArrayList<Transaction> prevYear = years_Transaction.get(index - 1);
                        transactions.get(i).berechneKontoStand(prevYear.get(prevYear.size() - 1).getKonto());
                    }

                } else {
                    transactions.get(i).berechneKontoStand(transactions.get(i - 1).getKonto());
                }
                years_data.get(index).get(i).setYValue(transactions.get(i).getKonto() / 100);
                i++;
            }
            index++;
            i=0;
        }
        reload();
    }

    private int getSumIndex(Transaction transaction) {
        int i=0;
        for (Sum s : sums){
            if(s.getReason().equals(transaction.getReason())){
                return i;
            }
            i++;
        }
        sums.add(new Sum(transaction));
        return i;
    }

    private int getYearIndex(Transaction transaction){
        int i=0;
        for (ArrayList<Transaction> allTarnsactions: years_Transaction){
            if(allTarnsactions.get(0).getDate().getYear() == transaction.getDate().getYear()){
                return i;
            }if(allTarnsactions.get(0).getDate().getYear() > transaction.getDate().getYear()){
                years_Transaction.add(i,new ArrayList<>());
                years_data.add(i,new ArrayList<>());
                return i;
            }if(allTarnsactions.get(0).getDate().getYear() < transaction.getDate().getYear()){
                i++;
            }
        }
        years_Transaction.add(new ArrayList<>());
        years_data.add(new ArrayList<>());
        return i;
    }

    public void reload(){
        Platform.runLater((Runnable) () -> {
            int j = tabPane.getSelectionModel().getSelectedIndex();
            new TransactionChart().putInto(diagrammContainer);
            new SumTable().putInto(sumContainer);
            Main.currentAccount.tabPane = tabPane;
            tabPane.getTabs().clear();
            for (ArrayList<Transaction> year : Main.currentAccount.getYears_Transaction()) {
                tabPane.getTabs().add(new Tab(year.get(0).getDate().getYear() + "", new TransactionTable(year)));
            }
            tabPane.getSelectionModel().select(j);
            mi_save.setDisable(false);
        });
    }

    public String getName() {
        return name;
    }

    public ObservableList<ArrayList<Transaction>> getYears_Transaction() {
        return years_Transaction;
    }

    public ObservableList<ArrayList<XYChart.Data<MyDate, Number>>> getYears_data() {
        return years_data;
    }

    public ArrayList<Sum> getSums() {
        return sums;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}

