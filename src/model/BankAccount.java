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

public class BankAccount {


    public TabPane tabPane;
    public Pane diagrammContainer;
    public Pane sumContainer;
    public MenuItem mi_save;

    ArrayList <Group> groups = new ArrayList<>();
    private ObservableList<ArrayList<Transaction>> years_Transaction = FXCollections.observableArrayList();
    private ObservableList<ArrayList<XYChart.Data<MyDate,Number>>> years_data = FXCollections.observableArrayList();

    public BankAccount() {
    }

    public void addTransaction(Transaction transaction) {
        int yearIndex = getYearIndex(transaction);
        ArrayList<Transaction> transactions = years_Transaction.get((yearIndex));

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
        years_data.get(yearIndex).add(new XYChart.Data<>(transaction.getMyDate(),transaction.getBalance()/100));


        // update Kontostand of the transaction after it
        while (yearIndex< years_Transaction.size()) {
            transactions = years_Transaction.get(yearIndex);
            while (transactionIndex < transactions.size()) {
                if (transactionIndex == 0) {
                    if (yearIndex == 0) {
                        transactions.get(transactionIndex).calculateBalance(0);
                    } else {
                        ArrayList<Transaction> prevYear = years_Transaction.get(yearIndex - 1);
                        transactions.get(transactionIndex).calculateBalance(prevYear.get(prevYear.size() - 1).getBalance());
                    }
                    years_data.get(yearIndex).get(transactionIndex).setYValue(transactions.get(transactionIndex).getBalance() / 100);
                } else {
                    transactions.get(transactionIndex).calculateBalance(transactions.get(transactionIndex - 1).getBalance());
                }
                years_data.get(yearIndex).get(transactionIndex).setYValue(transactions.get(transactionIndex).getBalance() / 100);
                transactionIndex++;
            }
            yearIndex++;
            transactionIndex=0;
        }
    }

    public void deleteTransaction(Transaction transaction){
        int index = getYearIndex(transaction);
        ArrayList<Transaction> transactions = years_Transaction.get(index);

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
                        transactions.get(i).calculateBalance(0);
                    } else {
                        ArrayList<Transaction> prevYear = years_Transaction.get(index - 1);
                        transactions.get(i).calculateBalance(prevYear.get(prevYear.size() - 1).getBalance());
                    }

                } else {
                    transactions.get(i).calculateBalance(transactions.get(i - 1).getBalance());
                }
                years_data.get(index).get(i).setYValue(transactions.get(i).getBalance() / 100);
                i++;
            }
            index++;
            i=0;
        }
        reload();
    }

    public void addGroup(Group group){
        groups.add(group);
    }

    private int getYearIndex(Transaction transaction){
        int i=0;
        for (ArrayList<Transaction> allTransactions: years_Transaction){
            if(allTransactions.get(0).getDate().getYear() == transaction.getDate().getYear()){
                return i;
            }if(allTransactions.get(0).getDate().getYear() > transaction.getDate().getYear()){
                years_Transaction.add(i,new ArrayList<>());
                years_data.add(i,new ArrayList<>());
                return i;
            }if(allTransactions.get(0).getDate().getYear() < transaction.getDate().getYear()){
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
            tabPane.getTabs().clear();
            tabPane.getSelectionModel().select(j);
            mi_save.setDisable(false);
        });
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public ObservableList<ArrayList<Transaction>> getYears_Transaction() {
        return years_Transaction;
    }

    public ObservableList<ArrayList<XYChart.Data<MyDate, Number>>> getYears_data() {
        return years_data;
    }
}
