package model.runnables;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import model.Account;
import model.Main;
import model.Transaction;
import view.customized_Panes.ViewUtils;

import java.io.FileWriter;
import java.io.IOException;

public class Saver implements Runnable {

    SimpleDoubleProperty progressYear;
    SimpleDoubleProperty progressTransaction;

    @Override
    public void run() {
        String path= Main.currentAccount.getPath();
        Account acc = Main.currentAccount;
        int i=0;
        int sizeYt = acc.getYears_Transaction().size();
        try {
            FileWriter accountFile = new FileWriter(path + "/"+ acc.getName()+".konto");
            for (ObservableList<Transaction> olt:acc.getYears_Transaction()) {
                i++;
                progressYear.set((double) i/sizeYt);
                int j=0;
                int sizeT = olt.size();
                for (Transaction t:olt) {
                    j++;
                    progressTransaction.set((double)j/sizeT);
                    accountFile.append(t.getDate_S() + ":" + t.getReason() + ":" + t.getBetrag() + ";");
                }
            }
            accountFile.close();
        } catch (IOException e) {
            System.out.println("Problem mit dem Pfad: "+path);
            Platform.runLater(ViewUtils::setPath);
            e.printStackTrace();
        }
    }


    public SimpleDoubleProperty progressYearProperty() {
        return progressYear;
    }

    public SimpleDoubleProperty progressTransactionProperty() {
        return progressTransaction;
    }
}
