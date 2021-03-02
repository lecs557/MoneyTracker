package view.simple_panes;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.storeclasses.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionTabPane extends TabPane {

    public TransactionTabPane() {

        setTransactions(SampleClass.getSampleTransactions());

    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        getTabs().clear();
        int currentYear=0;
        TransactionTable currentTable=null;
        for (Transaction transaction : transactions){
            if (transaction.getLocalDate().getYear()!=currentYear){
                currentYear = transaction.getLocalDate().getYear();
                currentTable = new TransactionTable();
                Tab tab = new Tab(""+currentYear);
                tab.setContent(currentTable);
                getTabs().add(tab);
            }
            if (currentTable != null) {
                currentTable.addTransacion(transaction);
            }
        }
    }



}
