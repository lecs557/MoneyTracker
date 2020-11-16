package model.threads;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import model.Profile;
import model.Group;
import model.Main;
import model.Transaction;
import view.simple_panes.ViewUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Saver extends Thread {

    private SimpleDoubleProperty progressYear = new SimpleDoubleProperty();
    private SimpleDoubleProperty progressTransaction = new SimpleDoubleProperty();
    private SimpleBooleanProperty running = new SimpleBooleanProperty();

    @Override
    public void run() {
        running.set(true);
        String path= Main.currentAccount.getFilePath();
        Profile acc = Main.currentAccount;
        int i=0;
        int sizeYt = acc.getYears_Transaction().size();
        try {
            FileWriter accountFile = new FileWriter(path + "/"+ acc.getName()+".konto");
            for (Group group: acc.getGroups()){
                accountFile.append(group.store());
            }
            accountFile.append(Main.OPTIONSEPARATOR);
            for (ArrayList<Transaction> olt:acc.getYears_Transaction()) {
                i++;
                progressYear.set((double) i/sizeYt);
                int j=0;
                int sizeT = olt.size();
                for (Transaction t:olt) {
                    j++;
                    progressTransaction.set((double)j/sizeT);
                    accountFile.append(t.store());
                }
            }
            accountFile.close();
        } catch (IOException  e) {
            System.out.println("Problem mit dem Pfad: "+path);
            Platform.runLater(ViewUtils::setPath);
            e.printStackTrace();
        }
        running.set(false);
    }


    public SimpleDoubleProperty progressYearProperty() {
        return progressYear;
    }

    public SimpleDoubleProperty progressTransactionProperty() {
        return progressTransaction;
    }

    public SimpleBooleanProperty runningProperty() {
        return running;
    }
}
