package view.panes.elements;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.storeclasses.Group;
import model.storeclasses.Transaction;
import view.simple_panes.SampleClass;

import java.util.ArrayList;

public class TransactionTabPane extends TabPane {

    ArrayList<Transaction> transactions;
    ArrayList<TransactionTable> transactionTables = new ArrayList<>();

    public TransactionTabPane() {
        getStylesheets().add(getClass().getResource("/view/style/tatabpane.css").toString());
        applyTransactions(SampleClass.getSampleTransactions(), SampleClass.getSampleGroups());
    }

    public void applyTransactions(ArrayList<Transaction> transactions, ArrayList<Group> groups) {
        getTabs().clear();
        transactionTables.clear();
        int currentYear=0;
        TransactionTable currentTable=null;
        this.transactions=transactions;
        for (Transaction transaction : transactions){
            if (transaction.getLocalDate().getYear()!=currentYear){
                currentYear = transaction.getLocalDate().getYear();
                currentTable = new TransactionTable(groups);
                Tab tab = new Tab(""+currentYear);
                tab.setContent(currentTable);
                getTabs().add(tab);
                transactionTables.add(currentTable);
            }
            if (currentTable != null) {
                currentTable.addTransaction(transaction);
            }
        }
        if(getTabs().isEmpty()){
            getTabs().add(new Tab("Keine Transactionen"));
        }
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<TransactionTable> getTransactionTables() {
        return transactionTables;
    }
}


