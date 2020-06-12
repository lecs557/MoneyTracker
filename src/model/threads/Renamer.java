package model.threads;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import model.Account;
import model.Main;
import model.Renames;
import model.Transaction;

public class Renamer extends Thread {

    private SimpleDoubleProperty progressYear = new SimpleDoubleProperty();
    private SimpleDoubleProperty progressTransaction = new SimpleDoubleProperty();
    private SimpleBooleanProperty isRunning = new SimpleBooleanProperty(false);
    private Renames renames;

    public Renamer(Renames renames) {
        this.renames = renames;
    }

    @Override
    public void run() {
        isRunning.set(true);
        Account acc = Main.currentAccount;
        int i=0;
        int sizeYt = acc.getYears_Transaction().size();
        for (ObservableList<Transaction> yt: Main.currentAccount.getYears_Transaction()){
            i++;
            progressYear.set((double) i/sizeYt);
            int j=0;
            int sizeT = yt.size();
            for(Transaction t: yt){
                if(t.getReason().contains(renames.getContains())){
                    t.setReason(renames.getRenameTo());
                }
                j++;
                progressTransaction.set((double)j/sizeT);

            }
        }
        isRunning.set(false);

    }

    public SimpleDoubleProperty progressYearProperty() {
        return progressYear;
    }

    public SimpleDoubleProperty progressTransactionProperty() {
        return progressTransaction;
    }

    public SimpleBooleanProperty isRunningProperty() {
        return isRunning;
    }

}

